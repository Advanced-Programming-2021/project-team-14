package Controller;

import org.json.JSONObject;

public class MainController {

    public static String processCommand(String command) {

        JSONObject request = new JSONObject(command);

//        if (request.get("view").toString().equals(Menus.REGISTER_MENU.getLabel())){
        // send request to login controller
//        }

        return "from server : " + request.toString();

    }
}
