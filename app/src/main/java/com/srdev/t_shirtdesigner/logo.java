package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class logo extends AppCompatActivity {
    GridView gridView;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Window window;
    int[] logos = {R.drawable.logo1,R.drawable.logo2,R.drawable.logo3,R.drawable.logo4,R.drawable.logo5,R.drawable.logo22,R.drawable.logo7,R.drawable.logo8,R.drawable.logo9,R.drawable.logo10,R.drawable.logo11,R.drawable.logo12,R.drawable.logo13,R.drawable.logo14,R.drawable.logo15,R.drawable.logo16,R.drawable.logo17,R.drawable.logo18,R.drawable.logo19,R.drawable.logo20,R.drawable.logo21,R.drawable.logo22};
    //int[] price = {30,20,40,25,30,50,45,20,35,20,30,20,40,25,30,50,45,20,35,20,40,35};
    String[] price = {"30","20","40","25","30","50","45","20","35","20","30","20","40","25","30","50","45","20","35","20","40","35"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        gridView = findViewById(R.id.gridview_logo);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));


        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getApplicationContext(), T_Shirt_preview.class);
                //intent.putExtra("Logo_rate",price[i]);



                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("logo", logos[i]);
                editor.putString("Logo_rate", price[i]);
                editor.apply();
                startActivity(intent);
            }
        });


    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return logos.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            RelativeLayout lay1 = view1.findViewById(R.id.gridviewdata);
            RelativeLayout lay2 = view1.findViewById(R.id.gridviewdata2);
            lay1.setVisibility(View.GONE);
            lay2.setVisibility(View.VISIBLE);
            TextView Price = view1.findViewById(R.id.price);
            ImageView image = view1.findViewById(R.id.grid_image2);
            Glide.with(getApplicationContext()).load(logos[i]).centerCrop().into(image);
            Price.setText("Rs. "+price[i]+" /-");
            image.setImageResource(logos[i]);
            return view1;

        }
    }
}
