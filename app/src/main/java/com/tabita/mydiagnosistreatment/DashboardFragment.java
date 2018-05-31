package com.tabita.mydiagnosistreatment;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public Diagnosis currentTreatment;
    private TextView currentTreatmentView;
    private TextView periodDoseView;
    private TextView periodDoseTextView;
    private TextView medicationTextView;
    private Button unsubscribeButton;
    private View medicationDelimiter;
    private Button alarmButton;
    private Diagnosis diagnosis;
    private ListView medicationListView;


    String currentTime = DateFormat.getDateInstance().format(new Date());
    TextView dateView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        diagnosis = (Diagnosis) getActivity().getIntent().getSerializableExtra(DiagnosisFragment.KEY);

        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        dateView = view.findViewById(R.id.date);
        periodDoseView = view.findViewById(R.id.periodDoseValues);
        periodDoseTextView = view.findViewById(R.id.periodDoseText);
        medicationTextView = view.findViewById(R.id.medication_text);
        unsubscribeButton = view.findViewById(R.id.unsubscribe);
        medicationDelimiter = view.findViewById(R.id.medication_delimiter);
        alarmButton = view.findViewById(R.id.alarm_button);

       /* // Medication List
        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
        medicationListView = view.findViewById(R.id.medication_list);
        medicationListView.setAdapter(new MedicationAdapter(getContext(), medicationList));*/

        dateView.setText(currentTime);
        if (currentTreatment != null) {
            periodDoseTextView.setText("Period");
            currentTreatmentView.setText(currentTreatment.getName());
            periodDoseView.setText(currentTreatment.getTreatment().getPeriod());
            medicationTextView.setText("Medication");
        }
        else{
            unsubscribeButton.setVisibility(view.GONE);
            medicationDelimiter.setVisibility(view.GONE);
            alarmButton.setVisibility(view.GONE);
        }


        unsubscribeButton.setOnClickListener(this::unsubscribe);
        alarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
                AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()*1000,pendingIntent);*/
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getFragmentManager(),"Time Picker");
            }
        });
        return view;
    }

    /*@Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView text = view.findViewById(R.id.text_view);
        text.setText("Hour: "+ hourOfDay + "Minute: " + minute);
    }*/

    public void setCurrentTreatment(Diagnosis currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    public void unsubscribe(View view){
    }

    public void startAlarm(View view){
        AlarmManager manager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(getActivity(), AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
    }
}
