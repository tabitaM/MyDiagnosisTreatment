package com.tabita.mydiagnosistreatment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;
import com.tabita.mydiagnosistreatment.model.Treatment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddDiagnosisFragment extends Fragment {

    private TextView diagnosisName;
    private TextView keywords;
    private TextView period;
    private TextView medicationName1;
    private TextView medicationDose1;
    private TextView medicationName2;
    private TextView medicationDose2;
    private TextView medicationName3;
    private TextView medicationDose3;
    private TextView notes;

    private List<Diagnosis> diagnosisList;

    public AddDiagnosisFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_diagnosis, container, false);

        // get GUI references
        TextView date = view.findViewById(R.id.date);
        TextView loginUser = view.findViewById(R.id.login_user);
        diagnosisName = view.findViewById(R.id.diagnosis_name);
        keywords = view.findViewById(R.id.keywords);
        period = view.findViewById(R.id.period);
        medicationName1 = view.findViewById(R.id.medication1_name);
        medicationDose1 = view.findViewById(R.id.medication1_dose);
        medicationName2 = view.findViewById(R.id.medication2_name);
        medicationDose2 = view.findViewById(R.id.medication2_dose);
        medicationName3 = view.findViewById(R.id.medication3_name);
        medicationDose3 = view.findViewById(R.id.medication3_dose);
        notes = view.findViewById(R.id.notes);
        ImageView addButton = view.findViewById(R.id.add_diagnosis_button);

        date.setText(DateFormat.getDateInstance().format(new Date()));
        String firebaseEmail = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail();
        loginUser.setText(Objects.requireNonNull(firebaseEmail).split("[\\p{Punct}@]")[0]);

        // Add to diagnosis list
        DoctorActivity doctorActivity = (DoctorActivity) getActivity();
        diagnosisList = Objects.requireNonNull(doctorActivity).getDiagnosisList();

        addButton.setOnClickListener(view1 -> {

            // medication
            List<Medication> medicationList = new ArrayList<>();
            medicationList.add(new Medication(medicationName1.getText().toString(), medicationDose1.getText().toString()));
            if (medicationName2.getText() != null && !medicationName2.getText().toString().equals("")
                    && medicationDose2.getText() != null && !medicationDose2.getText().toString().equals("")) {
                medicationList.add(new Medication(medicationName2.getText().toString(), medicationDose2.getText().toString()));
            }
            if (medicationName3.getText() != null && !medicationName3.getText().toString().equals("")
                    && medicationDose3.getText() != null && !medicationDose3.getText().toString().equals("")) {
                medicationList.add(new Medication(medicationName3.getText().toString(), medicationDose3.getText().toString()));
            }

            // keywords
            String keywordsString = keywords.getText().toString();
            List<String> keywordsList = Arrays.asList(keywordsString.split(" "));

            // treatment
            Treatment treatment = new Treatment(notes.getText().toString(), period.getText().toString(), medicationList);

            // diagnosis
            Diagnosis newDiagnosis = new Diagnosis(diagnosisName.getText().toString(), keywordsList, treatment);

            diagnosisList.add(newDiagnosis);
            setDiagnosisListToFirebase();

            Toast.makeText(doctorActivity, "Added " + diagnosisName.getText().toString(), Toast.LENGTH_SHORT).show();

            // reset gui
            diagnosisName.setText("");
            keywords.setText("");
            period.setText("");
            medicationName1.setText("");
            medicationDose1.setText("");
            medicationName2.setText("");
            medicationDose2.setText("");
            medicationName3.setText("");
            medicationDose3.setText("");
            notes.setText("");

            doctorActivity.changeToDiagnosisList();
        });

        return view;
    }

    private void setDiagnosisListToFirebase() {
        DatabaseReference myRefPastTreatments = FirebaseDatabase.getInstance().getReference().child("diagnosis");
        myRefPastTreatments.setValue(diagnosisList);
    }


}
