package sampleapp.wolken.com.sampleappopen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProtectedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protected);
    }
}
