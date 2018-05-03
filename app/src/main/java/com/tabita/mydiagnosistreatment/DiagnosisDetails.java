package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

public class DiagnosisDetails extends AppCompatActivity {

    private TextView diagnosisNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        Diagnosis diagnosis = (Diagnosis) getIntent().getSerializableExtra(ClientActivity.KEY);

        diagnosisNameView = findViewById(R.id.name);
        diagnosisNameView.setText(diagnosis.getName());

    }
}
