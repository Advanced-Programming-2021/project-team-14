package graphic.component;

import com.jfoenix.controls.JFXProgressBar;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.game.Game;
import model.game.Player;

public class DuelistInfo extends HBox implements ComponentLoader {

    @FXML
    private Circle playerPhoto;
    @FXML
    private Text playerNickname;
    @FXML
    private Text playerLifePoint;
    @FXML
    private JFXProgressBar progressBar;

    private Game game;
    private Player player;

    public DuelistInfo(Game game, Player player) {
        load("DuelistInfo");
        this.player = player;
        this.game = game;
        progressBar.setStyle("-fx-accent: #62C0CF");
        progressBar.setProgress(1);

        playerNickname.setText(game.getBoard().getMainPlayer().getNickname());
        playerLifePoint.setText(String.valueOf(game.getBoard().getMainPlayer().getLifePoint()));
        playerPhoto.setStroke(Color.TRANSPARENT);
//        Image image = Database.getProfilePhoto(game.getBoard().getMainPlayer().getUsername());
//        playerPhoto.setFill(new ImagePattern(image));
    }

    public void update() {
        progressBar.setProgress(player.getLifePoint() / 8000.0);
    }
}