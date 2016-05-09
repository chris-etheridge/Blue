package com.example.chrisetheridge.blue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by chrisetheridge on 5/8/16.
 *
 * Activity that controls the splash for the app.
 * Shamelessly taken from: https://www.bignerdranch.com/blog/splash-screens-the-right-way/
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // our splash screen essentially just passes us onto the Menu Activity
        // in the future we would do setting up here, like loading files or connecting to
        // a web service
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
        finish();
    }
}
