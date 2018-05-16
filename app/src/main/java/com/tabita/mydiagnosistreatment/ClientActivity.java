package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private DashboardFragment dashboardFragment;
    private DiagnosisFragment diagnosisFragment;
    private PastTreatmentsFragment pastTreatmentsFragment;

    private BottomNavigationView navigation;

    private Diagnosis currentTreatment;
    private List<Diagnosis> diagnosisList = new ArrayList<>();
    private List<Diagnosis> pastTreatmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    setFragment(dashboardFragment);
                    return true;
                case R.id.navigation_diagnosis:
                    setFragment(diagnosisFragment);
                    return true;
                case R.id.navigation_past_treatment:
                    setFragment(pastTreatmentsFragment);
                    return true;
            }
            return false;
        });

        dashboardFragment = new DashboardFragment();
        diagnosisFragment = new DiagnosisFragment();
        pastTreatmentsFragment = new PastTreatmentsFragment();

        setFragment(dashboardFragment);
        getDiagnosisListFromFirebase();
    }

    private void getDiagnosisListFromFirebase() {
        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("diagnosis");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Diagnosis diagnosis = postSnapshot.getValue(Diagnosis.class);
                    diagnosisList.add(diagnosis);
                }

                diagnosisFragment.setDiagnosisList(diagnosisList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read values diagnosis from firebase.", error.toException());
            }
        });
    }

    public void setCurrentTreatment(Diagnosis diagnosis) {

        currentTreatment = diagnosis;
        dashboardFragment.setCurrentTreatment(diagnosis);

        //
        // pastTreatmentList.add(diagnosis);
        // pastTreatmentsFragment.setPastTreatmentList(pastTreatmentList);

        setFragment(dashboardFragment);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }

}
