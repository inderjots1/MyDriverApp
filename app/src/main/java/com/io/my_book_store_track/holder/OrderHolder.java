package com.io.my_book_store_track.holder;


import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.io.my_book_store_track.R;


public class OrderHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
    public RelativeLayout constraintLayout;
    public TextView tv_orderId;
    public TextView tv_orderDate;
    public TextView tv_detail;
    public TextView tv_track;
    public TextView tv_orderStatus;
    public ImageView iv_book_icon;



    public OrderHolder(View view) {

        super(view);
        tv_orderId = view.findViewById(R.id.tv_orderid);
        tv_orderDate = view.findViewById(R.id.tv_order_time);
        tv_detail = view.findViewById(R.id.tv_detail);
        tv_track = view.findViewById(R.id.tv_track);
        iv_book_icon = view.findViewById(R.id.imageView10);
        tv_orderStatus = view.findViewById(R.id.tv_orderStatus);
        constraintLayout = view.findViewById(R.id.root);

    }

    @Override
    public void onClick(View view) {
    }
}
