package com.tabita.mydiagnosistreatment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {

    private AddDiagnosisFragment addDiagnosisFragment;
    private DoctorDiagnosisFragment doctorDiagnosisFragment;
    private BottomNavigationView navigation;

    private List<Diagnosis> diagnosisList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_add_diagnosis:
                    setFragment(addDiagnosisFragment);
                    return true;
                case R.id.navigation_diagnosis:
                    setFragment(doctorDiagnosisFragment);
                    return true;
            }
            return false;
        });

        addDiagnosisFragment = new AddDiagnosisFragment();
        doctorDiagnosisFragment = new DoctorDiagnosisFragment();

        getDiagnosisListFromFirebase();

        setFragment(addDiagnosisFragment);
        navigation.setSelectedItemId(R.id.navigation_add_diagnosis);
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();
    }

    public void changeToDiagnosisList(){
        setFragment(doctorDiagnosisFragment);
        navigation.setSelectedItemId(R.id.navigation_diagnosis);
    }

    private void getDiagnosisListFromFirebase() {

        final DatabaseReference myRefDiagnosis = FirebaseDatabase.getInstance().getReference().child("diagnosis");
        myRefDiagnosis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Diagnosis diagnosis = postSnapshot.getValue(Diagnosis.class);
                    if (diagnosis != null) {
                        diagnosisList.add(diagnosis);
                    }
                }
                myRefDiagnosis.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read values diagnosis from firebase.", error.toException());
            }
        });
    }

    public List<Diagnosis> getDiagnosisList() {
        return diagnosisList;
    }
}
