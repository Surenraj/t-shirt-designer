package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

public class SplashScreen extends AppCompatActivity {
    Window window;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String value = prefs.getString("AuthStatus","empty");

                if (value.equals("user")){
                    Intent intent = new Intent(SplashScreen.this,home.class);
                    startActivity(intent);
                    finish();
                }else if (value.equals("admin")){
                    Intent i = new Intent(SplashScreen.this, admin_home.class);
                    startActivity(i);
                    finish();
                }else if(value.equals("empty")){
                    Intent i = new Intent(SplashScreen.this, loginpage.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 2000);
    }
}
