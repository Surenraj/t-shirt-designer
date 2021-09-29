package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

public class detailPage extends AppCompatActivity {

    ImageView ConformImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        ConformImage = findViewById(R.id.conform_image);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {


            int res_image = bundle.getInt("Screenshot");

//        Intent intent = getIntent();
            // Bitmap bitmap = (Bitmap) intent.getParcelableExtra("ScreenShotImage");
            ConformImage.setImageResource(res_image);
        }
    }
}