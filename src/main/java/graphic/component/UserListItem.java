package graphic.component;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.SimpleUser;

import java.util.Locale;

public class UserListItem extends AnchorPane implements ComponentLoader {

    @FXML
    Text username;
    @FXML
    Text shortUsername;
    @FXML
    Circle circle, profileCircle;

    public UserListItem(SimpleUser user) {
        load("userListItem");
        System.out.println(user.getUsername());
        username.setText(user.getUsername());
        shortUsername.setText(String.valueOf(user.getUsername().charAt(0)).toUpperCase(Locale.ROOT));
    }

    public void update(boolean isOnline) {
        circle.setVisible(isOnline);
    }
}
