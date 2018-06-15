package com.tabita.mydiagnosistreatment.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.tabita.mydiagnosistreatment.R;
import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosisFragment extends Fragment {

    static final int PICK_CONTACT_REQUEST = 1;
    public static final String KEY = "key";

    private List<Diagnosis> diagnosisList = new ArrayList<>();

    private ListView diagnosisListView;

    public DiagnosisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_diagnosis, container, false);
        diagnosisListView = view.findViewById(R.id.diagnosis);
        EditText searchView = view.findViewById(R.id.search_input);
        Button searchButton = view.findViewById(R.id.search_submit_button);

        searchButton.setOnClickListener(v -> {

            // hide keyboard
            InputMethodManager imm = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(Context.INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);

            // validate input
            String searchText = searchView.getText().toString();
            if (TextUtils.isEmpty(searchText)) {
                searchView.setError(getString(R.string.error_input_symptoms));
                searchView.requestFocus();
                return;
            }

            // filter list
            List<Diagnosis> filteredDiagnosisList = new ArrayList<>();
            for (Diagnosis diagnosis : diagnosisList) {
                for (String keyword : diagnosis.getKeywords()) {
                    if (keyword.equals(searchText)) {
                        filteredDiagnosisList.add(diagnosis);
                    }
                }
            }

            // populate list
            diagnosisListView.setAdapter(
                    new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                            android.R.layout.simple_list_item_1,
                            filteredDiagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

            diagnosisListView.setOnItemClickListener((parent, listView, position, id) -> {

                String diagnosisName = parent.getItemAtPosition(position).toString();
                for (Diagnosis cursor : filteredDiagnosisList) {
                    if (cursor.getName().equals(diagnosisName)) {

                        // Start DiagnosisDetailsActivity Activity
                        Intent intent = new Intent(getActivity(), DiagnosisDetailsActivity.class);
                        intent.putExtra(KEY, cursor);
                        startActivityForResult(intent, PICK_CONTACT_REQUEST);
                    }
                }
            });
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == DiagnosisDetailsActivity.RESULT_OK) {
            ClientActivity clientActivity = (ClientActivity) getActivity();
            if (clientActivity == null) return;
            clientActivity.setTreatment((Diagnosis) data.getSerializableExtra(KEY));
        }
    }

    public void setDiagnosisList(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

}
