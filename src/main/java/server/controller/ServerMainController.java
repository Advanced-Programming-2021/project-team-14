package server.controller;

import Controller.*;
import org.json.JSONObject;
import server.ServerResponse;
import view.Logger;
import view.enums.Menus;

public class ServerMainController {
    public void processCommand(JSONObject request, ServerResponse response) {

        String view = request.getString("view");
        if (view.equals(Menus.REGISTER_MENU.getLabel())) {
            ServerRegistrationController.processCommand(request, response);
        }
    }
}
