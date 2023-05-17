package com.example.pses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    ImageView logo;
    TextView text;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo = findViewById(R.id.iv);
        text = findViewById(R.id.tv);

        anim = AnimationUtils.loadAnimation(this, R.anim.anim1);

        logo.startAnimation(anim);
        text.startAnimation(anim);

        Intent intent = new Intent(SplashScreen.this, Login.class);

        Thread td = new Thread() {
            public void run() {
                try{
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(intent);
                    finish();
                }

            }
        };
        td.start();

    }
}