package com.example.bett.wordscramble;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.samaay.homework2.R;

public class HowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_how);
    }

    public void onStart (View view) {
        Intent intent = new Intent(getApplicationContext(),PlayActivity.class);
        startActivity(intent);
        /*Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        startActivity(sendIntent); */
    }
}
