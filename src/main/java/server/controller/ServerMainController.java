package server.controller;

import model.User;
import org.json.JSONObject;
import server.Server;
import server.ServerResponse;
import view.enums.Menus;

public class ServerMainController {
    public void processCommand(JSONObject request, ServerResponse response) {

        String view = request.getString("view");
        String token = null;
        User user = null;

        if (request.has("token")) {
            token = request.getString("token");
            user = Server.getUser(token);
        }

        if (view.equals(Menus.REGISTER_MENU.getLabel())) {
            ServerRegistrationController.processCommand(request, response);
        } else if (view.equals(Menus.MAIN_MENU.getLabel())) {
            Server.logout(token);
        } else if (view.equals(Menus.CHAT.getLabel())) {
            ServerChatController.processCommand(request, response, user);
        } else if (view.equals(Menus.SCOREBOARD_MENU.getLabel())) {
            ServerScoreBoardController.processCommand(request, response);
        } else if (view.equals(Menus.SHOP_MENU.getLabel())) {
            ServerShopController.processCommand(request, response);
        } else if (view.equals(Menus.PROFILE_MENU.getLabel())) {
            ServerProfileController.processCommand(request, response, user);
        } else if (view.equals(Menus.IMPORT_EXPORT_MENU.getLabel())) {
            ServerImportExportController.processCommand(request, response, user);
        } else if (view.equals(Menus.AUCTION.getLabel())) {
            ServerAuctionController.processCommand(request, response, user);
        }
    }
}
