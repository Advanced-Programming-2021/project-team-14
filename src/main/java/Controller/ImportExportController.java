package Controller;

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
import view.Logger;

public class ImportExportController {
    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.IMPORT.getLabel()))
            Response.addMessage(importCard(request.getString("token"), request.getString("cardName")));

        else if (commandTag.equals(CommandTags.EXPORT.getLabel()))
            Response.addMessage(exportCard(request.getString("token"), request.getString("cardName")));

        else if (commandTag.equals(CommandTags.SAVE_CARD.getLabel()))
            Response.addMessage(saveCard(request.getString("token"), request.getString("card")));
    }

    private static String saveCard(String username, String cardString) {
        User user = User.getUserByUsername(username);
        Card card;

        if (cardString.contains("Monster")) {
            card = new Gson().fromJson(cardString, Monster.class);
        } else {
            card = new Gson().fromJson(cardString, SpellTrap.class);
        }

        if (!user.getWallet().isCashEnough(card.getPrice() / 10)) {
            Response.error();
            return "no enough money!";
        } else {
            DatabaseResponses responses = Database.exportCard(username, card);
            if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
                Response.success();
                user.getWallet().decreaseCash(card.getPrice() / 10);
                user.updateDatabase();
                return "card saved successfully!";
            } else {
                Response.error();
                return responses.getLabel();
            }
        }
    }

    private static String exportCard(String username, String cardName) {
        if (!Card.doesCardExist(cardName)) {
            return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
        }

        Card card = Card.getCardByName(cardName);
        DatabaseResponses responses = Database.exportCard(username, card);

        if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
            Response.success();
            return String.format(DatabaseResponses.EXPORT.getLabel(), cardName);
        } else {
            Response.error();
            return DatabaseResponses.SORRY.getLabel();
        }

    }

    private static String importCard(String username, String cardName) {
        DatabaseResponses responses = Database.importCard(username, cardName);
        switch (responses) {
            case NOT_EXIST_ERROR:
                Response.error();
                return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
            case BAD_FORMAT_ERROR:
                Response.error();
                return DatabaseResponses.BAD_FORMAT_RESPONSE.getLabel();
            case SUCCESSFUL:
                Response.success();
                return String.format(DatabaseResponses.IMPORT.getLabel(), cardName);
        }

        return null;
    }
}
