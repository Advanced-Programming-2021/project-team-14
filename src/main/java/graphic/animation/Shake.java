package graphic.animation;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node) {
        tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(0f);
        tt.setByX(10f);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
        tt.setOnFinished(e -> node.setTranslateX(0));
    }

    public void shake() {
        tt.playFromStart();
    }

    public void littleShake() {
        tt.setDuration(Duration.millis(100));
        tt.playFromStart();
    }
}
