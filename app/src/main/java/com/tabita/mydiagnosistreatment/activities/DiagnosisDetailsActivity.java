package com.tabita.mydiagnosistreatment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tabita.mydiagnosistreatment.utils.MedicationAdapter;
import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;

import java.util.ArrayList;
import java.util.List;

public class DiagnosisDetailsActivity extends AppCompatActivity {

    private TextView diagnosisNameView;
    private TextView diagnosisPeriodView;
    private ListView medicationListView;
    private TextView notesView;
    private List<Diagnosis> diagnosisList = new ArrayList<>();
    private ArrayAdapter<Diagnosis> adapter;
    private ListView diagnosisListView;

    private Diagnosis diagnosis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_details);

        diagnosis = (Diagnosis) getIntent().getSerializableExtra(DiagnosisFragment.KEY);


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

        /*//Populate diagnosisList list with diagnosis names
        diagnosisListView = findViewById(R.id.medication_list_dashboardFragment);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,diagnosisList);
        //diagnosisListView.setAdapter(adapter);
        diagnosisListView.setAdapter(adapter);

        diagnosisListView.setAdapter(
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));*/
    }

    public void subscribe(View view) {
        Intent intent = new Intent();
        intent.putExtra(DiagnosisFragment.KEY, diagnosis);
        setResult(RESULT_OK, intent); //setResult returns data back to its parent

     /*   for (Diagnosis cursor : diagnosisList) {
            if (cursor.getName().equals(diagnosisNameView.toString())) {

                Intent intent = new Intent(this, DashboardFragment.class);
                intent.putExtra(DiagnosisFragment.KEY, cursor);
                setResult(RESULT_OK, intent);

            }
        }*/

        Toast.makeText(this, "You are now subscribed to " + diagnosis.getName() + " treatment", Toast.LENGTH_SHORT).show();

        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DiagnosisFragment.PICK_CONTACT_REQUEST) {
            if (resultCode == DiagnosisDetailsActivity.RESULT_OK) {
                ClientActivity clientActivity = (ClientActivity) getBaseContext();
                clientActivity.setCurrentTreatment((Diagnosis) data.getSerializableExtra(DiagnosisFragment.KEY));
            }
        }
    }
}
