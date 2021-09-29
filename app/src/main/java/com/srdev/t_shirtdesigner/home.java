package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class home extends AppCompatActivity {
    Window window;
    CardView create,myorder;
    Button logout;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));


        create = findViewById(R.id.create_card);
        myorder = findViewById(R.id.my_order);
        logout = findViewById(R.id.btn_logout);

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String image = prefs.getString("final_image", null);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("AuthStatus", null);
                editor.apply();
                Intent intent = new Intent(home.this, loginpage.class);
                startActivity(intent);
                Toast.makeText(home.this, "Logged out", Toast.LENGTH_SHORT).show();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home.this, T_Shirt.class);
                startActivity(i);
            }
        });
        myorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home.this, MyOrder.class);
                startActivity(i);
            }
        });
    }
}