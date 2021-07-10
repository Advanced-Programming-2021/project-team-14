package graphic.component;

import com.jfoenix.controls.JFXSnackbar;
import graphic.Medias;
import graphic.Menu;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SnackBarComponent {
    public SnackBarComponent(String content, ResultState state){
        Medias.SNACKBAR_ERROR.play(1);
        Label response = new Label();
        response.setText(content);
        response.getStyleClass().add(state.getStyleClass());
        new JFXSnackbar(Menu.getRoot()).enqueue(new JFXSnackbar.SnackbarEvent(response, Duration.seconds(2), null));
    }


    public SnackBarComponent(String content, ResultState state, Pane root){
        Medias.SNACKBAR_ERROR.play(1);
        Label response = new Label();
        response.setText(content);
        response.getStyleClass().add(state.getStyleClass());
        new JFXSnackbar(root).enqueue(new JFXSnackbar.SnackbarEvent(response, Duration.seconds(2), null));
    }
}
