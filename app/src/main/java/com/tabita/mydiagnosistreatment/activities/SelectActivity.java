package com.tabita.mydiagnosistreatment.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.utils.UserType;

public class SelectActivity extends AppCompatActivity {

    public static String KEY = "STATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        TextView titleView = findViewById(R.id.title);
        titleView.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/PT_Sans-Narrow-Web-Regular.ttf"));

        View doctor = findViewById(R.id.section_doctor);
        doctor.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(KEY, UserType.doctor);
            startActivity(intent);
        });

        View patient = findViewById(R.id.section_patient);
        patient.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(KEY, UserType.patient);
            startActivity(intent);
        });

    }

}
