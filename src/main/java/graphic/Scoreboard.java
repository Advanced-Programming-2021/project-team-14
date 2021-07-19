package graphic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXListView;
import graphic.component.Colors;
import graphic.component.ListItem;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import model.User;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;
import java.util.List;

public class
Scoreboard extends Menu {

    @FXML
    public ImageView image;
    @FXML
    private JFXListView<ListItem> listView;

    private List<User> users;

    private ArrayList<String> onlineUsers;


    @FXML
    public void initialize() {
        prepare();
        new Thread(() -> {
            System.out.println(Menu.getData());
            while (true) {
                update();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void prepare() {
        Request.setCommandTag(CommandTags.SHOW_SCOREBOARD);
        setView(Menus.SCOREBOARD_MENU);
        Request.send();
        users = new Gson().fromJson(Request.getMessage(), new TypeToken<ArrayList<User>>() {
        }.getType());
        listView.setBackground(Background.EMPTY);
        getOnlineUsers();
        for (User user : users) {
            listView.getItems().add(addItem(user));
        }
        listView.setVerticalGap(5.0);
//        image.setImage();
    }

    private void update() {

        getOnlineUsers();
        Platform.runLater(() -> {
            listView.getItems().clear();
            prepare();
        });
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
        if (onlineUsers.contains(user.getUsername()))
            listItem.setOnlineCircle(true);

        listItem.getContainer().setStyle("-fx-border-color: " + color);

        if (currentUser.equals(user)) {
            listItem.setEffect(new DropShadow());
            listItem.getContainer().setStyle("-fx-background-color: " + Colors.SUCCESS.getHexCode() + "; -fx-background-radius: 10;");
        }
        return listItem;
    }

    private void getOnlineUsers() {
        setView(Menus.SCOREBOARD_MENU);
        Request.setCommandTag(CommandTags.GET_ONLINE_USERS);
        Request.send();
        onlineUsers = new Gson().fromJson(Request.getMessage(), new TypeToken<ArrayList<String>>() {
        }.getType());
    }
}