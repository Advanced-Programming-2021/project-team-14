package graphic;

import com.jfoenix.controls.JFXListView;
import graphic.component.ListItem;
import javafx.fxml.FXML;
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
    private AnchorPane root;

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
        listView.setVerticalGap(10.0);
    }


    private ListItem addItem(User user) {

        return new ListItem(user);
    }
}