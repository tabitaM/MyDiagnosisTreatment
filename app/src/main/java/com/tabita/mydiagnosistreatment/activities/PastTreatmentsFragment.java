package com.tabita.mydiagnosistreatment.activities;


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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class PastTreatmentsFragment extends Fragment {

    public PastTreatmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_treatments, container, false);

        ClientActivity clientActivity = (ClientActivity) getActivity();
        List<Diagnosis> pastTreatmentList = Objects.requireNonNull(clientActivity).getPastTreatmentList();

        // get GUI references
        ListView pastTreatmentListView = view.findViewById(R.id.past_treatments_list);

        pastTreatmentListView.setAdapter(
                new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                        android.R.layout.simple_list_item_1,
                        pastTreatmentList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        return view;
    }

}
