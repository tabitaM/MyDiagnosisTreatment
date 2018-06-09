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
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
public class DashboardFragment extends Fragment implements TimePickerDialog.OnTimeSetListener{

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
    private EditText editTextTitle;
    private Button sendOnChannel;
    private Button cancelAlarmButton;
    private NotificationHelper mNotificationHelper;


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
        //editTextTitle = view.findViewById(R.id.edit_text_title);
        sendOnChannel = view.findViewById(R.id.send_on_channel);
        mNotificationHelper = new NotificationHelper(getContext());
        cancelAlarmButton = view.findViewById(R.id.button_cancel_alarm);

        /*// Medication List to fill list from DashboardFragment
        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
        medicationListView = view.findViewById(R.id.medication_list_dashboardFragment);
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
            cancelAlarmButton.setVisibility(view.GONE);
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

        sendOnChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOnChannel();
            }
        });

        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });

        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
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

    /*public void startAlarm(View view){
        AlarmManager manager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(getActivity(), AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
    }*/

     public void sendOnChannel(){
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification();
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //Toast.makeText(ClientActivity.this,"Alarm set to "+ hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND,0);

        startAlarm(c);
    }

    public void startAlarm(Calendar c){
         AlarmManager alarmManager = (AlarmManager) (getContext().getSystemService(Context.ALARM_SERVICE));
         Intent intent = new Intent(getContext(), AlertReceiver.class);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);

         alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) (getContext().getSystemService(Context.ALARM_SERVICE));
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1,intent,0);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(getContext(),"Alarm canceled.", Toast.LENGTH_LONG).show();
    }
}
