package com.tabita.mydiagnosistreatment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public Diagnosis currentTreatment;
    private TextView currentTreatmentView;
    private TextView periodDoseView;
    private TextView periodDoseTextView;
    String currentTime = DateFormat.getDateInstance().format(new Date());
    TextView dateView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        dateView = view.findViewById(R.id.date);
        periodDoseView = view.findViewById(R.id.periodDoseValues);
        periodDoseTextView = view.findViewById(R.id.periodDoseText);
        String messageString=periodDoseTextView.getText().toString();

        dateView.setText(currentTime);
        if (currentTreatment != null) {
            periodDoseTextView.setText(messageString);
            currentTreatmentView.setText(currentTreatment.getName());
            periodDoseView.setText(currentTreatment.getTreatment().getPeriod());

        }

        return view;
    }

    public void setCurrentTreatment(Diagnosis currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    public void unsubscribe(View view){
    }
}
