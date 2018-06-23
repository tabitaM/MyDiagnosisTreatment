package com.tabita.mydiagnosistreatment.activities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;
import com.tabita.mydiagnosistreatment.model.Medication;
import com.tabita.mydiagnosistreatment.utils.MedicationAdapter;

import java.text.DateFormat;
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

    private NotificationCompat.Builder notificationBuilder;
    private NotificationChannel channel;
    private TextView dateView;

    private ClientActivity clientActivity;
    private Diagnosis currentTreatment;

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
        dateView = view.findViewById(R.id.date);
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
        notificationBuilder = new NotificationCompat.Builder(Objects.requireNonNull(getActivity()), "NOTIFICATION_CHANNEL")
                .setSmallIcon(R.drawable.ic_clock)
                .setContentTitle("It's time to take your pills")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setPriority(NotificationCompat.PRIORITY_MAX);
        String meds = currentTreatment.getTreatment().getMedication().stream().map(Medication::getName).collect(Collectors.toList()).toString();
        notificationBuilder.setContentText(meds);
        createNotificationChannel();
        setupNotification();

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
        clockView.setOnClickListener(view3 -> sendNotification());

        return view;
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel for API26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            channel = new NotificationChannel("NOTIFICATION_CHANNEL", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = Objects.requireNonNull(getActivity()).getSystemService(NotificationManager.class);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Here is where we want to implement the alarm
    private void setupNotification() {
        Integer maxDose = Integer.parseInt(currentTreatment.getTreatment().getMedication().get(0).getDose());
        for (Medication medication : currentTreatment.getTreatment().getMedication()) {
            Integer dose = Integer.parseInt(medication.getDose());
            if (maxDose < dose) {
                maxDose = dose;
            }
        }
        Toast.makeText(getActivity(), "MAX dose: " + maxDose, Toast.LENGTH_SHORT).show();
    }

    private void sendNotification() {
        // Check Medication dose are all 0 -> add to PastTreatments
        if (checkMedicationDoseZero()) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Objects.requireNonNull(getActivity()));
            notificationManager.notify(1234, notificationBuilder.build());
            clientActivity.addPastTreatment(currentTreatment);
            clientActivity.subscribeToTreatment(null);
            hideDash();
            return;
        }
        else {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Objects.requireNonNull(getActivity()));
            notificationManager.notify(1234, notificationBuilder.build());
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

        Diagnosis treatment = clientActivity.getDiagnosisList().stream()
                .filter(diagnosis -> diagnosis.getName().equals(currentTreatment.getName()))
                .findAny()
                .orElse(null);
        List<Medication> medicationListForPreview = Objects.requireNonNull(treatment).getTreatment().getMedication();
        medicationListView.setAdapter(new MedicationAdapter(getActivity(), medicationListForPreview));

        takePillsView1.setVisibility(View.VISIBLE);
        takePillsView2.setText(constructTakePillsText(currentTreatment.getTreatment().getMedication()));
        takePillsView3.setVisibility(View.VISIBLE);
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
