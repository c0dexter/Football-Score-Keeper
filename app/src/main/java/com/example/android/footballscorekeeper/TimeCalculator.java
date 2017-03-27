package com.example.android.footballscorekeeper;

import android.os.AsyncTask;

/**
 * Created by dobry on 10.03.17.
 */

class TimeCalculator extends AsyncTask<Void, String, Void> {
    private long start;
    private StopWatchInterface stopWatchInterface;
    private boolean turnedOn;
    private int currentTime = 0;
    private int defaultTime = 60000; // unit: [ms]
    private int destinationTime = 0;

    TimeCalculator(StopWatchInterface swInterface) {
        stopWatchInterface = swInterface;
        start = System.currentTimeMillis();
        turnedOn = true;
    }

    TimeCalculator(StopWatchInterface swInterface, long start) {
        stopWatchInterface = swInterface;
        this.start = start;
        turnedOn = true;
    }

    long GetStartTime() {
        return start;
    }

    boolean IsStarted() {
        return turnedOn;
    }

    void Stop() {
        turnedOn = false;
    }


    @Override
    protected Void doInBackground(Void... params) {

        while (turnedOn) { //turnedOn = true

            long elapsedTime = System.currentTimeMillis() - start;
            currentTime = (int) elapsedTime;
            elapsedTime = elapsedTime / 1000;
            String seconds = Integer.toString((int) (elapsedTime % 60));
            String minutes = Integer.toString((int) ((elapsedTime % 3600) / 60));

            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }

            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }

            String writeThis = minutes + ":" + seconds;
            publishProgress(writeThis);
            checkTimeRule();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    void setAdditionalTime(int additionalSeconds) {
        int stopwatchAdditionalTime = additionalSeconds * 1000;
        destinationTime = defaultTime + stopwatchAdditionalTime;
    }

    private void checkTimeRule() {
        if (currentTime >= defaultTime) {
            if (currentTime > destinationTime) {
                turnedOn = false;
            }
        }
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
