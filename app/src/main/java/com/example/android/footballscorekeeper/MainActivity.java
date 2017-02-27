package com.example.android.footballscorekeeper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int scoreTeamA = 0;
    private int scoreTeamB = 0;

    private TextView teamAScoreTextView;
    private TextView teamBScoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // recovering the instance state
        if (savedInstanceState != null) {
            scoreTeamA = savedInstanceState.getInt("TEAM_A_SCORE");
            scoreTeamB = savedInstanceState.getInt("TEAM_B_SCORE");
        }

        setContentView(R.layout.activity_main);

        //Declaration TextViews so we can manipulate them later
        teamAScoreTextView = (TextView) findViewById(R.id.team_a_score);
        teamBScoreTextView = (TextView) findViewById(R.id.team_b_score);
        //Creating a new custom Typeface for Counter digits
        Typeface CounterCustomFont = Typeface.createFromAsset(getAssets(), "fonts/KARNIVOD.ttf");
        teamAScoreTextView.setTypeface(CounterCustomFont);
        teamBScoreTextView.setTypeface(CounterCustomFont);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("TEAM_A_SCORE", scoreTeamA);
        outState.putInt("TEAM_B_SCORE", scoreTeamB);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        teamAScoreTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_SCORE")));
        teamBScoreTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_B_SCORE")));
    }

    /**
     * Displays the given score for Team A.
     *
     * @param score
     */
    public void displayForTeamA(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Increase points by 3
     */
    public void addThreePointsForTeamA(View v) {
        scoreTeamA = scoreTeamA + 3;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Increase points by 2
     */
    public void addTwoPointsForTeamA(View v) {
        scoreTeamA = scoreTeamA + 2;
        displayForTeamA(scoreTeamA);
    }

    /**
     * Increase points by 1
     */
    public void addOnePointsForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }


    /**
     * Displays the given score for Team B.
     *
     * @param score
     */
    public void displayForTeamB(int score) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_score);
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Increase points by 3
     */
    public void addThreePointsForTeamB(View v) {
        scoreTeamB = scoreTeamB + 3;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Increase points by 2
     */
    public void addTwoPointsForTeamB(View v) {
        scoreTeamB = scoreTeamB + 2;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Increase points by 1
     */
    public void addOnePointsForTeamB(View v) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    /**
     * Reset the counters for the both teams back to 0
     */
    public void resetScoreCounters(View v) {
        scoreTeamA = 0;
        scoreTeamB = 0;
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }

    public class ScoreCounterTextView extends TextView {

        public ScoreCounterTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/KARNIVOD.ttf"));
        }
    }

}

