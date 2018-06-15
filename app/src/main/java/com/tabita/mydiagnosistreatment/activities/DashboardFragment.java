package com.tabita.mydiagnosistreatment.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.NotificationHelper;
import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;
import com.tabita.mydiagnosistreatment.utils.MedicationAdapter;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    public Diagnosis currentTreatment;
    private TextView currentTreatmentView;
    private TextView periodView;
    private ImageView clockView;
    private ListView medicationListView;
    private TextView takePillsView;
    private Button unsubscribeButton;

    private TextView periodDoseTextView;
    private TextView medicationTextView;
    private View medicationDelimiter;
    private Button alarmButton;
    private EditText editTextTitle;
    private Button sendOnChannel;
    private Button cancelAlarmButton;
    private NotificationHelper mNotificationHelper;

    TextView dateView;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        dateView = view.findViewById(R.id.date);
        dateView.setText(DateFormat.getDateInstance().format(new Date()));

        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        periodView = view.findViewById(R.id.period);
        medicationListView = view.findViewById(R.id.medication_list);
        clockView = view.findViewById(R.id.clock);
        takePillsView = view.findViewById(R.id.takePills);
        unsubscribeButton = view.findViewById(R.id.unsubscribe);
        unsubscribeButton.setVisibility(View.GONE);
        unsubscribeButton.setOnClickListener(view1 -> {
            currentTreatment = null;
            ClientActivity clientActivity = (ClientActivity) getActivity();
            if (clientActivity == null) return;
            clientActivity.setTreatment(null);
            hideDash();
        });
        currentTreatmentView.setOnClickListener(view1 -> unsubscribeButton.setVisibility(View.VISIBLE));

        if (currentTreatment != null) {
            showDash();
        } else {
            hideDash();
        }

//            unsubscribeButton.setVisibility(View.GONE);
//            medicationDelimiter.setVisibility(View.GONE);
//            alarmButton.setVisibility(View.GONE);
//            cancelAlarmButton.setVisibility(View.GONE);

//        currentTreatmentView = view.findViewById(R.id.currentTreatment);
//        periodDoseView = view.findViewById(R.id.periodDoseValues);
//        periodDoseTextView = view.findViewById(R.id.periodDoseText);
//        medicationTextView = view.findViewById(R.id.medication_text);
//        unsubscribeButton = view.findViewById(R.id.unsubscribe);
//        medicationDelimiter = view.findViewById(R.id.medication_delimiter);
//        alarmButton = view.findViewById(R.id.alarm_button);
//        //editTextTitle = view.findViewById(R.id.edit_text_title);
//        sendOnChannel = view.findViewById(R.id.send_on_channel);
//        mNotificationHelper = new NotificationHelper(getContext());
//        cancelAlarmButton = view.findViewById(R.id.button_cancel_alarm);

        // Medication List to fill list from DashboardFragment
//        List<Medication> medicationList = diagnosis.getTreatment().getMedication();
//        medicationListView = view.findViewById(R.id.medication_list_dashboardFragment);
//        medicationListView.setAdapter(new MedicationAdapter(getContext(), medicationList));

//


//        unsubscribeButton.setOnClickListener(this::unsubscribe);
//        alarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
//                AlarmManager alarmManager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()*1000,pendingIntent);*/
//                DialogFragment timePicker = new TimePickerFragment();
//                timePicker.show(getActivity().getFragmentManager(), "Time Picker");
//            }
//        });

//        sendOnChannel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendOnChannel();
//            }
//        });
//
//        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelAlarm();
//            }
//        });
//
//        cancelAlarmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cancelAlarm();
//            }
//        });

        return view;
    }

    private void showDash() {
        currentTreatmentView.setText(currentTreatment.getName());
        periodView.setText(currentTreatment.getTreatment().getPeriod());
        List<Medication> medicationList = currentTreatment.getTreatment().getMedication();
        medicationListView.setAdapter(new MedicationAdapter(getActivity(), medicationList));
    }

    private void hideDash() {
        currentTreatmentView.setText(R.string.dashboard_no_treatment);
        periodView.setVisibility(View.GONE);
        clockView.setVisibility(View.GONE);
        takePillsView.setVisibility(View.GONE);
        medicationListView.setAdapter(null);
    }

    /*@Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView text = view.findViewById(R.id.text_view);
        text.setText("Hour: "+ hourOfDay + "Minute: " + minute);
    }*/

    public void setCurrentTreatment(Diagnosis currentTreatment) {
        this.currentTreatment = currentTreatment;
    }

    /*public void startAlarm(View view){
        AlarmManager manager = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(getActivity(), AlarmNotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);

        manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime()+3000,3000,pendingIntent);
    }*/

//    public void sendOnChannel() {
//        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification();
//        mNotificationHelper.getManager().notify(1, nb.build());
//    }

//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        //Toast.makeText(ClientActivity.this,"Alarm set to "+ hourOfDay + ":" + minute, Toast.LENGTH_LONG).show();
//        Calendar c = Calendar.getInstance();
//        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        c.set(Calendar.MINUTE, minute);
//        c.set(Calendar.SECOND, 0);
//
//        startAlarm(c);
//    }

//    public void startAlarm(Calendar c) {
//        AlarmManager alarmManager = (AlarmManager) (getContext().getSystemService(Context.ALARM_SERVICE));
//        Intent intent = new Intent(getContext(), AlertReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//    }
//
//    private void cancelAlarm() {
//        AlarmManager alarmManager = (AlarmManager) (getContext().getSystemService(Context.ALARM_SERVICE));
//        Intent intent = new Intent(getContext(), AlertReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, 0);
//
//        alarmManager.cancel(pendingIntent);
//        Toast.makeText(getContext(), "Alarm canceled.", Toast.LENGTH_LONG).show();
//    }
}
