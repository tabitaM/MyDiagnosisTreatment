package com.tabita.mydiagnosistreatment.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Medication;

import java.util.List;

public class MedicationAdapter extends ArrayAdapter<Medication> {

    public MedicationAdapter(Context context, List<Medication> medications) {
        super(context, 0, medications);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Get the data item for this position
        Medication medication = getItem(position);
        if (medication == null) {
            return convertView;
        }

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.medication_list_item, parent, false);
        }

        // Lookup view for data population
        TextView nameView = convertView.findViewById(R.id.name);
        TextView doseView = convertView.findViewById(R.id.dose);

        // Populate the data into the template view using the data object
        nameView.setText(medication.getName());
        doseView.setText(medication.getDose() + "/day");

        // Return the completed view to render on screen
        return convertView;
    }
}
