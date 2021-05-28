package com.example.velaann.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.velaann.Interface.SubCategoryOnClickInterface;
import com.example.velaann.R;

import static android.view.View.*;

public class CategoryTwoViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    public TextView dataName;
    public ImageView image;
    Button selling;
    public SubCategoryOnClickInterface subCategoryOnClickInterface;

    public CategoryTwoViewHolder(@NonNull final View itemView) {
        super(itemView);
        dataName = itemView.findViewById(R.id.data_name);
        image = itemView.findViewById(R.id.proimg);
        selling = itemView.findViewById(R.id.sell);
        selling.setOnClickListener(this);
        itemView.setOnClickListener(this);
    }

    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        subCategoryOnClickInterface.onClick(v,false);
    }

    public void SubCategoryInterfaceClick(SubCategoryOnClickInterface subCategoryOnClickInterface)
    {
        this.subCategoryOnClickInterface = subCategoryOnClickInterface;
    }

}