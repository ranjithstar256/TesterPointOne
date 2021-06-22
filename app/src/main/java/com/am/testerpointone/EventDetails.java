package com.am.testerpointone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class EventDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);//School Annual Day Celebration…
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("School Annual Day Celebration…");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_arrow);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.white)));

    }

    public void ok(View view) {
//        Dialog dialog = new Dialog(EventDetails.this, android.R.style.Theme_Material_Light_Dialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.custom_dialog);
//
//        dialog.findViewById(R.id.appCompatButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Dialog dialog = new Dialog(EventDetails.this, android.R.style.Theme_Material_Light_Dialog);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.cus_exit_dia);
//                dialog.show();
//            }
//        });
//
//        dialog.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
        Intent intent = new Intent(EventDetails.this, EventRegistration.class);
        startActivity(intent);
    }
}