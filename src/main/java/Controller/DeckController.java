package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.Card;
import model.Deck;
import model.User;
import org.json.JSONObject;

public class DeckController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CREATE_DECK.getLabel()))
            Response.addMessage(createDeck(request.getString("deck"), request.getString("token")));

        else if (commandTag.equals(CommandTags.DELETE_DECK.getLabel()))
            Response.addMessage(removeDeck(request.getString("deck"), request.getString("token")));

        else if (commandTag.equals(CommandTags.ACTIVATE_DECK.getLabel()))
            Response.addMessage(setActiveDeck(request.getString("deck"), request.getString("token")));

        else if (commandTag.equals(CommandTags.ADD_CARD.getLabel()))
            Response.addMessage(addCardToDeck(request.getString("card"),
                    request.getString("deck"),
                    request.getString("option"),
                    request.getString("token")));

        else if (commandTag.equals(CommandTags.REMOVE_CARD.getLabel()))
            Response.addMessage(removeCardFromDeck(request.getString("deck"),
                    request.getString("card"),
                    request.getString("option"),
                    request.getString("token")));

//        else if (commandTag.equals(CommandTags.SHOW_ALL_DECKS.getLabel()))
//            Response.addMessage(showAllDecks(request.getString("deck-name")));
//
//        else if (commandTag.equals(CommandTags.SHOW_DECK.getLabel()))
//            Response.addMessage(showDeck(request.getString("deck-name")));
    }

    private static String createDeck(String deckName, String username) {

        User user = User.getUserByName(username);
        if (!user.doesDeckExist(deckName)) {

            Response.success();
            new Deck(deckName);
            return Responses.CREATE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_ALREADY_EXIST.getLabel(), deckName);
    }


    private static String removeDeck(String deckName, String username) {

        User user = User.getUserByName(username);
        if (user.doesDeckExist(deckName)) {

            Response.success();
            Deck.removeDeck(deckName);
            return Responses.DELETE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }


    private static String setActiveDeck(String deckName, String username) {

        User user = User.getUserByName(username);
        if (user.doesDeckExist(deckName)) {

            Response.success();
            User.setActiveDeck(username, deckName);
            return Responses.ACTIVATE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }


    private static String addCardToDeck(String cardName, String deckName, String option, String username) {

        User user = User.getUserByName(username);
        if (user.getWallet().doesCardExist(Card.getCardByName(cardName))) {
            Card card = Card.getCardByName(cardName);
            if (user.doesDeckExist(deckName)) {
                Deck deck = Deck.getDeckByName(deckName);
                if (!Deck.isDeckFull(deckName, option)) {
                    if (!canAddCard(deckName, cardName)) {
                        Response.success();
                        // remove card from user wallet and add that to deck
                        user.getWallet().removeCard(card);
                        deck.addCard(card, option);
                        return Responses.ADD_CARD_SUCCESSFUL.getLabel();
                    }
                    Response.error();
                    return String.format(Responses.ENOUGH_CARDS.getLabel(), cardName, deckName);
                }
                Response.error();
                if (option.equals(CommandTags.SIDE.getLabel())) {
                    return Responses.SIDE_DECK_IS_FULL.getLabel();
                }
                return Responses.MAIN_DECK_IS_FULL.getLabel();
            }
            Response.error();
            return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
        }
        Response.error();
        return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
    }


    private static String removeCardFromDeck(String deckName, String cardName, String option, String username) {

        User user = User.getUserByName(username);
        Card card = Card.getCardByName(cardName);
        if (user.doesDeckExist(deckName)) {
            if (Deck.doesCardAvailableInDeck(cardName, deckName, option)) {
                Response.success();
                user.getWallet().removeCard(card);
                User.setActiveDeck(username, deckName);
                return Responses.REMOVE_CARD_SUCCESSFUL.getLabel();

            }
            Response.error();
            return String.format(Responses.CARD_NOT_EXIST_IN_DECK.getLabel(), cardName, option);
        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }


//    private static String showAllDecks(String title) {
//
//    }
//
//
//    private static String showDeck(String title) {
//
//    }


    private static boolean canAddCard(String deckName, String cardName) {

        return Deck.getNumberOfCardInDeck(cardName, deckName) < 3;
    }
}
