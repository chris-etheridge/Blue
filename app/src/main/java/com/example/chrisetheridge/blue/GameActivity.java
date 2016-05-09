package com.example.chrisetheridge.blue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by chrisetheridge on 5/8/16.
 *
 * Main activity of the app.
 * Controls the running of the game.
 */
public class GameActivity extends AppCompatActivity {

    // game score that is changed
    private int GAME_SCORE = 0;

    // default values for the game
    // this could be refactored into a class / config file
    private final int MINIMUM_BUTTON_SIZE = 100;
    private final int MAXIMUM_BUTTON_SIZE = 500;
    private final int POS_X_CUT_OFF = 300;
    private final int POS_Y_CUT_OFF = 500;
    // seed for randomizing a blue button
    private final int SEED_MAX = 5;
    // default blue color
    private final int BLUE_COLOR_ARGB = Color.argb(255, 81, 131, 198);
    // delay between adding and removing a button
    private final int ADD_BUTTON_DELAY = 800;
    private final int REMOVE_BUTTON_DELAY = 1200;

    // setup or views that we are going to use
    private TextView DEBUG_TXT_VIEW;
    private RelativeLayout GAME_LAYOUT;
    private TextView SCORE_TXT_VIEW;
    private Button GAME_START_BUTTON;

    // we also setup the class context so it can be passed around
    private Context CLASS_CONTEXT;

    // random class
    private Random rnd;

    // handles our timers
    // we require a handler for adding and removing buttons
    Handler addButtonHandler = new Handler();
    Handler removeButtonHandler = new Handler();

    // runnable task that generates new buttons
    Runnable addButtonRunnable = new Runnable() {
        @Override
        public void run() {
            // ensure that the context is available
            // if it is not, then the view has not been inflated
            if(CLASS_CONTEXT != null) {
                // generate a random button
                Button b = generateButton(CLASS_CONTEXT);

                // randomize the x and y values of the button
                // randomize the height and widht of the button
                float posX = rnd.nextInt(GAME_LAYOUT.getWidth() - POS_X_CUT_OFF);
                float posY = rnd.nextInt(GAME_LAYOUT.getHeight() - POS_Y_CUT_OFF);
                int w = rnd.nextInt((MAXIMUM_BUTTON_SIZE - MINIMUM_BUTTON_SIZE) + 1) + MINIMUM_BUTTON_SIZE;
                int h = rnd.nextInt((MAXIMUM_BUTTON_SIZE - MINIMUM_BUTTON_SIZE) + 1) + MINIMUM_BUTTON_SIZE;

                // create a linear layout with the width and height
                LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(w, h, 1);

                // set the position of the button
                b.setX(posX);
                b.setY(posY);

                // add our button
                GAME_LAYOUT.addView(b, lp);
            }

            // re-run this task after the ADD_BUTTON_DELAY
            addButtonHandler.postDelayed(this, ADD_BUTTON_DELAY);
        }
    };

    Runnable removeButtonRunnable = new Runnable() {
        @Override
        public void run() {
            // ensure the context is available
            // if it is not, then the view has not been inflated
            if(CLASS_CONTEXT != null) {
                // randomize an int within the range of the view child count
                int r = rnd.nextInt(GAME_LAYOUT.getChildCount());

                // remove that view
                // TODO: brittle code! we aren't actually checking if we are removing a button
                GAME_LAYOUT.removeViewAt(r);
            }

            removeButtonHandler.postDelayed(this, REMOVE_BUTTON_DELAY);
        }
    };


    // when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        // set our views and context
        CLASS_CONTEXT = this;
        GAME_LAYOUT = (RelativeLayout) findViewById(R.id.game_board);
        SCORE_TXT_VIEW = (TextView) findViewById(R.id.score_txt_view);
        GAME_START_BUTTON = (Button) findViewById(R.id.game_start_btn);

        // set the text field
        // TODO: use placeholders!
        SCORE_TXT_VIEW.setText(getString(R.string.game_score_pre) + " " + 0);
    }

    // PUBLIC METHODS

    // handle the start button tap
    public void startButtonTapped(View view) {
        clearGame(this);
        setupGame(this);
    }

    // handle the menu button tap
    public void menuButtonTapped(View view) {
        // stop and take us to the menu
        Intent intent = new Intent(this, MenuActivity.class);
        stopGame(this);

        startActivity(intent);

        this.finish();
    }

    // handle the stop button tap
    // TODO: should this be considered a game over state?
    public void stopButtonTapped(View view) {
        stopGame(this);

        // should we move in the game over state when the user 'stops'?
        gameOver(this);
    }

    // PRIVATE METHODS

    // sets up the game
    // mainly starts the runnables
    private void setupGame(Context ctx) {
        // start the runnables
        // starts the add runnable immediately and starts the
        // remove runnable after 1 second
        addButtonHandler.postDelayed(addButtonRunnable, 0);
        removeButtonHandler.postDelayed(removeButtonRunnable, 1000);

        Toast.makeText(ctx, getString(R.string.game_new_toast), Toast.LENGTH_SHORT).show();

        // swap the start button text
        GAME_START_BUTTON.setText("retry");

        // set score to zero
        GAME_SCORE = 0;
    }

    // stops the game
    // clears the runnables and cleans up
    private void stopGame(Context ctx) {
        // stop our runnables
        addButtonHandler.removeCallbacks(addButtonRunnable);
        removeButtonHandler.removeCallbacks(removeButtonRunnable);

        GAME_START_BUTTON.setText("start");
    }

    // stops the game
    // game is over -> the user has hit a button that is not BLUE
    private void gameOver(Context ctx) {
        stopGame(ctx);

        clearGame(ctx);

        GAME_START_BUTTON.setText("start");

        // show the game over activity
        Intent intent = new Intent(getBaseContext(), GameOverActivity.class);
        intent.putExtra("EXTRA_PLAYER_SCORE", GAME_SCORE);
        startActivity(intent);
    }

    // cleanup the game board
    private void clearGame(Context ctx) {
        // remove all buttons
        GAME_LAYOUT.removeAllViewsInLayout();
    }

    // generates a random button with a random color and random size
    // returns the random generated button
    // a button has a chance to get the tag "BLUE", which means that
    // it's the button that needs to be tapped
    private Button generateButton(Context ctx) {
        Button b = new Button(ctx);
        int[] colors = generateRandomColor();
        int w = rnd.nextInt((MAXIMUM_BUTTON_SIZE - MINIMUM_BUTTON_SIZE) + 1) + MINIMUM_BUTTON_SIZE;
        int h = rnd.nextInt((MAXIMUM_BUTTON_SIZE - MINIMUM_BUTTON_SIZE) + 1) + MINIMUM_BUTTON_SIZE;
        int seed = rnd.nextInt(20);

        // look & feel of button
        b.setBackgroundColor(Color.argb(colors[0], colors[1], colors[2], colors[3]));
        b.setWidth(w);
        b.setHeight(h);

        // if we are within the seed range, then we can create a 'blue' button
        // which gives score
        // TODO: the randomness here is not good enough, need another way to select a 'blue' button
        if(seed <= SEED_MAX) {
            b.setText("BLUE");
            b.setBackgroundColor(BLUE_COLOR_ARGB);
            b.setTag("blue");

            // on click listener of button
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GAME_SCORE++;
                    SCORE_TXT_VIEW.setText(getString(R.string.game_score_pre) + " " + GAME_SCORE);

                    // TODO: this needs to be deleted and not set invisible
                    v.setVisibility(View.GONE);
                }
            });
        } else {
            b.setText(generateRandomClickVariant());

            // on click listener of button
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameOver(v.getContext());

                    v.setVisibility(View.GONE);
                }
            });
        }

        return b;
    }

    // generates a random color in the form of aRGB
    // returns an array with 4 items, being the A R G B values respectively
    private int[] generateRandomColor() {
        rnd = new Random();

        return new int[] {255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)};
    }

    // generates a random click text variant
    // uses the array in our resources and randomizes a position for the index
    // this is nice since we just need to add to the resource file to add more click variants,
    // don't need to modify this method anymore
    private String generateRandomClickVariant() {
        String[] vs = getResources().getStringArray(R.array.game_click_variants);

        // ensure that our index is not -1
        return vs[rnd.nextInt(vs.length) == 0 ? 0 : rnd.nextInt(vs.length)];
    }

}
