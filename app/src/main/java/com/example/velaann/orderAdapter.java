package com.example.velaann;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import static android.widget.Toast.LENGTH_LONG;
import static androidx.core.content.ContextCompat.startActivity;

public class orderAdapter extends FirestoreRecyclerAdapter<order, orderAdapter.NoteHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public orderAdapter(@NonNull FirestoreRecyclerOptions<order> options) {
        super(options);
    }
    String name,quantity,price,date1,date2,title,userid,title1;
    int iend = 0;
    FirebaseAuth firebaseAuth;
    Context context;

    public orderAdapter(@NonNull FirestoreRecyclerOptions<order> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull orderAdapter.NoteHolder holder, int position, @NonNull order model) {
        title = model.getProduct_Namee();
        iend = title.indexOf("/");
        title1 = title.substring(0,iend);
        firebaseAuth = FirebaseAuth.getInstance();
        userid = firebaseAuth.getCurrentUser().getUid();
        holder.textViewTitle.setText(model.getProduct_Namee());
        holder.textViewDescription.setText(model.getProduct_Quantityy());
        holder.textViewPriority.setText(model.getProduct_Pricee());
        holder.textViewsp.setText(model.getProduct_Valididtyy());
        holder.textViewdp.setText(model.getValid_tilll());
    }

    @NonNull
    @Override
    public orderAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_sell,parent,false);
        return new NoteHolder(view);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
        FirebaseFirestore docRef = FirebaseFirestore.getInstance();
       /* DocumentReference documentReference = */
        docRef.collection(""+title1).document(userid).delete();
                /*.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                 //   Toast.makeText(context, "Clicked Laugh Vote", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
               /* .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/
      //  documentReference.delete();
    }


    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;
        TextView textViewsp;
        TextView textViewdp;
      //  Button button1;
        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);
            textViewsp = itemView.findViewById(R.id.sai);
            textViewdp = itemView.findViewById(R.id.bhaii);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = textViewTitle.getText().toString();
                    quantity = textViewDescription.getText().toString();
                    price= textViewPriority.getText().toString();
                    date1= textViewsp.getText().toString();
                    date2 = textViewsp.getText().toString();
                    Intent intent=new Intent(v.getContext(),update.class);
                    intent.putExtra("name",name);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("price",price);
                    intent.putExtra("date1",date1);
                    intent.putExtra("date2",date2);
                    v.getContext().startActivity(intent);
                }
            });
          /*  button1 = (Button)itemView.findViewById(R.id.updatebutton);
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                name = textViewTitle.getText().toString();
                quantity = textViewDescription.getText().toString();
                price= textViewPriority.getText().toString();
                date1= textViewsp.getText().toString();
                date2 = textViewsp.getText().toString();
               Intent intent=new Intent(v.getContext(),update.class);
                    intent.putExtra("name",name);
                    intent.putExtra("quantity",quantity);
                    intent.putExtra("price",price);
                    intent.putExtra("date1",date1);
                    intent.putExtra("date2",date2);
                    v.getContext().startActivity(intent);
                   //updatedetails(name,quantity,price,date1,date2,v);

                }
            });*/

        }
    }
  /*public void updatedetails(String s1,String s2, String s3,String s4,String s5,View v)
    {
        Intent intent=new Intent(v.getContext(),update.class);
        intent.putExtra("name",name);
        intent.putExtra("quantity",quantity);
        intent.putExtra("price",price);
        intent.putExtra("date1",date1);
        intent.putExtra("date2",date2);
        v.getContext().startActivity(intent);
    }*/
}