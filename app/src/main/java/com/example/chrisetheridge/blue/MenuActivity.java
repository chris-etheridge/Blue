package com.example.chrisetheridge.blue;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by chrisetheridge on 5/8/16.
 *
 * Activity that controls the menu of the game. Nothing special here.
 * The activity just passes the user onto either the about screen or the game screen.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
    }

    // handle play button tap
    public void onPlayBtnTapped(View view) {
        // the play button just takes us to the game activity
        Intent intent = new Intent(this, GameActivity.class);

        startActivity(intent);
    }

    // handle about button tap
    public void onAboutBtnTapped(View view) {
        Intent intent = new Intent(this, AboutActivity.class);

        startActivity(intent);
    }
}
