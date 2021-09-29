package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MyOrder extends AppCompatActivity {

    Window window;
    TextView title;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    TextView username,userphoneno,useraddress,pay,logo,shirtcount,sh_xxxl,sh_xxl,sh_xl,sh_l,sh_m,sh_s,tot, norder;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        username = findViewById(R.id.nameid);
        userphoneno = findViewById(R.id.phoneid);
        useraddress = findViewById(R.id.breif_address);
        pay = findViewById(R.id.payid);
        logo = findViewById(R.id.logoid);
        shirtcount = findViewById(R.id.countid);
        sh_xxxl = findViewById(R.id.xxxlid);
        sh_xxl = findViewById(R.id.xxlid);
        sh_xl = findViewById(R.id.xlid);

        sh_l = findViewById(R.id.lid);
        sh_m = findViewById(R.id.mid);
        sh_s = findViewById(R.id.sid);

        tot = findViewById(R.id.totalid);
        norder = findViewById(R.id.no_order);

        cardView = findViewById(R.id.card_view);

        title = findViewById(R.id.my_ordertitle);


        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String value = prefs.getString("AuthStatus","empty");

        if(value.equals("user")){
            title.setText("My Order");
        }else if(value.equals("admin")){
            title.setText("User Orders");
        }

        String name = prefs.getString("name", null);

        String phone = prefs.getString("phoneno", null);
        String address = prefs.getString("address", null);

        ///  Shirt size count
        int triXl = prefs.getInt("xxxl", 0); // XXXL
        String XXXL = String.valueOf(triXl);

        int douXl = prefs.getInt("xxl", 0); // XXL
        String XXL = String.valueOf(douXl);

        int sinXl = prefs.getInt("xl", 0); // XL
        String XL = String.valueOf(sinXl);

        int a = prefs.getInt("l", 0); // L
        String L = String.valueOf(a);

        int b = prefs.getInt("m", 0); // M
        String M = String.valueOf(b);

        int c = prefs.getInt("s", 0); // S
        String S = String.valueOf(c);

        String total = prefs.getString("total_amount",null);
        String payMethod = prefs.getString("paymethod",null);

        String count = prefs.getString("count",null);
        String logoPrice = prefs.getString("logo_price",null);


        if (name == null){
            norder.setVisibility(View.VISIBLE);
            cardView.setVisibility(View.GONE);
        }else{
            norder.setVisibility(View.GONE);
            cardView.setVisibility(View.VISIBLE);

            username.setText(name);
            userphoneno.setText(phone);
            useraddress.setText(address);

            pay.setText(payMethod);
            logo.setText(logoPrice);
            shirtcount.setText(count);

            sh_xxxl.setText(XXXL);
            sh_xxl.setText(XXL);
            sh_xl.setText(XL);

            sh_l.setText(L);
            sh_m.setText(M);
            sh_s.setText(S);

            tot.setText(total);

        }
    }
}