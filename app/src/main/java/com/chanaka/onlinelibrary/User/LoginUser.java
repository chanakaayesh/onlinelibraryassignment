package com.chanaka.onlinelibrary.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chanaka.onlinelibrary.MainActivity;
import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.book.View_book_list;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity {
    private EditText email, password;
    private Button loginBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    UserModel usemodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        setTitle("Login");
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        loginBtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        usemodel = new UserModel();
        System.out.println("User Id is :"+usemodel.getId());



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount();
            }
        });
    }

    private void loginUserAccount() {
        progressBar.setVisibility(View.VISIBLE);

        String emails, passwords;
        emails = email.getText().toString();
        passwords = password.getText().toString();

        if (TextUtils.isEmpty(emails)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(passwords)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id1 =  mAuth.getCurrentUser().getUid();//get UserId
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(LoginUser.this, View_book_list.class);

                            startActivity(intent);
                            UserModel userModel =new UserModel();
                            userModel. setDupId(id1);

                        }
                        else {
                            email.setError("Check emails");
                            email.requestFocus();
                            password.setError("Check Password");
                            password.requestFocus();


                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }


    public void openregisterUi(View view) {
        Intent intent = new Intent(LoginUser.this,registration.class);
        startActivity(intent);

    }
}