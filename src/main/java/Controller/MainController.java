package Controller;

import org.json.JSONObject;
import view.enums.Menus;

public class MainController {

    public static String processCommand(String command) {

        JSONObject request = new JSONObject(command);

        if (request.getString("view").equals(Menus.REGISTER_MENU.getLabel())) {

            command = RegistrationController.processCommand(request);
        }

        request = new JSONObject(command);

        return "from server : " + request.toString();

    }
}
