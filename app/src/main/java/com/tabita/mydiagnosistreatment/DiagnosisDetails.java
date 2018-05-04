package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisDetails extends AppCompatActivity {

    private TextView diagnosisNameView;
    private TextView diagnosisPeriodView;
    private ListView medicationNameDoseView;
    //private TextView medicationDoseView;
    private TextView notesView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> nameDoseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        Diagnosis diagnosis = (Diagnosis) getIntent().getSerializableExtra(ClientActivity.KEY);

        diagnosisNameView = findViewById(R.id.name);
        diagnosisNameView.setText(diagnosis.getName());

        diagnosisPeriodView = findViewById(R.id.period);
        diagnosisPeriodView.setText(diagnosis.getTreatment().getPeriod());

        medicationNameDoseView = findViewById(R.id.medication_name_dose);
        adapter = new ArrayAdapter<>(DiagnosisDetails.this, R.layout.activity_diagnosis_details);
        medicationNameDoseView.setAdapter(adapter);

        /*medicationDoseView = findViewById(R.id.medication_dose);
        medicationDoseView.setText(diagnosis.getTreatment().getMedication().get(0).getDose());*/

        notesView = findViewById(R.id.notes);
        notesView.setText((diagnosis.getTreatment().getNotes()));

    }
}
