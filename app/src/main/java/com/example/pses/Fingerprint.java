package com.example.pses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class Fingerprint extends AppCompatActivity {

    TextView tvMessage;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        tvMessage = findViewById(R.id.tv_message);
        btnLogin = findViewById(R.id.btn_login);

        BiometricManager manager = BiometricManager.from(this);

        switch (manager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                tvMessage.setText("Touch the fingerprint sensor");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                tvMessage.setText("This device doesn't have biometric sensor");
                btnLogin.setVisibility(View.GONE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                tvMessage.setText("This device doesn't have fingerprint," + "Please check your security setting");
                btnLogin.setVisibility(View.INVISIBLE);
                break;
        }

        BiometricPrompt prompt = new BiometricPrompt(this,
                ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        //Display Toast
                        Toast.makeText(getApplicationContext(),
                                "Login Successfull.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Fingerprint.this, BottomNav.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                    }
                });
        //Initialize prompt info
        BiometricPrompt.PromptInfo info = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric")
                .setNegativeButtonText("Cancel")
                .build();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prompt.authenticate(info);
            }
        });
    }
}