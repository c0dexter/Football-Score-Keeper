package com.example.android.footballscorekeeper;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements StopWatchInterface {

    private int scoreTeamA = 0;
    private int amountOfYellowCardsTeamA = 0;
    private int amountOfRedCardsTeamA = 0;
    private int scoreTeamB = 0;
    private int amountOfYellowCardsTeamB = 0;
    private int amountOfRedCardsTeamB = 0;
    private String stopwatchCurrentTime = "00:00:00";

    //Team A
    private TextView teamAScoreTextView;
    private TextView teamAYellowCardTextView;
    private TextView teamARedCardTextView;
    //Team B
    private TextView teamBScoreTextView;
    private TextView teamBYellowCardTextView;
    private TextView teamBRedCardTextView;
    //Stopwatch
    private TextView stopwatchTextView;
    private boolean isRunning = false;
    private TimeCalculator stopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // *** RECOVERING THE INSTANCE STATE
        if (savedInstanceState != null) {
            // Team A
            scoreTeamA = savedInstanceState.getInt("TEAM_A_SCORE");
            amountOfYellowCardsTeamA = savedInstanceState.getInt("TEAM_A_YELLOW_CARD");
            amountOfRedCardsTeamA = savedInstanceState.getInt("TEAM_A_RED_CARD");
            // Team B
            scoreTeamB = savedInstanceState.getInt("TEAM_B_SCORE");
            amountOfYellowCardsTeamB = savedInstanceState.getInt("TEAM_B_YELLOW_CARD");
            amountOfRedCardsTeamB = savedInstanceState.getInt("TEAM_B_RED_CARD");
            // Stopwatch
            stopwatchCurrentTime = savedInstanceState.getString("STOPWATCH_TIMER");
        }

        setContentView(R.layout.activity_main);

        // *** DECLARATION TEXTVIEWS SO WE CAN MANIPULATE THEM LATER
        // Team A
        teamAScoreTextView = (TextView) findViewById(R.id.team_a_score);
        teamAYellowCardTextView = (TextView) findViewById(R.id.team_a_yellow_card);
        teamARedCardTextView = (TextView) findViewById(R.id.team_a_red_card);
        // Team B
        teamBScoreTextView = (TextView) findViewById(R.id.team_b_score);
        teamBYellowCardTextView = (TextView) findViewById(R.id.team_b_yellow_card);
        teamBRedCardTextView = (TextView) findViewById(R.id.team_b_red_card);
        //Stopwatch
        stopwatchTextView = (TextView) findViewById(R.id.stopwatch);

        // *** Creating a new custom Typeface for Counter digits
        Typeface CounterCustomFont = Typeface.createFromAsset(getAssets(), "fonts/KARNIVOD.ttf");
        teamAScoreTextView.setTypeface(CounterCustomFont);
        teamAYellowCardTextView.setTypeface(CounterCustomFont);
        teamARedCardTextView.setTypeface(CounterCustomFont);
        teamBScoreTextView.setTypeface(CounterCustomFont);
        teamBYellowCardTextView.setTypeface(CounterCustomFont);
        teamBRedCardTextView.setTypeface(CounterCustomFont);
        stopwatchTextView.setTypeface(CounterCustomFont);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Team A
        outState.putInt("TEAM_A_SCORE", scoreTeamA);
        outState.putInt("TEAM_A_YELLOW_CARD", amountOfYellowCardsTeamA);
        outState.putInt("TEAM_A_RED_CARD", amountOfRedCardsTeamA);

        // Team B
        outState.putInt("TEAM_B_SCORE", scoreTeamB);
        outState.putInt("TEAM_B_YELLOW_CARD", amountOfYellowCardsTeamB);
        outState.putInt("TEAM_B_RED_CARD", amountOfRedCardsTeamB);

        // Stopwatch
        outState.putString("STOPWATCH_TIMER", stopwatchCurrentTime);

        if (stopWatch != null) {
            outState.putBoolean("STOPWATCH_STARTED", stopWatch.IsStarted());
            outState.putLong("STOPWATCH_START_TIME", stopWatch.GetStartTime());
            stopWatch.Stop();
        } else {
            outState.putBoolean("STOPWATCH_STARTED", false);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Team A
        teamAScoreTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_SCORE")));
        teamAYellowCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_YELLOW_CARD")));
        teamARedCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_A_RED_CARD")));
        // Team B
        teamBScoreTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_B_SCORE")));
        teamBYellowCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_B_YELLOW_CARD")));
        teamBRedCardTextView.setText(String.valueOf(savedInstanceState.getInt("TEAM_B_RED_CARD")));
        // Stopwatch
        stopwatchTextView.setText(String.valueOf(savedInstanceState.getString("STOPWATCH_TIMER")));
        if (savedInstanceState.getBoolean("STOPWATCH_STARTED")) {
            isRunning = true;
            stopWatch = new TimeCalculator(this, savedInstanceState.getLong("STOPWATCH_START_TIME"));
            stopWatch.execute();
        }

    }

    // *** LOGIC TO BUTTONS AND VIEWS
    //TEAM A

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
     * Increase points by 1 for Team A
     */
    public void addOnePointsForTeamA(View v) {
        scoreTeamA = scoreTeamA + 1;
        displayForTeamA(scoreTeamA);
    }

    /**
     * @param amountYellowCard Displays the given amount of yellow cards for Team A.
     */
    public void displayYellowCardsForTeamA(int amountYellowCard) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_yellow_card);
        scoreView.setText(String.valueOf(amountYellowCard));
    }

    /**
     * Increase Team A's amount of yellow cards by 1
     */
    public void addOneYellowCardForTeamA(View v) {
        amountOfYellowCardsTeamA = amountOfYellowCardsTeamA + 1;
        displayYellowCardsForTeamA(amountOfYellowCardsTeamA);
    }

    /**
     * @param amountOfRedCards Displays the given amount of red cards for Team A.
     */
    public void displayRedCardsForTeamA(int amountOfRedCards) {
        TextView scoreView = (TextView) findViewById(R.id.team_a_red_card);
        scoreView.setText(String.valueOf(amountOfRedCards));
    }

    /**
     * Increase Team A's amount of red cards by 1
     */
    public void addOneRedCardForTeamA(View v) {
        amountOfRedCardsTeamA = amountOfRedCardsTeamA + 1;
        displayRedCardsForTeamA(amountOfRedCardsTeamA);
    }

    //TEAM B

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
     * Increase points by 1 for Team B
     */
    public void addOnePointsForTeamB(View v) {
        scoreTeamB = scoreTeamB + 1;
        displayForTeamB(scoreTeamB);
    }

    /**
     * @param amountYellowCard Displays the given amount of yellow cards for Team B.
     */
    public void displayYellowCardsForTeamB(int amountYellowCard) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_yellow_card);
        scoreView.setText(String.valueOf(amountYellowCard));
    }

    /**
     * Increase Team B's amount of yellow cards by 1
     */
    public void addOneYellowCardForTeamB(View v) {
        amountOfYellowCardsTeamB = amountOfYellowCardsTeamB + 1;
        displayYellowCardsForTeamB(amountOfYellowCardsTeamB);
    }

    /**
     * @param amountOfRedCards Displays the given amount of red cards for Team B.
     */
    public void displayRedCardsForTeamB(int amountOfRedCards) {
        TextView scoreView = (TextView) findViewById(R.id.team_b_red_card);
        scoreView.setText(String.valueOf(amountOfRedCards));
    }

    /**
     * Increase Team B's amount of red cards by 1
     */
    public void addOneRedCardForTeamB(View v) {
        amountOfRedCardsTeamB = amountOfRedCardsTeamB + 1;
        displayRedCardsForTeamB(amountOfRedCardsTeamB);
    }

    /**
     * Reset the counters for the both teams back to 0
     */
    public void resetScoreCounters(View v) {
        if (stopWatch != null) stopWatch.Stop();

        //Set all values to 0 by default
        scoreTeamA = 0;
        scoreTeamB = 0;
        amountOfYellowCardsTeamA = 0;
        amountOfRedCardsTeamA = 0;
        amountOfYellowCardsTeamB = 0;
        amountOfRedCardsTeamB = 0;
        stopwatchCurrentTime = "00:00:00";

        //Display default values
        displayForTeamA(scoreTeamA);
        displayForTeamB(scoreTeamB);
        displayYellowCardsForTeamA(amountOfYellowCardsTeamA);
        displayRedCardsForTeamA(amountOfRedCardsTeamA);
        displayYellowCardsForTeamB(amountOfYellowCardsTeamB);
        displayRedCardsForTeamB(amountOfRedCardsTeamB);
        displayStopwatchTime(stopwatchCurrentTime);

    }

    /**
     * Displays stopwatch.
     *
     * @param stopwatchCurrentTime
     */
    public void displayStopwatchTime(String stopwatchCurrentTime) {
        TextView stopwatchTextView = (TextView) findViewById((R.id.stopwatch));
        stopwatchTextView.setText(stopwatchCurrentTime);
    }

    /**
     * Update stopwatch
     */
    public void updateStopwatch(View v) {
        startStopwatch();
    }

    public void startStopwatch() {
        if (!isRunning) {
            isRunning = true;
            stopWatch = new TimeCalculator(this);
            stopWatch.execute();
        }
    }

    public void reportFinish() {
        isRunning = false;
    }

    public class ScoreCounterTextView extends android.support.v7.widget.AppCompatTextView {

        public ScoreCounterTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/KARNIVOD.ttf"));
        }
    }

}
