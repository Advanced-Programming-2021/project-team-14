package graphic;

import com.jfoenix.controls.JFXListView;
import graphic.component.ListItem;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import model.ScoreBoard;
import model.User;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.List;

public class Scoreboard extends Menu {

    @FXML
    public ImageView image;
    @FXML
    private JFXListView<ListItem> listView;

    private List<User> users;


    @FXML
    public void initialize() {
        Request.setCommandTag(CommandTags.SHOW_SCOREBOARD);
        setView(Menus.SCOREBOARD_MENU);
        Request.send();
        users = ScoreBoard.getSortedUsers();
        listView.setBackground(Background.EMPTY);
        for (User user : users) {
            listView.getItems().add(addItem(user));
        }
        listView.setVerticalGap(5.0);
//        image.setImage();
    }


    private ListItem addItem(User user) {
        ListItem listItem = new ListItem(user);
        String color = "#403c45";
        switch (user.getRank()) {
            case 1:
                color = "GOLD";
                break;
            case 2:
                color = "SILVER";
                break;
            case 3:
                color = "#cd7f32";
                break;
        }
        listItem.getContainer().setStyle("-fx-border-color: " + color);
        return listItem;
    }
}