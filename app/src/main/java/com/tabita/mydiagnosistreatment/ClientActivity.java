package com.tabita.mydiagnosistreatment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

public class ClientActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private FrameLayout frame;
    private BottomNavigationView navigation;
    private DashboardFragment dashboardFragment;
    private DiagnosisFragment diagnosisFragment;
    private PastTreatmentsFragment pastTreatmentsFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    setFragment(dashboardFragment);
                    return true;
                case R.id.navigation_diagnosis:
                    mTextMessage.setText(R.string.title_diagnosis);
                    setFragment(diagnosisFragment);
                    return true;
                case R.id.navigation_past_treatment:
                    mTextMessage.setText(R.string.title_past_treatments);
                    setFragment(pastTreatmentsFragment);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        // Gui
        mTextMessage = findViewById(R.id.message);
        frame = findViewById(R.id.main_frame);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        dashboardFragment = new DashboardFragment();
        diagnosisFragment = new DiagnosisFragment();
        pastTreatmentsFragment = new PastTreatmentsFragment();

        setFragment(dashboardFragment);
    }

    private void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        finish();
    }
}
