package com.tabita.mydiagnosistreatment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosisFragment extends Fragment {

    private List<Diagnosis> diagnosisList = new ArrayList<>();
    static final int PICK_CONTACT_REQUEST = 1;
    public static final String KEY = "key";

    public DiagnosisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagnosis, container, false);
        ListView diagnosisListView = view.findViewById(R.id.diagnosis);

        diagnosisListView.setAdapter(
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        diagnosisListView.setOnItemClickListener((parent, listView, position, id) -> {
            String diagnosisName = parent.getItemAtPosition(position).toString();
            for (Diagnosis cursor : diagnosisList) {
                if (cursor.getName().equals(diagnosisName)) {

                    // Start DiagnosisDetails Activity
                    Intent intent = new Intent(getActivity(), DiagnosisDetails.class);
                    intent.putExtra(KEY, cursor);
                    startActivityForResult(intent, PICK_CONTACT_REQUEST);

                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == DiagnosisDetails.RESULT_OK) {
                ClientActivity clientActivity = (ClientActivity) getActivity();
                clientActivity.setCurrentTreatment((Diagnosis) data.getSerializableExtra(KEY));
            }
        }
    }

    public void setDiagnosisList(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }
}
