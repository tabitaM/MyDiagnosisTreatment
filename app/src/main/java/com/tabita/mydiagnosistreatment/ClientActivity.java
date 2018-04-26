package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;

public class ClientActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ArrayList<String> medsList = new ArrayList<>();
    private ListView listViewmeds;
    private ArrayAdapter<String> arrayAdapter;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("diagnosis");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Diagnosis value = dataSnapshot.getValue(Diagnosis.class);
                //System.out.print(value);
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Diagnosis value = childSnapshot.getValue(Diagnosis.class);
                }


                listViewmeds = (ListView) findViewById(R.id.medsListView);
                arrayAdapter = new ArrayAdapter<String>(ClientActivity.this, android.R.layout.simple_list_item_1, medsList);
                listViewmeds.setAdapter(arrayAdapter);

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        // Get the value from the DataSnapshot and add it to the meds' list
                        Diagnosis diag = (Diagnosis) dataSnapshot.getValue(Diagnosis.class);
                        String diagString = String.valueOf(diag);
                        arrayAdapter.add(diagString);

                        // Notify the ArrayAdapter that there was a change
                        arrayAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }

            public void logout(View view) {
                FirebaseAuth.getInstance().signOut();
                finish();
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
            });
}
}
