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

public class signup extends AppCompatActivity {

    EditText Username,Email,Password;

    Button SignupGetin;
    Window window;

    FirebaseAuth firebaseAuth;

    TextView ALready;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        Username =findViewById(R.id.signup_user_edit_text);
        Email=findViewById(R.id.signup_email_edit_text);
        Password =findViewById(R.id.signup_password_edit_text);
        ALready = findViewById(R.id.have_an_acc);

        SignupGetin = findViewById(R.id.btn_signup_getin);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        window=this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.white));

        ALready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signup.this,loginpage.class);
                startActivity(intent);
            }
        });


        SignupGetin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail = Email.getText().toString();
                String getPassword = Password.getText().toString();

                if (getEmail.isEmpty() || getPassword.isEmpty()){
                    Toast.makeText(signup.this, "Fields cannot be empty!!", Toast.LENGTH_SHORT).show();
                }else {
                    firebaseAuth.createUserWithEmailAndPassword(getEmail, getPassword)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(signup.this, "User account created", Toast.LENGTH_SHORT).show();
                                    if (getEmail.equals("admin@gmail.com") && getPassword.equals("admin123")){
                                        Intent intent = new Intent(signup.this, admin_home.class);
                                        startActivity(intent);
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                        editor.putString("AuthStatus", "admin");
                                        editor.apply();
                                    }else{
                                        Intent intent = new Intent(signup.this, home.class);
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
                                    Toast.makeText(signup.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}