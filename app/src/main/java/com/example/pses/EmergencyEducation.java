package com.example.pses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EmergencyEducation extends AppCompatActivity {

    CardView accident, disaster, pandemic, violence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_education);

        accident = findViewById(R.id.cv_accident);
        disaster = findViewById(R.id.cv_disaster);
        pandemic = findViewById(R.id.cv_pandemic);
        violence = findViewById(R.id.cv_violence);

        accident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmergencyEducation.this, Accident.class));
            }
        });

        disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmergencyEducation.this, Disaster.class));
            }
        });

        pandemic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmergencyEducation.this, Pandemic.class));
            }
        });

        violence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmergencyEducation.this, Violence.class));
            }
        });

    }
}