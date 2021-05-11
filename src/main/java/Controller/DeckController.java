
package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.Deck;
import model.Strings;
import model.User;
import model.card.Card;
import model.card.enums.CardType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class DeckController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");
        User user = User.getUserByName(request.getString(Strings.TOKEN.getLabel()));

        if (commandTag.equals(CommandTags.CREATE_DECK.getLabel()))
            Response.addMessage(createDeck(request.getString(Strings.DECK.getLabel()), user));

        else if (commandTag.equals(CommandTags.DELETE_DECK.getLabel()))
            Response.addMessage(removeDeck(request.getString(Strings.DECK.getLabel()), user));

        else if (commandTag.equals(CommandTags.ACTIVATE_DECK.getLabel()))
            Response.addMessage(setActiveDeck(request.getString(Strings.DECK.getLabel()), user));

        else if (commandTag.equals(CommandTags.ADD_CARD.getLabel()))
            Response.addMessage(addCardToDeck(request.getString(Strings.CARD.getLabel()),
                    request.getString(Strings.DECK.getLabel()),
                    request.getBoolean(Strings.SIDE_OPTION.getLabel()),
                    user));

        else if (commandTag.equals(CommandTags.REMOVE_CARD.getLabel()))
            Response.addMessage(removeCardFromDeck(request.getString(Strings.DECK.getLabel()),
                    request.getString(Strings.CARD.getLabel()),
                    request.getBoolean(Strings.SIDE_OPTION.getLabel()),
                    user));

        else if (commandTag.equals(CommandTags.SHOW_ALL_DECKS.getLabel()))
            Response.addMessage(showAllDecks(user));

        else if (commandTag.equals(CommandTags.SHOW_DECK.getLabel()))
            Response.addMessage(showDeck(request.getString(Strings.DECK_NAME.getLabel()), request.getBoolean(Strings.SIDE_OPTION.getLabel()), user));
    }

    private static String showAllDecks(User user) {
        HashMap<String, Deck> decks = new HashMap<>(user.getDecks());
        Response.success();
        String activeDeck = (user.getActiveDeck() == null) ? "" : "\n" + decks.remove(user.getActiveDeck()).toString();
        return String.format(Strings.DECKS_PRINT_FORMAT.getLabel(), activeDeck, stringifyDecks(new ArrayList<>(decks.values())));
    }

    private static String createDeck(String deckName, User user) {

        if (!user.doesDeckExist(deckName)) {

            Response.success();
            user.addDeck(deckName);
            user.updateDatabase();
            return Responses.CREATE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_ALREADY_EXIST.getLabel(), deckName);
    }


    private static String removeDeck(String deckName, User user) {

        if (user.doesDeckExist(deckName)) {

            Response.success();
            user.removeDeck(deckName);
            user.updateDatabase();
            return Responses.DELETE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }


    private static String setActiveDeck(String deckName, User user) {

        if (user.doesDeckExist(deckName)) {

            Response.success();
            user.setActiveDeck(deckName);
            user.updateDatabase();
            return Responses.ACTIVATE_DECK_SUCCESSFUL.getLabel();

        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }


    private static String addCardToDeck(String cardName, String deckName, Boolean isSideDeck, User user) {

        if (user.getWallet().doesCardExist(cardName)) {
            if (user.doesDeckExist(deckName)) {
                Deck deck = user.getDeck(deckName);
                if (!deck.isDeckFull(isSideDeck)) {
                    if (deck.canAddCard(cardName)) {
                        Response.success();
                        user.getWallet().removeCard(cardName);
                        user.getDeck(deckName).addCard(cardName, isSideDeck);
                        user.updateDatabase();
                        return Responses.ADD_CARD_SUCCESSFUL.getLabel();
                    }
                    Response.error();
                    return String.format(Responses.ENOUGH_CARDS.getLabel(), cardName, deckName);
                }
                Response.error();
                return (isSideDeck ? Responses.MAIN_DECK_IS_FULL : Responses.SIDE_DECK_IS_FULL).getLabel();
            }
            Response.error();
            return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
        }
        Response.error();
        return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
    }


    private static String removeCardFromDeck(String deckName, String cardName, boolean isSideDeck, User user) {

        if (user.doesDeckExist(deckName)) {
            Deck deck = user.getDeck(deckName);
            if (deck.isCardAvailableInDeck(cardName, isSideDeck)) {
                Response.success();
                deck.removeCard(cardName, isSideDeck);
                user.getWallet().addCard(cardName);
                user.updateDatabase();
                return Responses.REMOVE_CARD_SUCCESSFUL.getLabel();

            }
            Response.error();
            return String.format(Responses.CARD_NOT_EXIST_IN_DECK.getLabel(), cardName, (isSideDeck) ? Strings.SIDE_DECK.getLabel() : Strings.MAIN_DECK.getLabel());
        }
        Response.error();
        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
    }

    private static String showDeck(String deckName, boolean isSideDeck, User user) {
        if (!user.doesDeckExist(deckName)) {
            Response.error();
            return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
        }
        Response.success();
        Deck deck = user.getDeck(deckName);
        ArrayList<ArrayList<Card>> cards = separateCards(deck, isSideDeck);
        return String.format(Strings.DECK_CARDS_PRINT_FORMAT.getLabel(), deckName, isSideDeck ? Strings.SIDE.getLabel() : Strings.MAIN.getLabel(),
                stringifyCards(ShopController.sort(cards.get(0))), stringifyCards(ShopController.sort(cards.get(1))));
    }

    private static String stringifyDecks(ArrayList<Deck> arrayList) {
        if (arrayList.isEmpty()) return "";
        return arrayList.toString().substring(1, arrayList.toString().length() - 1).replace("valid, ", "valid\n");
    }

    private static String stringifyCards(ArrayList<Card> arrayList) {
        if (arrayList.isEmpty()) return "";
        return "\n" + arrayList.toString().substring(1, arrayList.toString().length() - 1).replace("., ", ".\n");
    }

    private static ArrayList<ArrayList<Card>> separateCards(Deck deck, boolean isSideDeck) {
        ArrayList<ArrayList<Card>> result = new ArrayList<>();
        ArrayList<Card> monsters = new ArrayList<>();
        ArrayList<Card> spellsTrap = new ArrayList<>();
        for (String cardName : deck.getCards(isSideDeck)) {
            Card card = Card.getCardByName(cardName);
            if (card.getCardType() == CardType.MONSTER)
                monsters.add(Card.getCardByName(cardName));
            else
                spellsTrap.add(Card.getCardByName(cardName));
        }
        result.add(monsters);
        result.add(spellsTrap);
        return result;
    }
}