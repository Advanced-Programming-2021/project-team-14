package Controller;

import org.json.JSONObject;
import view.Logger;
import view.enums.Menus;

public class MainController {

    public static String processCommand(String command) {

        JSONObject request = new JSONObject(command);

        String view = request.getString("view");
        if (view.equals(Menus.REGISTER_MENU.getLabel())) {
            RegistrationController.processCommand(request);
        } else if (view.equals(Menus.SCOREBOARD_MENU.getLabel())) {
            ScoreboardController.processCommand(request);
        } else if (view.equals(Menus.PROFILE_MENU.getLabel())) {
            ProfileController.processCommand(request);
        } else if (view.equals(Menus.DECK_MENU.getLabel())) {
            DeckController.processCommand(request);
        }

        Logger.log("server", Response.getResponse().toString());
        return Response.getResponse().toString();

    }
}
