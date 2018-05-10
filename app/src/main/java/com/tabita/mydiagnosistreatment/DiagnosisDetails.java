package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;

import java.util.List;

public class DiagnosisDetails extends AppCompatActivity {

    private TextView diagnosisNameView;
    private TextView diagnosisPeriodView;
    private ListView medicationListView;
    private TextView notesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        Diagnosis diagnosis = (Diagnosis) getIntent().getSerializableExtra(DiagnosisFragment.KEY);

        // Diagnosis Name
        diagnosisNameView = findViewById(R.id.name);
        diagnosisNameView.setText(diagnosis.getName());

        // Diagnosis Period
        diagnosisPeriodView = findViewById(R.id.period);
        diagnosisPeriodView.setText(diagnosis.getTreatment().getPeriod());

        // Medication List
        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
        medicationListView = findViewById(R.id.medication_list);
        medicationListView.setAdapter(new MedicationAdapter(this, medicationList));

        // Notes
        notesView = findViewById(R.id.notes);
        notesView.setText((diagnosis.getTreatment().getNotes()));

    }
}
