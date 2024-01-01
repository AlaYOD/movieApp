package com.example.weatherapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
        private EditText userEmail,userPass;
        private Button buttonLogin,registerMove;
        private String email,password;
        public static final String NAME = "NAME";
        public static final String PASS = "PASS";
        public static final String FLAG = "FLAG";
        private boolean flag = false;
        ProgressBar progressBar;


        private SharedPreferences sharedPreferences;
        private SharedPreferences.Editor editor;
        FirebaseAuth mAuth;
        CheckBox checkbox;


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = findViewById(R.id.login_email);
        userPass = findViewById(R.id.login_password);
        checkbox = findViewById(R.id.check_pass);
        progressBar = findViewById(R.id.progressBar);
        buttonLogin = findViewById(R.id.login_btn);
        registerMove = findViewById(R.id.move_to_register);

        mAuth = FirebaseAuth.getInstance();

        setupSharedPrefs();
        checkPrefs();

        registerMove.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , Register.class);
                startActivity(intent);
                finish();
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password;
                email = userEmail.getText().toString();
                password = userPass.getText().toString();
                if(TextUtils.isEmpty(email)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("MyAppTag", "This is a zero debug message");

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d("MyAppTag", "This is a first debug message");

                                progressBar.setVisibility(View.GONE);
                                Log.d("MyAppTag", "This is a second debug message");

                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                                    Log.d("MyAppTag", "This is a thered debug message");

                                    String name = userEmail.getText().toString();
                                    String password = userPass.getText().toString();

                                    if(checkbox.isChecked()){
                                        if(!flag) {
                                            editor.putString(NAME, name);
                                            editor.putString(PASS, password);
                                            editor.putBoolean(FLAG, true);
                                            editor.commit();
                                        }

                                    }

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }




    private void setupSharedPrefs() {
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
    }
    private void checkPrefs() {
        flag = sharedPreferences.getBoolean(FLAG, false);

        if(flag){
            String name = sharedPreferences.getString(NAME, "");
            String password = sharedPreferences.getString(PASS, "");
            userEmail.setText(name);
            userPass.setText(password);
            checkbox.setChecked(true);
        }
    }
}