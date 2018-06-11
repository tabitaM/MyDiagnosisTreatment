package com.tabita.mydiagnosistreatment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.tabita.mydiagnosistreatment.model.Diagnosis;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiagnosisFragment extends Fragment {

    private List<Diagnosis> diagnosisList = new ArrayList<>();
    static final int PICK_CONTACT_REQUEST = 1;
    public static final String KEY = "key";
    private SearchView searchBox;
    private ArrayAdapter<Diagnosis> adapter;

    public DiagnosisFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_diagnosis, container, false);
        ListView diagnosisListView = view.findViewById(R.id.diagnosis);
        searchBox = view.findViewById(R.id.search);
        searchBox.setQueryHint("Search...");

        //Populate diagnosisList list with diagnosis names
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, diagnosisList);
        diagnosisListView.setAdapter(adapter);

        diagnosisListView.setAdapter(
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1,
                        diagnosisList.stream().map(Diagnosis::getName).collect(Collectors.toList())));

        diagnosisListView.setOnItemClickListener((parent, listView, position, id) -> {

            String diagnosisName = parent.getItemAtPosition(position).toString();
            for (Diagnosis cursor : diagnosisList) {
                if (cursor.getName().equals(diagnosisName)) {

                    // Start DiagnosisDetails Activity
                    Intent intent = new Intent(getActivity(), DiagnosisDetails.class);
                    intent.putExtra(KEY, cursor);
                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
                }
            }
        });

        searchBox.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                //for (Diagnosis item:diagnosisList){
                //if(item.getName().equals(s))
                //diagnosisList.remove(item);
                //}
                //adapter.notifyDataSetChanged();
                return false;
            }
        });
     /*searchBox.addTextChangedListener(new TextWatcher() {
         @Override
         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

         }

         @Override
         public void onTextChanged(CharSequence s, int start, int before, int count) {
           //adapter.getFilter().filter(s);
             searchItem(s.toString());
         }

         @Override
         public void afterTextChanged(Editable s) {

         }
     });*/
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == DiagnosisDetails.RESULT_OK) {
            ClientActivity clientActivity = (ClientActivity) getActivity();
            if (clientActivity == null) return;
            clientActivity.setCurrentTreatment((Diagnosis) data.getSerializableExtra(KEY));
        }
    }


    public void setDiagnosisList(List<Diagnosis> diagnosisList) {
        this.diagnosisList = diagnosisList;
    }

    public void searchItem(String textToSearch) {
        for (Diagnosis item : diagnosisList) {
            if (item.getName().equals(textToSearch)) {
                diagnosisList.remove(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
