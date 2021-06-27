package Controller;

import Controller.enums.CommandTags;
import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import model.Database;
import model.card.Card;
import org.json.JSONObject;
import view.Logger;

public class ImportExportController {
    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.IMPORT.getLabel()))
            Response.addMessage(importCard(request.getString("token"), request.getString("cardName")));

        if (commandTag.equals(CommandTags.EXPORT.getLabel()))
            Response.addMessage(exportCard(request.getString("token"), request.getString("cardName")));

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
