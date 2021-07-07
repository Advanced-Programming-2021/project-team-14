package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Database;
import model.User;
import model.game.Player;


public class DuelistInfo extends AnchorPane implements ComponentLoader {

    @FXML
    private Text username, nickname;
    @FXML
    private ImageView profilePhoto;

    public DuelistInfo(User user, boolean isOpponent) {
        load("DuelistInfo");
        username.setText(user.getUsername());
        nickname.setText(user.getNickname());
        this.setLayoutX(900);
        this.setLayoutY(700);
        if (isOpponent) {
            this.setLayoutY(10);
        }
        if (user.hasProfilePhoto()) {
            Image image = Database.getProfilePhoto(user.getUsername());
            profilePhoto.setImage(image);
            System.out.println("here");
        } else {
            profilePhoto.setStyle("-fx-background-color: BLUE;");
        }
    }
}
