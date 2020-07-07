package com.io.my_book_store_track.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.io.my_book_store_track.Config;
import com.io.my_book_store_track.R;
import com.io.my_book_store_track.activities.home.UpdateOrderActivity;
import com.io.my_book_store_track.holder.OrderHolder;
import com.io.my_book_store_track.model.responseModel.Datum;
import com.io.my_book_store_track.model.responseModel.OrderListResponseModel;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    Context context;
    List<Datum> models;


    public OrderAdapter(Context activity, List<Datum> models) {
        this.models = models;
        this.context = activity;
    }


    @Override
    public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderHolder holder, final int position) {
        final Datum model = models.get(position);
        holder.tv_orderId.setText("Order Id -#"+model.getOrderId());
        holder.tv_orderStatus.setText("Status -#"+model.getOrderStatus());
        Glide.with(context).load(Config.imageUrl +model.getOrderItems().get(0).getBook().getAvatarPath()).into(holder.iv_book_icon);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.OrderID = String.valueOf(model.getOrderId());
                Config.driverID = String.valueOf(model.getDeliveryGuyId());
                Config.devliveryStatus =model.getOrderStatus();
                Intent i = new Intent(context, UpdateOrderActivity.class);
                i.putExtra("orderid",model.getOrderId());
                i.putExtra("driverid",model.getDeliveryGuyId());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
