package server.controller;

import Controller.enums.CommandTags;
import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import com.google.gson.Gson;
import model.Database;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import org.json.JSONObject;
import server.ServerResponse;

public class ServerImportExportController {

    private static ServerResponse response;

    public static void processCommand(JSONObject request, ServerResponse response, User user) {
        ServerImportExportController.response = response;

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.IMPORT.getLabel()))
            response.addMessage(importCard(user, request.getString("cardName")));

        if (commandTag.equals(CommandTags.EXPORT.getLabel()))
            response.addMessage(exportCard(user, request.getString("card")));

        else if (commandTag.equals(CommandTags.SAVE_CARD.getLabel()))
            response.addMessage(saveCard(user, request.getString("card")));

    }

    private static String saveCard(User user, String cardString) {
        Card card;

        if (cardString.contains("Monster")) {
            card = new Gson().fromJson(cardString, Monster.class);
        } else {
            card = new Gson().fromJson(cardString, SpellTrap.class);
        }

        if (!user.getWallet().isCashEnough(card.getPrice() / 10)) {
            response.error();
            return "no enough money!";
        } else {
            DatabaseResponses responses = Database.exportCard(user.getUsername(), card);
            if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
                response.success();
                user.getWallet().decreaseCash(card.getPrice() / 10);
                user.updateDatabase();
                return "card saved successfully!";
            } else {
                response.error();
                return responses.getLabel();
            }
        }
    }

    private static String exportCard(User user, String cardString) {
        Card card;

        if (cardString.contains("Monster")) {
            card = new Gson().fromJson(cardString, Monster.class);
        } else {
            card = new Gson().fromJson(cardString, SpellTrap.class);
        }

        DatabaseResponses responses = Database.exportCard(user.getUsername(), card);

        if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
            response.success();
            return String.format(DatabaseResponses.EXPORT.getLabel(), card.getName());
        } else {
            response.error();
            return DatabaseResponses.SORRY.getLabel();
        }

    }

    private static String importCard(User user, String cardName) {
        DatabaseResponses responses = Database.importCard(user.getUsername(), cardName);
        switch (responses) {
            case NOT_EXIST_ERROR:
                response.error();
                return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
            case BAD_FORMAT_ERROR:
                response.error();
                return DatabaseResponses.BAD_FORMAT_RESPONSE.getLabel();
            case SUCCESSFUL:
                response.success();
                return String.format(DatabaseResponses.IMPORT.getLabel(), cardName);
        }

        return null;
    }
}
