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

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>

{
    private  List<DetailModel> detailModels;
    private  Context context;

    public RecyclerViewAdapter2(List<DetailModel> detailModels, Context context) {
        this.detailModels = detailModels;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_row,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final DetailModel detailModel=detailModels.get(i);
        viewHolder.price.setText(detailModel.getPrice());
        viewHolder.service_name.setText(detailModel.getService());
        viewHolder.view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " );
                Intent intent = new Intent(context, AppointmentDetail.class);
                Log.d("!!!!!!!!!!!",detailModel.getPrice());
                intent.putExtra("price",detailModel.getPrice());
                intent.putExtra("service_name",detailModel.getService());
                intent.putExtra("contact",detailModel.getContact());
                intent.putExtra("booking_date",detailModel.getDate());
                intent.putExtra("slot",detailModel.getTime());



                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return detailModels.size();
    }



    public class ViewHolder  extends RecyclerView.ViewHolder{
        public TextView   service_name;
        public TextView price;
        Button view_detail;
        public ViewHolder(@NonNull View itemView) {


            super(itemView);
            service_name=itemView.findViewById(R.id.service_name);
            price=itemView.findViewById(R.id.price);
            view_detail=itemView.findViewById(R.id.view_detail);

        }
    }
}