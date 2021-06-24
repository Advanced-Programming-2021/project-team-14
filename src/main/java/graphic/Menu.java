package graphic;

import model.User;
import view.Request;
import view.enums.Menus;

public class Menu {
    protected static User currentUser;
    protected static void setView(Menus menus){
        Request.addData("view", menus.getLabel());
    }
    public static void setCurrentUser(String username) {
        currentUser = User.getUserByUsername(username);
    }
}
