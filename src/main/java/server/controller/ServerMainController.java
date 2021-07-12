package server.controller;

import model.User;
import org.json.JSONObject;
import server.Server;
import server.ServerResponse;
import view.enums.Menus;

public class ServerMainController {
    public void processCommand(JSONObject request, ServerResponse response) {

        String token = null;
        User user = null;

        String view = request.getString("view");
        if (view.equals(Menus.REGISTER_MENU.getLabel())) {
            ServerRegistrationController.processCommand(request, response);
        }
        if (request.has("token")) {
            token = request.getString("token");
            user = Server.getUser(token);
        }
        if (view.equals(Menus.MAIN_MENU.getLabel())) {
            Server.logout(token);
        }
    }
}
