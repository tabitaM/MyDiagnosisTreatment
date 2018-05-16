package com.tabita.mydiagnosistreatment;


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
public class PastTreatmentsFragment extends Fragment {

    private List<Diagnosis> pastTreatmentList = new ArrayList<>();
    ;

    public PastTreatmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_past_treatments, container, false);
        ListView pastTreatmentListView = view.findViewById(R.id.past_treatments_list);

        pastTreatmentListView.setAdapter(
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        pastTreatmentList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        return view;
    }

    public void setPastTreatmentList(List<Diagnosis> pastTreatmentList) {
        this.pastTreatmentList = pastTreatmentList;
    }
}
