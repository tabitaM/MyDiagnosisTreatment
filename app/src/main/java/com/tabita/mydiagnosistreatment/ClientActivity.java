package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClientActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ListView diagnosisListView;

    private List<Diagnosis> diagnosisList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_diagnosis:
                    mTextMessage.setText(R.string.title_diagnosis);
                    return true;
                case R.id.navigation_past_treatment:
                    mTextMessage.setText(R.string.title_past_treatments);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Gui
        mTextMessage = findViewById(R.id.message);
        diagnosisListView = findViewById(R.id.diagnosis);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("diagnosis");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Diagnosis diagnosis = postSnapshot.getValue(Diagnosis.class);
                    diagnosisList.add(diagnosis);
                }

                diagnosisListView.setAdapter(new ArrayAdapter<>(ClientActivity.this,
                        android.R.layout.simple_list_item_1, diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("blabla ", "Failed to read value.", error.toException());
            }
        });


        diagnosisListView.setOnItemClickListener((parent, view, position, id) -> {
            String diagnosisName = parent.getItemAtPosition(position).toString();
            for (Diagnosis cursor : diagnosisList) {
                if (cursor.getName().equals(diagnosisName)) {
                    // Trimite folosindu-te de intent tot obiectul cursor de tipul Diagnosis.
                    // Il va primi o activitate de detaliu pe tratament care va afisa toata informatia
                    Toast.makeText(this, cursor.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
