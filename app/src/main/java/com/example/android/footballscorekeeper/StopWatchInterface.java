package com.example.android.footballscorekeeper;

/**
 * Created by dobry on 10.03.17.
 */

public interface StopWatchInterface {
    /**
     * This method displaying stopwatch time in TextView
     */
    void displayStopwatchTime(String timeToDisplay);

    /**
     * After end of time set flat isRunning to false
     */
    void reportFinish();
}
