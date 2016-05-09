package com.example.chrisetheridge.blue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chrisetheridge on 5/9/16.
 *
 * Activity that shows the score at the end of the game.
 * Gets the score from the bundle extras and sets it to the text field.
 */
public class GameOverActivity extends Activity {

    private int GAME_SCORE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over_activity);

        // get our bundle extras
        Bundle extras = getIntent().getExtras();
        TextView tv = (TextView) findViewById(R.id.final_score_txt);

        // check the extras are available
        if(extras != null) {
            // pull out the score
            int v = extras.getInt("EXTRA_PLAYER_SCORE");

            // ensure the score is greater or equal 0
            if(v > 0) {
                GAME_SCORE = v;
            } else {
                GAME_SCORE = v;
            }

            // set the text of the score field
            tv.setText("your final score is " + GAME_SCORE);
        }
    }

    // handle the play again button tap
    public void playAgainBtnTapped(View v) {
        Intent intent = new Intent(getBaseContext(), GameActivity.class);
        startActivity(intent);

        finish();
    }

    // handle the menu button tap
    public void menuBtnTapped(View v) {
        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
        startActivity(intent);

        finish();
    }

}
