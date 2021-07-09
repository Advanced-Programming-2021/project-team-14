package graphic.component;

import ui.RingProgressIndicator;

public class LifePointCircle extends Thread {


    public void setter(RingProgressIndicator rpi, int progress) {

        rpi.setProgress(progress);
    }

}
