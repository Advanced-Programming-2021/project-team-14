package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.Card;
import model.Deck;
import model.User;
import org.json.JSONObject;

public class DeckController {

    public static void processCommand(JSONObject request) {

        System.out.println(request.toString());
        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.CREATE_DECK.getLabel()))
            Response.addMessage(createDeck(request.getString("deck")));

        else if (commandTag.equals(CommandTags.DELETE_DECK.getLabel()))
            Response.addMessage(removeDeck(request.getString("deck")));

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

    private static String createDeck(String deckName) {

        if (!Deck.doesDeckExist(deckName)) {

            Response.success();
            new Deck(deckName);
            return Responses.CREATE_DECK_SUCCESSFUL.getLabel();

        } else {
            Response.error();
            return "deck with name " + deckName + " already exists";
        }
    }


    private static String removeDeck(String deckName) {

        if (Deck.doesDeckExist(deckName)) {

            Response.success();
            Deck.removeDeck(deckName);
            return Responses.DELETE_DECK_SUCCESSFUL.getLabel();

        } else {
            Response.error();
            return "deck with name " + deckName + " does not exist";
        }
    }


    private static String setActiveDeck(String deckName, String username) {

        if (Deck.doesDeckExist(deckName)) {

            Response.success();
            User.setActiveDeck(username, deckName);
            return Responses.ACTIVATE_DECK_SUCCESSFUL.getLabel();

        } else {
            Response.error();
            return "deck with name " + deckName + " does not exist";
        }
    }


    private static String addCardToDeck(String cardName, String deckName, String option, String username) {

        User user = User.getUserByName(username);
        Card card = Card.getCardByName(cardName);
        Deck deck = Deck.getDeckByName(deckName);
        if (user.getWallet().doesCardExist(card)) {
            if (Deck.doesDeckExist(deckName)) {
                if (!isDeckFull(deckName, option)) {
                    if (!canAddCard(deckName, cardName)) {
                        Response.success();
                        // remove card from user wallet and add that to deck
                        user.getWallet().removeCard(card);
                        deck.addCard(card, option);
                        return Responses.ADD_CARD_SUCCESSFUL.getLabel();
                    } else {
                        Response.error();
                        return "there are already three cards with name " + cardName + " in deck " + deckName;
                    }

                } else {
                    Response.error();
                    if (option.equals("side")) {
                        return "side deck is full";
                    } else {
                        return "main deck is full";
                    }
                }
            } else {
                Response.error();
                return "deck with name " + deckName + " does not exist";
            }
        } else {
            Response.error();
            return "card with name " + cardName + " does not exist";
        }
    }


    private static String removeCardFromDeck(String deckName, String cardName, String option, String username) {

        User user = User.getUserByName(username);
        Card card = Card.getCardByName(cardName);
        if (Deck.doesDeckExist(deckName)) {
            if (Deck.doesCardAvailableInDeck(cardName, deckName, option)) {
                Response.success();
                user.getWallet().removeCard(card);
                User.setActiveDeck(username, deckName);
                return Responses.REMOVE_CARD_SUCCESSFUL.getLabel();

            } else {
                Response.error();
                return "card with name " + cardName + " does not exist in " + option + " deck";
            }
        } else {
            Response.error();
            return "deck with name " + deckName + " does not exist";
        }
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

    private static boolean isDeckFull(String deckName, String option) {

        return Deck.isDeckFull(deckName, option);
    }
}
