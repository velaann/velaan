package com.example.velaann;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;


public class MyProfile extends AppCompatActivity {
    public static final String TAG = "TAG";
    TextView proname, proemail, proaddress,procity, prostate;
    EditText procontact;
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userId;
    ImageView profileImage;
    Button changeProfileImage,save;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        proname=findViewById(R.id.profilename);
        procontact=findViewById(R.id.profilephone);
        proemail=findViewById(R.id.profilemail);
        proaddress=findViewById(R.id.profileaddress);
        procity=findViewById(R.id.profilecity);
        prostate=findViewById(R.id.profilestate);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        userId= fAuth.getCurrentUser().getUid();
        profileImage = findViewById(R.id.profileImage);
        changeProfileImage = findViewById(R.id.changeProfile);
        save=findViewById(R.id.savebutton);
        storageReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                             @Override
                                                             public void onSuccess(Uri uri) {
                                                                 Picasso.get().load(uri).into(profileImage);
                                                             }
                                                         });


                DocumentReference documentReference = fstore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                proname.setText(documentSnapshot.getString("UserName"));
                procontact.setText(documentSnapshot.getString("contactNo"));
                proemail.setText(documentSnapshot.getString("emailId"));
                proaddress.setText(documentSnapshot.getString("address"));
                procity.setText(documentSnapshot.getString("city"));
                prostate.setText(documentSnapshot.getString("state"));
            }
        });
        changeProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference1=fstore.collection("Users").document(userId);
                Map<String,Object> user=new HashMap<>();
                String username=proname.getText().toString();
                String email=proemail.getText().toString();
                String contact=procontact.getText().toString();
                String state=prostate.getText().toString();
                String city=procity.getText().toString();
                String address=proaddress.getText().toString();
                user.put("UserName",username);
                user.put("emailId",email);
                user.put("contactNo",contact);
                user.put("state",state);
                user.put("city",city);
                user.put("address",address);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Succesfully Updated!!!",Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onsuccess: user profile updated "+ userId);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Error!" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        Log.d(TAG,"onfailure: "+e.toString());
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
          if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
               //profileImage.setImageURI(imageUri);
                
                uploadImageToFirebase(imageUri);
                
            }
        }


    private void uploadImageToFirebase(Uri imageUri) {
        StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(MyProfile.this,"Image Uploaded.",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MyProfile.this, "Failed.", Toast.LENGTH_SHORT).show();
            }
        });
        
    }
}