package com.example.shubham.barbar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder>

{
private  List<Item_Model> item_models;
private  Context context;

    public RecyclerviewAdapter(List<Item_Model> item_models, Context context) {
        this.item_models = item_models;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final Item_Model item_model=item_models.get(i);
        viewHolder.price.setText(item_model.getPrice());
        viewHolder.item.setText(item_model.getItem());
        viewHolder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " );
                Intent intent = new Intent(context, Book.class);
                Log.d("!!!!!!!!!!!",item_model.getPrice());
             /*   Toast.makeText(this,item_model.getPrice(),Toast.LENGTH_SHORT).show();*/
                intent.putExtra("price",item_model.getPrice());
                intent.putExtra("item",item_model.getItem());
                intent.putExtra("category_id",item_model.getCategory_id());
              /*  Log.d("category_id",item_model.getCategory_id());*/
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return item_models.size();
    }



    public class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView   item;
        public TextView price;
        Button book;
        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            item=itemView.findViewById(R.id.item);
            price=itemView.findViewById(R.id.price);
            book=itemView.findViewById(R.id.book);

        }
    }
}