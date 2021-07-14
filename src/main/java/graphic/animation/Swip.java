package graphic.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Swip {
    private TranslateTransition transition;

    public Swip(Node node) {
        transition = new TranslateTransition(Duration.millis(100), node);
        transition.setAutoReverse(true);
        transition.setByX(-7);
    }

    public void play(){
        transition.play();
    }
    public void stop(){
        transition.stop();
    }

}
