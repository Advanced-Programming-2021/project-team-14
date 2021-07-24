package model;

import java.util.Timer;
import java.util.TimerTask;


public class Stopwatch {
    private int interval;
    private Timer timer;
    private int delay;
    private int period;
    private Auction auction;
    private Timer myTimer = new Timer();
    private boolean exit;

    public Stopwatch(Auction auction, int interval, int delay, int period) {
        this.exit = false;
        this.delay = delay;
        this.period = period;
        timer = new Timer();
        this.interval = interval;
        this.auction = auction;
    }

    public void startTimer() {

        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                System.out.println(interval--);
                if (interval == 0) {
                    timer.cancel();

                }
            }
        }, delay, period);
    }

    public int getInterval() {
        return this.interval;
    }

}