package com.srdev.t_shirtdesigner;

import androidx.appcompat.app.AppCompatActivity;

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

import com.bumptech.glide.Glide;

public class T_Shirt extends AppCompatActivity {
    GridView gridView;
    Window window;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    int[] round_neck = {R.drawable.r_whitetshirt,R.drawable.r_blacktshirt,R.drawable.r_blueshirt,R.drawable.r_greentshirt,R.drawable.r_lightblueshirt,R.drawable.r_orangetshirt,R.drawable.r_redtshirt,R.drawable.r_violettshirt,R.drawable.r_yellowtshirt,R.drawable.r_pinktshirt};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_shirt);
        gridView = findViewById(R.id.gridview_round);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        CustomAdapter customAdapter = new CustomAdapter();
        gridView.setAdapter(customAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), logo.class);

                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putInt("round_tshirt", round_neck[i]);
                editor.apply();
                startActivity(intent);
            }
        });
    }

    private class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return round_neck.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = getLayoutInflater().inflate(R.layout.row_data,null);
            ImageView image = view1.findViewById(R.id.grid_image);

            // int id = context.getResources().getIdentifier(posts.get(position), "drawable", context.getPackageName());
                   Glide.with(getApplicationContext()).load(round_neck[i]).centerCrop().into(image);


            //image.setImageResource(round_neck[i]);
            return view1;

        }
    }
}
