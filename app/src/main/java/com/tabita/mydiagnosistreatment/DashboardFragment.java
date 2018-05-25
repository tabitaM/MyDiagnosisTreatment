package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public Diagnosis currentTreatment;
    private TextView currentTreatmentView;
    private TextView periodDoseView;
    private TextView periodDoseTextView;
    private TextView medicationTextView;


    String currentTime = DateFormat.getDateInstance().format(new Date());
    TextView dateView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //diagnosis = (Diagnosis) getIntent().getSerializableExtra(DiagnosisFragment.KEY);

        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        dateView = view.findViewById(R.id.date);
        periodDoseView = view.findViewById(R.id.periodDoseValues);
        periodDoseTextView = view.findViewById(R.id.periodDoseText);
        medicationTextView = view.findViewById(R.id.medication_text);

        /*// Medication List
        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
        medicationListView = view.findViewById(R.id.medication_list);
        medicationListView.setAdapter(new MedicationAdapter(this, medicationList));*/

        dateView.setText(currentTime);
        if (currentTreatment != null) {
            periodDoseTextView.setText("Period");
            currentTreatmentView.setText(currentTreatment.getName());
            periodDoseView.setText(currentTreatment.getTreatment().getPeriod());
            medicationTextView.setText("Medication");
        }
        return view;
    }

    public void setCurrentTreatment(Diagnosis currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    public void unsubscribe(View view){
    }
}
