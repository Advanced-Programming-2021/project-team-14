package graphic.component;

import com.jfoenix.controls.JFXProgressBar;
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
//    @FXML
//    private JFXProgressBar lifePoint;

    public DuelistInfo(User user, boolean isOpponent) {
        load("DuelistInfo");
//        lifePoint.setProgress(0.5);
        username.setText(user.getUsername());
        nickname.setText(user.getNickname());
        if (isOpponent) {
            this.setLayoutY(5);
        }
        if (user.hasProfilePhoto()) {
            Image image = Database.getProfilePhoto(user.getUsername());
            profilePhoto.setImage(image);
        } else {
            Image image = Database.getProfilePhoto("default");
            profilePhoto.setImage(image);
        }
    }
}
