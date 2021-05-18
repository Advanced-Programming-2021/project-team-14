package Controller;

import Controller.enums.CommandTags;
import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import model.Database;
import model.card.Card;
import org.json.JSONObject;

public class ImportExportController {
    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.IMPORT.getLabel()))
            Response.addMessage(importCard(request.getString("cardName")));

        if (commandTag.equals(CommandTags.EXPORT.getLabel()))
            Response.addMessage(exportCard(request.getString("cardName")));

    }

    private static String importCard(String cardName) {
        if (!Card.doesCardExist(cardName)) {
            return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
        }

        Card card = Card.getCardByName(cardName);
        DatabaseResponses responses = Database.importCard(card);

        if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
            return String.format(Responses.IMPORT.getLabel(), cardName);
        } else {
            return DatabaseResponses.SORRY.getLabel();
        }

    }

    private static String exportCard(String cardName) {
        DatabaseResponses responses = Database.exportCard(cardName);

        switch (responses) {
            case NOT_EXIST_ERROR:
                return Responses.CARD_NOT_EXIST.getLabel();
            case BAD_FORMAT_ERROR:
                return DatabaseResponses.BAD_FORMAT_RESPONSE.getLabel();
            case SUCCESSFUL:
                return String.format(Responses.EXPORT.getLabel(), cardName);
        }
        return String.format(Responses.EXPORT.getLabel(), cardName);
    }
}
