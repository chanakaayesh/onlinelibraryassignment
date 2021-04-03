package com.chanaka.onlinelibrary.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chanaka.onlinelibrary.R;
import com.chanaka.onlinelibrary.dbhadler.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {

    private EditText email, password,username;
    private Button regBtn;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    UserModel usemodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        setTitle("Registration");
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        usemodel = new UserModel();
        reference = FirebaseDatabase.getInstance().getReference().child("Member");




        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(password.getText().toString().length() <6){
                    password.setError("password must be more than 6 digits");
                }

                else{
                    registerNewUser();

                }
            }
        });
    }

    private void registerNewUser() {
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

        mAuth.createUserWithEmailAndPassword(emails, passwords)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id1 =  mAuth.getCurrentUser().getUid();
                            String usernames =username.getText().toString();
                            usemodel.setId(id1);
                            usemodel.setName(usernames);
                            reference.child(id1).setValue(usemodel);

                            Toast.makeText(getApplicationContext(), "Registration successful!  ", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);

                            Intent intent = new Intent(registration.this, LoginUser.class);
                            startActivity(intent);
                            System.out.println("Generated id is :"+id1);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void openLoginUi(View view) {

        Intent intent = new Intent(registration.this,LoginUser.class);
        startActivity(intent);
    }
}