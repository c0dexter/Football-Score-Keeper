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
    private int amountOfYellowCardsTeamA = 0;
    private int amountOfRedCardsTeamA = 0;
    private int scoreTeamB = 0;
    private int amountOfYellowCardsTeamB = 0;
    private int amountOfRedCardsTeamB = 0;

    //Team A
    private TextView teamAScoreTextView;
    private TextView teamAYellowCardTextView;
    private TextView teamARedCardTextView;
    //Team B
    private TextView teamBScoreTextView;
    private TextView teamBYellowCardTextView;
    private TextView teamBRedCardTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // recovering the instance state
        if (savedInstanceState != null) {
            scoreTeamA = savedInstanceState.getInt("TEAM_A_SCORE");
            amountOfYellowCardsTeamA = savedInstanceState.getInt("TEAM_A_YELLOW_CARD");
            amountOfRedCardsTeamA = savedInstanceState.getInt("TEAM_A_RED_CARD");
            // Team B
            scoreTeamB = savedInstanceState.getInt("TEAM_B_SCORE");
            amountOfYellowCardsTeamB = savedInstanceState.getInt("TEAM_B_YELLOW_CARD");
            amountOfRedCardsTeamB = savedInstanceState.getInt("TEAM_B_RED_CARD");
        }

        setContentView(R.layout.activity_main);

        //Declaration TextViews so we can manipulate them later
        teamAScoreTextView = (TextView) findViewById(R.id.team_a_score);
        teamAYellowCardTextView = (TextView) findViewById(R.id.team_a_yellow_card);
        teamARedCardTextView = (TextView) findViewById(R.id.team_a_red_card);
        // Team B
        teamBScoreTextView = (TextView) findViewById(R.id.team_b_score);
        //TODO: Dodać pozostałe VIEWs do widoku
        //Creating a new custom Typeface for Counter digits
        Typeface CounterCustomFont = Typeface.createFromAsset(getAssets(), "fonts/KARNIVOD.ttf");
        teamAScoreTextView.setTypeface(CounterCustomFont);
        teamAYellowCardTextView.setTypeface(CounterCustomFont);
        teamARedCardTextView.setTypeface(CounterCustomFont);
        teamBScoreTextView.setTypeface(CounterCustomFont);
        //teamBYellowCardTextView.setTypeface(CounterCustomFont);
        //teamBRedCardTextView.setTypeface(CounterCustomFont);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("TEAM_A_SCORE", scoreTeamA);
        outState.putInt("TEAM_A_YELLOW_CARD", amountOfYellowCardsTeamA);
        outState.putInt("TEAM_A_RED_CARD", amountOfRedCardsTeamA);

        outState.putInt("TEAM_B_SCORE", scoreTeamB);
        outState.putInt("TEAM_B_YELLOW_CARD", amountOfYellowCardsTeamB);
        outState.putInt("TEAM_B_RED_CARD", amountOfRedCardsTeamB);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //Team A
        teamAScoreTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_SCORE")));
        teamAYellowCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_YELLOW_CARD")));
        teamARedCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_RED_CARD")));
        //Team B
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
        amountOfYellowCardsTeamA = 0;
        amountOfRedCardsTeamA = 0;
        amountOfYellowCardsTeamB = 0;
        amountOfRedCardsTeamB = 0;

        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
    }


    public class ScoreCounterTextView extends android.support.v7.widget.AppCompatTextView {

        public ScoreCounterTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/KARNIVOD.ttf"));
        }
    }

}

