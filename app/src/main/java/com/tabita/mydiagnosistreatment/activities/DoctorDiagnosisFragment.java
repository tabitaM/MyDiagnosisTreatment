package com.tabita.mydiagnosistreatment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.utils.UserType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DoctorDiagnosisFragment extends Fragment {

    static final int PICK_CONTACT_REQUEST = 1;
    public static final String KEY = "key";

    public DoctorDiagnosisFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diagnosis_doctor, container, false);

        // Get diagnosis list from parent
        DoctorActivity doctorActivity = (DoctorActivity) getActivity();
        List<Diagnosis> diagnosisList = Objects.requireNonNull(doctorActivity).getDiagnosisList();

        // get GUI references
        ListView diagnosisListView = view.findViewById(R.id.diagnosis);

        // populate list
        diagnosisListView.setAdapter(
                new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                        android.R.layout.simple_list_item_1,
                        diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        diagnosisListView.setOnItemClickListener((parent, listView, position, id) -> {

            String diagnosisName = parent.getItemAtPosition(position).toString();
            for (Diagnosis cursor : diagnosisList) {
                if (cursor.getName().equals(diagnosisName)) {

                    // Start DiagnosisDetailsActivity Activity
                    Intent intent = new Intent(getActivity(), DiagnosisDetailsActivity.class);
                    intent.putExtra(KEY, cursor);
                    intent.putExtra(SelectActivity.KEY, UserType.doctor);
                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
                }
            }
        });

        return view;
    }
}
