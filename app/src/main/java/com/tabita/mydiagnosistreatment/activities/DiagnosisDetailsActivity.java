package com.tabita.mydiagnosistreatment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;
import com.tabita.mydiagnosistreatment.utils.MedicationAdapter;
import com.tabita.mydiagnosistreatment.utils.UserType;

import java.util.List;

public class DiagnosisDetailsActivity extends AppCompatActivity {

    private Diagnosis diagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        diagnosis = (Diagnosis) getIntent().getSerializableExtra(DiagnosisFragment.KEY);

        // Figure out userType and change accordingly
        Button subscribe = findViewById(R.id.subscribe);
        UserType userType = (UserType) getIntent().getSerializableExtra(SelectActivity.KEY);
        switch (userType) {
            case patient:
                subscribe.setVisibility(View.VISIBLE);
                break;
            case doctor:
                subscribe.setVisibility(View.GONE);
                break;
        }

        // Diagnosis Name
        TextView diagnosisNameView = findViewById(R.id.name);
        diagnosisNameView.setText(diagnosis.getName());

        // Diagnosis Period
        TextView diagnosisPeriodView = findViewById(R.id.period);
        diagnosisPeriodView.setText(diagnosis.getTreatment().getPeriod());

        // Medication List
        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
        ListView medicationListView = findViewById(R.id.medication_list);
        medicationListView.setAdapter(new MedicationAdapter(this, medicationList));

        // Notes
        TextView notesView = findViewById(R.id.notes);
        notesView.setText((diagnosis.getTreatment().getNotes()));

    }

    public void subscribe(View view) {
        Intent intent = new Intent();
        intent.putExtra(DiagnosisFragment.KEY, diagnosis);
        setResult(RESULT_OK, intent); //setResult returns data back to its parent

        Toast.makeText(this, "You are now subscribed to " + diagnosis.getName() + " treatment", Toast.LENGTH_SHORT).show();

        finish();
    }

}
