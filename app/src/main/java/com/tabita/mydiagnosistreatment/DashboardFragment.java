package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public Diagnosis currentTreatment;
    private TextView currentTreatmentView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        if (currentTreatment != null) {
            currentTreatmentView.setText(currentTreatment.getName());
        }

        return view;
    }

    public void setCurrentTreatment(Diagnosis currentTreatment) {
        this.currentTreatment = currentTreatment;
    }
}
