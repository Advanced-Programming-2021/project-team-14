package Controller;

import org.json.JSONObject;
import view.enums.Menus;

public class MainController {

    public static String processCommand(String command) {

        JSONObject request = new JSONObject(command);

        String view = request.getString("view");
        if (view.equals(Menus.REGISTER_MENU.getLabel()))
            RegistrationController.processCommand(request);
        else if (view.equals(Menus.SCOREBOARD_MENU.getLabel()))
            ScoreboardController.processCommand(request);

        return Response.getResponse().toString();

    }
}
