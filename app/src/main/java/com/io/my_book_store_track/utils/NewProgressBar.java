package com.io.my_book_store_track.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.io.my_book_store_track.R;



public class NewProgressBar {
    Context con;
    Dialog dialog;
    public NewProgressBar(Context con  )
    {
        this.con  =con;
    }
    public void show ()
    {
            dialog = new Dialog(con);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(R.layout.layoutprogress);
            dialog.show();

        }

        public void dismiss()
        {

            dialog.dismiss();
        }


}
