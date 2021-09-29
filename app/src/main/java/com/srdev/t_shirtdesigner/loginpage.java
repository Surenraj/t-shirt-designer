package com.srdev.t_shirtdesigner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginpage extends AppCompatActivity {

    EditText email,password;
    Button loginGetin;
    FirebaseAuth firebaseAuth;
    TextView newuser;
    Window window;


    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        loginGetin = findViewById(R.id.btn_login_getin);

        email = findViewById(R.id.login_email_edit);
        password = findViewById(R.id.login_password_edit);

        newuser = findViewById(R.id.new_user_text);

        firebaseAuth = FirebaseAuth.getInstance();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));


        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginpage.this,signup.class);
                startActivity(intent);
            }
        });

        loginGetin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = email.getText().toString();
                String getPassword = password.getText().toString();
                if (getEmail.isEmpty() || getPassword.isEmpty()){
                    Toast.makeText(loginpage.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                }else {

                    firebaseAuth.signInWithEmailAndPassword(getEmail, getPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(loginpage.this, "Logged In", Toast.LENGTH_SHORT).show();


                                    if (getEmail.equals("admin@gmail.com") && getPassword.equals("admin123")){
                                        Intent intent = new Intent(loginpage.this, admin_home.class);
                                        startActivity(intent);
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("AuthStatus", "admin");
                                        editor.apply();
                                    }else{
                                        Intent intent = new Intent(loginpage.this, home.class);
                                        startActivity(intent);
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("AuthStatus", "user");
                                        editor.apply();
                                    }


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(loginpage.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}