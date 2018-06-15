package com.tabita.mydiagnosistreatment.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

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

    public DiagnosisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_diagnosis, container, false);
        ListView diagnosisListView = view.findViewById(R.id.diagnosis);
        SearchView searchBox = view.findViewById(R.id.search);
        searchBox.setQueryHint("Search...");

        //Populate diagnosisList list with diagnosis names
        diagnosisListView.setAdapter(
                new ArrayAdapter<>(Objects.requireNonNull(getActivity()),
                        android.R.layout.simple_list_item_1,
                        diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        diagnosisListView.setOnItemClickListener((parent, listView, position, id) -> {

            String diagnosisName = parent.getItemAtPosition(position).toString();
            for (Diagnosis cursor : diagnosisList) {
                if (cursor.getName().equals(diagnosisName)) {

                    // Start DiagnosisDetailsActivity Activity
                    Intent intent = new Intent(getActivity(), DiagnosisDetailsActivity.class);
                    intent.putExtra(KEY, cursor);
                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
                }
            }
        });

        diagnosisListView.setTextFilterEnabled(true);

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)) {
                    diagnosisListView.clearTextFilter();
                } else {
                    diagnosisListView.setFilterText(s);
                }
                return true;
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == DiagnosisDetailsActivity.RESULT_OK) {
            ClientActivity clientActivity = (ClientActivity) getActivity();
            if (clientActivity == null) return;
            clientActivity.setCurrentTreatment((Diagnosis) data.getSerializableExtra(KEY));
        }
    }

    public void setDiagnosisList(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

}
