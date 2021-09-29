package com.srdev.t_shirtdesigner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Base64;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class T_Shirt_preview extends AppCompatActivity {

    ViewGroup mainLayout, Page2, Page1, Page3, orderpreview, CreditCardDetails;
    ImageView Logo;
    LinearLayout PaymentMethodLayout;
    ImageView roundTshirt, FinalImage;
    private int xDelta;
    private int yDelta;
    Bitmap b_itmap;
    Button SubmitPage1, SubmitPage2, Placeorder;
    CardForm cardForm;
    RadioGroup radioGroup;
    EditText fname, fphone, fxxxl, fxxl, fxl, fl, fm, fs, faddress;
    TextView lg_price, ShirtCount;
    TextView totalamount;
    String radioValue = "cod";
    AlertDialog.Builder alertBuilder;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
   String stream;
    FirebaseStorage storage;
    StorageReference storageReference;
    ImageView imgview;
    Uri FilePathUri;
    int Image_Request_Code = 7;
    String XXXL,XXL,XL,L,M,S;
    
    int xxxl,xxl,xl,l,m,s;
    Window window;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_shirt_preview);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));

        mainLayout = (RelativeLayout) findViewById(R.id.main);

        PaymentMethodLayout = findViewById(R.id.paymentmethod);

        Page1 = findViewById(R.id.page1);
        Page2 = findViewById(R.id.page2);
        Page3 = findViewById(R.id.page3);

        orderpreview = findViewById(R.id.orderpreviewtitle);
        CreditCardDetails = findViewById(R.id.card_details);

        Logo = (ImageView) findViewById(R.id.logo);
        FinalImage = findViewById(R.id.final_image);

        CardForm cardForm = findViewById(R.id.card_form);
        Placeorder = findViewById(R.id.btn_place_order);

        SubmitPage1 = findViewById(R.id.sub_btn_1);
        SubmitPage2 = findViewById(R.id.sub_btn_2);

        roundTshirt = findViewById(R.id.round_shirt);
        radioGroup = findViewById(R.id.radioGroup1);

        fname = findViewById(R.id.edit_text_name);
        fphone = findViewById(R.id.edit_text_phone);
        fxxxl = findViewById(R.id.edit_text_xxxl);
        fxxl = findViewById(R.id.edit_text_xxl);
        fxl = findViewById(R.id.edit_text_xl);
        fl = findViewById(R.id.edit_text_l);
        fm = findViewById(R.id.edit_text_m);
        fs = findViewById(R.id.edit_text_s);
        faddress = findViewById(R.id.edit_text_address);

        lg_price = findViewById(R.id.logopr_text);
        ShirtCount = findViewById(R.id.shir_count_text);
        totalamount = findViewById(R.id.final_price_text);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("paymethod", radioValue);
        editor.apply();



        Intent intent = getIntent();

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        int received_roundtshirt_Image = prefs.getInt("round_tshirt", 0);
        int received_logo = prefs.getInt("logo", 0);


        String Logo_price = prefs.getString("Logo_rate", null);

        int LogoPrice = Integer.parseInt(Logo_price);

        roundTshirt.setImageResource(received_roundtshirt_Image);
        Logo.setImageResource(received_logo);

        Logo.setOnTouchListener(onTouchListener());


        SubmitPage1.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


                b_itmap = ScreenshotUtil.getInstance().takeScreenshotForView(mainLayout); // Take ScreenshotUtil for any view


                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                b_itmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                String headimg = new String(Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT));
                SharedPreferences.Editor editor3 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                editor3.putString("final_image", headimg);
                editor3.apply();



                FinalImage.setImageBitmap(b_itmap);
                Page1.setVisibility(View.GONE);
                Page2.setVisibility(View.VISIBLE);
                Page3.setVisibility(View.GONE);

            }
        });


        SubmitPage2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {

                shirtCount();
                String name = fname.getText().toString();
                String phone = fphone.getText().toString();
                
                String address = faddress.getText().toString();
                
                if (name.isEmpty()){
                    Toast.makeText(T_Shirt_preview.this, "Please enter username!!", Toast.LENGTH_SHORT).show();
                }else if(phone.isEmpty()){
                    Toast.makeText(T_Shirt_preview.this, "Please enter phone number!!", Toast.LENGTH_SHORT).show();
                }else if(address.isEmpty()){
                    Toast.makeText(T_Shirt_preview.this, "Please enter address!!", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.putString("address", address);
                    editor.putString("phoneno",phone);


                    int count = xxxl + xxl + xl + l + m + s;
                    String c = Integer.toString(count);

                    editor.putString("count",c);

                    int finalrate = (200 + LogoPrice) * count;
                    String rate = Integer.toString(finalrate);

                    String lg = "Rs." + Logo_price + "/-";
                    String tm = "Rs." + rate + "/-";
                    editor.putString("logo_price",lg);
                    editor.putString("total_amount",tm);

                    lg_price.setText(lg);
                    ShirtCount.setText(c);
                    totalamount.setText(tm);

                    Page1.setVisibility(View.GONE);
                    Page2.setVisibility(View.GONE);
                    Page3.setVisibility(View.VISIBLE);
                    editor.apply();
                }
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup1, int i) {
                switch (i) {
                    case R.id.cod:
                        orderpreview.setVisibility(View.VISIBLE);
                        PaymentMethodLayout.setVisibility(View.VISIBLE);
                        CreditCardDetails.setVisibility(View.GONE);
                        radioValue = "cod";
                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("paymethod", radioValue);
                        editor.apply();
                        break;
                    case R.id.credit:

                        orderpreview.setVisibility(View.GONE);
                        PaymentMethodLayout.setVisibility(View.VISIBLE);
                        CreditCardDetails.setVisibility(View.VISIBLE);
                        radioValue = "credit";
                        SharedPreferences.Editor editor1 = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor1.putString("paymethod", radioValue);
                        editor1.apply();
                        break;
                }
            }
        });


        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(T_Shirt_preview.this);


        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);


        Placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                String name = prefs.getString("name", null);
                String phone = prefs.getString("phone", null);
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


                if (payMethod.equals("credit")){
                    if (cardForm.isValid()) {
                        alertBuilder = new AlertDialog.Builder(T_Shirt_preview.this);
                        alertBuilder.setTitle("Confirm before purchase");
                        alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                                "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                                "Card CVV: " + cardForm.getCvv() + "\n" +
                                "Postal code: " + cardForm.getPostalCode() + "\n" +
                                "Phone number: " + cardForm.getMobileNumber());
                        alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                                Intent intent = new Intent(T_Shirt_preview.this,success_page.class);
                                startActivity(intent);
                                Toast.makeText(T_Shirt_preview.this, "Thank you for purchase", Toast.LENGTH_LONG).show();
                            }
                        });
                        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertBuilder.create();
                        alertDialog.show();

                     } else {
                        Toast.makeText(T_Shirt_preview.this, "Please complete the form", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Intent intent = new Intent(T_Shirt_preview.this,success_page.class);
                    startActivity(intent);
                    Toast.makeText(T_Shirt_preview.this, "Thank you for purchase", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

    private View.OnTouchListener onTouchListener() {
        return new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {

                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)
                                view.getLayoutParams();

                        xDelta = x - lParams.leftMargin;
                        yDelta = y - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
//                        Toast.makeText(T_Shirt_preview.this,
//                                "I'm here!", Toast.LENGTH_SHORT)
//                                .show();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                                .getLayoutParams();
                        layoutParams.leftMargin = x - xDelta;
                        layoutParams.topMargin = y - yDelta;
                        layoutParams.rightMargin = 0;
                        layoutParams.bottomMargin = 0;
                        view.setLayoutParams(layoutParams);
                        break;
                }

                mainLayout.invalidate();
                return true;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                imgview.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }

    }
    public void shirtCount(){
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        //  XXXl value
        XXXL = fxxxl.getText().toString();
        if (XXXL.isEmpty()){
            xxxl = 0;
            editor.putInt("xxxl", xxxl);
        }else{
            xxxl = Integer.parseInt(XXXL);
            editor.putInt("xxxl", xxxl);
        }

        // XXL value
        XXL = fxxl.getText().toString();
        if(XXL.isEmpty()){
            xxl = 0;
            editor.putInt("xxl", xxl);
        }else{
            xxl = Integer.parseInt(XXL);
            editor.putInt("xxl", xxl);
        }

        // XL value
        XL = fxl.getText().toString();
        if (XL.isEmpty()){
            xl = 0;
            editor.putInt("xl", xl);
        }else{
            xl = Integer.parseInt(XL);
            editor.putInt("xl", xl);
        }

        // L value
        L = fl.getText().toString();
        if (L.isEmpty()){
            l = 0;
            editor.putInt("l", l);
        }else{
            l = Integer.parseInt(L);
            editor.putInt("l", l);
        }

        // M value
        M = fm.getText().toString();
        if (M.isEmpty()){
            m = 0;
            editor.putInt("m", m);
        }else{
            m = Integer.parseInt(M);
            editor.putInt("m", m);
        }

        // S value
        S = fs.getText().toString();
        if (S.isEmpty()){
            s = 0;
            editor.putInt("s", s);
        }else {
            s = Integer.parseInt(S);
            editor.putInt("s", s);
        }
        editor.apply();
    }
}