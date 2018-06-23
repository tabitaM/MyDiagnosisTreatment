package com.tabita.mydiagnosistreatment.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;
import com.tabita.mydiagnosistreatment.notification.MyNotification;
import com.tabita.mydiagnosistreatment.notification.Receiver;
import com.tabita.mydiagnosistreatment.utils.MedicationAdapter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {

    private TextView currentTreatmentView;
    private TextView periodView;
    private ImageView clockView;
    private ListView medicationListView;
    private Button unsubscribeButton;
    private TextView takePillsView1;
    private TextView takePillsView2;
    private TextView takePillsView3;

    private ClientActivity clientActivity;
    private Diagnosis currentTreatment;

    private MyNotification myNotification;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        // Get current treatment from parent
        clientActivity = (ClientActivity) getActivity();
        currentTreatment = Objects.requireNonNull(clientActivity).getCurrentTreatment();


        // get GUI references
        TextView dateView = view.findViewById(R.id.date);
        currentTreatmentView = view.findViewById(R.id.currentTreatment);
        unsubscribeButton = view.findViewById(R.id.unsubscribe);
        periodView = view.findViewById(R.id.period);
        medicationListView = view.findViewById(R.id.medication_list);
        clockView = view.findViewById(R.id.clock);
        takePillsView1 = view.findViewById(R.id.pills_to_take_1);
        takePillsView2 = view.findViewById(R.id.pills_to_take_2);
        takePillsView3 = view.findViewById(R.id.pills_to_take_3);

        // Show/Hide dash
        if (currentTreatment == null) {
            hideDash();
            return view;
        }

        showDash();

        // Notification
        myNotification = new MyNotification(clientActivity);
        String meds = currentTreatment.getTreatment().getMedication().stream().map(Medication::getName).collect(Collectors.toList()).toString();
        myNotification.setContentText(meds);
        setupRegularNotifications();

        // Set date
        dateView.setText(DateFormat.getDateInstance().format(new Date()));

        // Unsubscribe button
        unsubscribeButton.setOnClickListener(view1 -> {
            clientActivity.subscribeToTreatment(null);
            unsubscribeButton.setVisibility(View.GONE);
            hideDash();
        });

        // Current Treatment click
        currentTreatmentView.setOnClickListener(view1 -> {
            if (!currentTreatmentView.getText().equals(getString(R.string.dashboard_no_treatment))) {
                unsubscribeButton.setVisibility(View.VISIBLE);
            }
        });

        // Test notification
        clockView.setOnClickListener(view3 -> handleNotification());

        return view;
    }


    // Here is where we want to implement the alarm
    private void setupRegularNotifications() {
        Integer maxDose = getMaxDose();

        Integer interval = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        switch (maxDose) {
            case 1:
                interval = 1000 * 60 * 60 * 24;
                //calendar.set(Calendar.HOUR_OF_DAY, 9);
                break;
            case 2:
                interval = 1000 * 60 * 60 * 12;
                //calendar.set(Calendar.HOUR_OF_DAY, 9);
                //calendar.set(Calendar.HOUR_OF_DAY, 18);
                break;
            case 3:
                interval = 1000 * 60 * 60 * 8;
                //calendar.set(Calendar.HOUR_OF_DAY, 9);
                //calendar.set(Calendar.HOUR_OF_DAY, 12);
                //calendar.set(Calendar.HOUR_OF_DAY, 18);
                break;
            case 4:
                interval = 1000 * 60 * 60 * 6;
                //calendar.set(Calendar.HOUR_OF_DAY, 9);
                //calendar.set(Calendar.HOUR_OF_DAY, 12);
                //calendar.set(Calendar.HOUR_OF_DAY, 18);
                //calendar.set(Calendar.HOUR_OF_DAY, 22);
                break;
            case 6:
                interval = 1000 * 60 * 60 * 4;
                //calendar.set(Calendar.HOUR_OF_DAY, 9);
                //calendar.set(Calendar.HOUR_OF_DAY, 12);
                //calendar.set(Calendar.HOUR_OF_DAY, 16);
                //calendar.set(Calendar.HOUR_OF_DAY, 18);
                //calendar.set(Calendar.HOUR_OF_DAY, 22);
                break;
        }

        Intent notifyIntent = new Intent(clientActivity, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(clientActivity, 1234, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) clientActivity.getSystemService(Context.ALARM_SERVICE);
        Objects.requireNonNull(alarmManager).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Objects.requireNonNull(interval), pendingIntent);
//        Objects.requireNonNull(alarmManager).set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private Integer getMaxDose() {
        Diagnosis treatment = Objects.requireNonNull(getTreatment());
        Integer maxDose = Integer.parseInt(treatment.getTreatment().getMedication().get(0).getDose());
        for (Medication medication : treatment.getTreatment().getMedication()) {
            Integer dose = Integer.parseInt(medication.getDose());
            if (maxDose < dose) {
                maxDose = dose;
            }
        }

        return maxDose;
    }

    private void handleNotification() {
        MyNotification.sendNotification();

        // Check Medication dose are all 0 -> add to PastTreatments
        if (checkMedicationDoseZero()) {
            clientActivity.addPastTreatment(currentTreatment);
            clientActivity.subscribeToTreatment(null);
            hideDash();
            return;
        }

        // reduce number of pills to take
        for (Medication medication : currentTreatment.getTreatment().getMedication()) {
            String dose = medication.getDose();
            Integer doseInt = Integer.parseInt(dose);
            if (doseInt != 0) {
                doseInt--;
            }
            medication.setDose(doseInt.toString());
        }

        // update client currentTreatment
        clientActivity.subscribeToTreatment(currentTreatment);

        // refresh GUI
        showDash();
    }

    private Boolean checkMedicationDoseZero() {
        Boolean ok = true;
        for (Medication medication : currentTreatment.getTreatment().getMedication()) {
            if (Integer.parseInt(medication.getDose()) > 1) {
                ok = false;
            }
        }

        return ok;
    }

    private void showDash() {
        currentTreatmentView.setText(currentTreatment.getName());
        periodView.setText(currentTreatment.getTreatment().getPeriod());


        List<Medication> medicationListForPreview = Objects.requireNonNull(getTreatment()).getTreatment().getMedication();
        medicationListView.setAdapter(new MedicationAdapter(clientActivity, medicationListForPreview));

        takePillsView1.setVisibility(View.VISIBLE);
        takePillsView2.setText(constructTakePillsText(currentTreatment.getTreatment().getMedication()));
        takePillsView3.setVisibility(View.VISIBLE);
    }

    private Diagnosis getTreatment() {
        return clientActivity.getDiagnosisList().stream()
                .filter(diagnosis -> diagnosis.getName().equals(currentTreatment.getName()))
                .findAny()
                .orElse(null);
    }

    private void hideDash() {
        currentTreatmentView.setText(R.string.dashboard_no_treatment);
        periodView.setVisibility(View.GONE);
        clockView.setVisibility(View.GONE);
        takePillsView1.setVisibility(View.GONE);
        takePillsView2.setVisibility(View.GONE);
        takePillsView3.setVisibility(View.GONE);
        medicationListView.setAdapter(null);
    }

    private String constructTakePillsText(List<Medication> medicationList) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < medicationList.size(); i++) {
            text.append(medicationList.get(i).getDose()).append(" pills of ").append(medicationList.get(i).getName());
            if (i != medicationList.size() - 1) {
                text.append("\n");
            }
        }

        return text.toString();
    }

}
