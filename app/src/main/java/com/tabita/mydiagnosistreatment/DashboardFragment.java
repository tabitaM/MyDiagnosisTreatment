package com.tabita.mydiagnosistreatment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }

    private ListView diagnosisListView;
    private List<Diagnosis> diagnosisList = new ArrayList<>();
    public static final String KEY = "key";
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       rootview= inflater.inflate(R.layout.fragment_dashboard, container, false);
        diagnosisListView = rootview.findViewById(R.id.diagnosis);
         final DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("diagnosis");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Diagnosis diagnosis = postSnapshot.getValue(Diagnosis.class);
                        diagnosisList.add(diagnosis);
                    }

                    diagnosisListView.setAdapter(new ArrayAdapter<>(getActivity(),
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

                    Intent intent = new Intent(getActivity(), DiagnosisDetails.class);
                    intent.putExtra(KEY, cursor);
                    startActivity(intent);
                }
            }
        });
        return rootview;
    }
}
