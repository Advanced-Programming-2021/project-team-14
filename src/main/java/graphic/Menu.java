package graphic;

import graphic.component.Colors;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.User;
import view.Request;
import view.enums.Menus;

public class Menu {
    private static String data;
    private static Pane root;
    protected static User currentUser;
    public static void setView(Menus menus){
        Request.addData("view", menus.getLabel());
    }
    protected static void addData(String data){
        Menu.data = data;
    }
    protected static void setRoot(Pane pane){
        root = pane;
    }

    public static Pane getRoot() {
        return root;
    }

    public static String getData() {
        return data;
    }

    public static void setCurrentUser(String username) {
        currentUser = User.getUserByUsername(username);
    }

    public void onDragExited(AnchorPane area) {
        area.setOnDragExited(e -> area.setStyle("-fx-border-color: " + Colors.DARK_GRAY.getHexCode() + ";"));
    }
}
