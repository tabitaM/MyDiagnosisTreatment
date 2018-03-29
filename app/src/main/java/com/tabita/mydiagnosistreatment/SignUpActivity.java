package com.tabita.mydiagnosistreatment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent=getIntent();
        String ids = intent.getStringExtra(LoginActivity.EXTRA_MESSAGE);

        TextView textView  = findViewById(R.id.textView);
        textView.setText(ids);
    }
}
