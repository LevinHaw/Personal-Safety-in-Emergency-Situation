package com.example.pses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    TextView alreadyhaveaccount;
    EditText Inputemail, Inputpassword, InputConfirmPassword;
    Button btnSignUp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        alreadyhaveaccount = findViewById(R.id.signin);
        Inputemail = findViewById(R.id.email);
        Inputpassword = findViewById(R.id.password);
        InputConfirmPassword = findViewById(R.id.confirmPass);
        btnSignUp = findViewById(R.id.signbtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        alreadyhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    private void registration() {
        String email = Inputemail.getText().toString();
        String password = Inputpassword.getText().toString();
        String ConfirmPassword = InputConfirmPassword.getText().toString();

        if (!email.matches(emailPattern)) {
            Inputemail.setError("Enter correct email");
        } else if (password.isEmpty() || password.length() < 6) {
            Inputpassword.setError("Enter your password again. Password must be at least >6 characters long");
        } else if (!password.equals(ConfirmPassword)) {
            InputConfirmPassword.setError("Password not match");
        } else {
            progressDialog.setMessage("Please wait....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        UserToDashboard();
                        Toast.makeText(Signup.this, "Register is successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Signup.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void UserToDashboard() {
        Intent intent = new Intent(Signup.this,BottomNav.class);
        startActivity(intent);
        finish();
    }
}