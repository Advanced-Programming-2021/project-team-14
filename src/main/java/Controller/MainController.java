package Controller;

import org.json.JSONObject;
import view.enums.Menus;

public class MainController {

    public static String processCommand(String command) {

        JSONObject request = new JSONObject(command);

        if (request.getString("view").equals(Menus.REGISTER_MENU.getLabel()))
            RegistrationController.processCommand(request);

        return Response.getResponse().toString();

    }
}
