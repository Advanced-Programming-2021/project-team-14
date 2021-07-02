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

    private List<User> users = ScoreBoard.getInstance().getSortedUsers();


    @FXML
    public void initialize() {
        Request.setCommandTag(CommandTags.SHOW_SCOREBOARD);
        setView(Menus.SCOREBOARD_MENU);
        Request.send();
        listView.setBackground(Background.EMPTY);
        for (int i = 0; i < users.size(); i++) {
            listView.getItems().add(addItem(users.get(i)));
        }
        listView.setVerticalGap(10.0);
    }


    private ListItem addItem(User user) {
        ListItem deckListItem = new ListItem(user);
        deckListItem.getDelete().setOnMouseClicked(e -> {
            listView.getItems().remove(deckListItem);
        });
        deckListItem.getEdit().setOnMouseClicked(e -> {
            deckListItem.getCardsNum().setText("21");
        });

        return deckListItem;
    }
}
