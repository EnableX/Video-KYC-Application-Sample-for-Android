package com.enablex.remitonline.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.enablex.remitonline.R;

public class EndKYCActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_kyc);
        getSupportActionBar().setTitle("End KYC");
    }
}
