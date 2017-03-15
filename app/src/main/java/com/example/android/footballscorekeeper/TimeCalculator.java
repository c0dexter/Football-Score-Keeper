package com.example.android.footballscorekeeper;

import android.os.AsyncTask;

/**
 * Created by dobry on 10.03.17.
 */

public class TimeCalculator extends AsyncTask<Void, String, Void> {
    long start;
    private StopWatchInterface stopWatchInterface;
    private boolean turnedOn;

    public TimeCalculator(StopWatchInterface swInterface) {
        stopWatchInterface = swInterface;
        start = System.currentTimeMillis();
        turnedOn = true;
    }

    public TimeCalculator(StopWatchInterface swInterface, long start) {
        stopWatchInterface = swInterface;
        this.start = start;
        turnedOn = true;
    }

    public long GetStartTime() {
        return start;
    }

    public boolean IsStarted() {
        return turnedOn;
    }

    public void Stop() {
        turnedOn = false;
    }

    @Override
    protected Void doInBackground(Void... params) {

        while (turnedOn == true) {

            long elapsedTime = System.currentTimeMillis() - start;
            elapsedTime = elapsedTime / 1000;

            String seconds = Integer.toString((int) (elapsedTime % 60));
            String minutes = Integer.toString((int) ((elapsedTime % 3600) / 60));
            int destinationTime = Integer.parseInt(minutes);

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }

            String writeThis = minutes + ":" + seconds;

            publishProgress(writeThis);

            if (destinationTime >= 90) {
                turnedOn = false;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (stopWatchInterface != null) {
            if (values != null && values.length > 0) {
                if (turnedOn) stopWatchInterface.displayStopwatchTime(values[0]);
            }
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (stopWatchInterface != null) {
            stopWatchInterface.reportFinish();
        }
        super.onPostExecute(aVoid);
    }
}
