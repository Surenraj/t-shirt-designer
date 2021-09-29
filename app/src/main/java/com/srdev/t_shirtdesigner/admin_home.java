package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class admin_home extends AppCompatActivity {
    ImageView imageView;
    Window window;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    CardView cardView;
    TextView noOrder;
    Button logout;

Bitmap bits = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        noOrder = findViewById(R.id.no_orderid);

        cardView = findViewById(R.id.ad_cardid);

        imageView = findViewById(R.id.img);
        logout = findViewById(R.id.btn_logout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String image = prefs.getString("final_image", null);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("AuthStatus", null);
                editor.apply();
                Intent intent = new Intent(admin_home.this, loginpage.class);
                startActivity(intent);
                Toast.makeText(admin_home.this, "Logged out", Toast.LENGTH_SHORT).show();

            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(admin_home.this,MyOrder.class);
                startActivity(intent);
            }
        });

        if(image != null) {
            byte[] decode = Base64.decode(image.getBytes(), 1);
            bits = BitmapFactory.decodeByteArray(decode, 0, decode.length);
            imageView.setImageBitmap(bits);
            cardView.setVisibility(View.VISIBLE);
            noOrder.setVisibility(View.GONE);
        }
        else
        {
           cardView.setVisibility(View.GONE);
           noOrder.setVisibility(View.VISIBLE);
        }
    }
}