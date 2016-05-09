package com.example.chrisetheridge.blue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chrisetheridge on 5/8/16.
 *
 * Activity that shows information about me (Chris Etheridge)
 * Nothing special here.
 */
public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
    }

    // handle the back button tap
    public void backButtonTapped(View view) {
        // go back to the menu
        Intent intent = new Intent(this, MenuActivity.class);

        startActivity(intent);

        finish();
    }

}
