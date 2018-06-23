package com.tabita.mydiagnosistreatment.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.R;
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

    private static String[] userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Intent intent = getIntent();
        String emailText = intent.getStringExtra("KEY");
        userID = emailText.split("[\\p{Punct}@]");

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

        setFragment(diagnosisFragment);
        navigation.setSelectedItemId(R.id.navigation_diagnosis);

        getCurrentDiagnosisFromFirebase();
        getDiagnosisListFromFirebase();
        getPastTreatmentsFromFirebase();

    }

    private void getCurrentDiagnosisFromFirebase() {

        final DatabaseReference myRefUserDiagnosis = FirebaseDatabase.getInstance().getReference().child("users");
        myRefUserDiagnosis.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Diagnosis userTreatment = dataSnapshot.child(userID[0]).child("diagnosis").getValue(Diagnosis.class);
                if (userTreatment != null && currentTreatment == null) {
                    setCurrentTreatment(userTreatment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read values diagnosis from firebase.", error.toException());
            }
        });

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read values diagnosis from firebase.", error.toException());
            }
        });
    }

    private void getPastTreatmentsFromFirebase() {

        final DatabaseReference myRefUserPastTreatments = FirebaseDatabase.getInstance().getReference().child("users");
        myRefUserPastTreatments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.child(userID[0]).child("pastTreatments").getChildren()) {
                    Diagnosis pastDiagnosis = postSnapshot.getValue(Diagnosis.class);
                    if (pastDiagnosis != null && !pastTreatmentList.contains(pastDiagnosis)) {
                        pastTreatmentList.add(pastDiagnosis);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("ERROR", "Failed to read values diagnosis from firebase.", error.toException());
            }
        });
    }

    private void setCurrentDiagnosisToFirebase() {
        DatabaseReference myRefUserDiagnosis = FirebaseDatabase.getInstance().getReference().child("users");
        myRefUserDiagnosis.child(userID[0]).child("diagnosis").setValue(currentTreatment);
    }

    private void setPastTreatmentsToFirebase() {
        DatabaseReference myRefPastTreatments = FirebaseDatabase.getInstance().getReference().child("users");
        myRefPastTreatments.child(userID[0]).child("pastTreatments").setValue(pastTreatmentList);
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, fragment);
        fragmentTransaction.commit();
    }

    public void subscribeToTreatment(Diagnosis diagnosis) {
        // Set current treatment for ClientActivity and DashboardFragment and Firebase
        setCurrentTreatment(diagnosis);
        setCurrentDiagnosisToFirebase();

        // Highlight dashboardFragment
        setFragment(dashboardFragment);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    public void setCurrentTreatment(Diagnosis diagnosis) {
        currentTreatment = diagnosis;
    }

    public void addPastTreatment(Diagnosis diagnosis) {
        if (!pastTreatmentList.contains(diagnosis)) {
            pastTreatmentList.add(diagnosis);
            setPastTreatmentsToFirebase();
        }
    }

    public Diagnosis getCurrentTreatment() {
        return currentTreatment;
    }

    public List<Diagnosis> getDiagnosisList() {
        return diagnosisList;
    }

    public List<Diagnosis> getPastTreatmentList() {
        return pastTreatmentList;
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
