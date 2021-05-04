
package Controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import model.Strings;
import model.User;
import org.json.JSONObject;

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

//        else if (commandTag.equals(CommandTags.ADD_CARD.getLabel()))
//            Response.addMessage(addCardToDeck(request.getString(Strings.CARD.getLabel()),
//                    request.getString(Strings.DECK.getLabel()),
//                    request.getString(Strings.OPTION.getLabel()),
//                    user));
//
//        else if (commandTag.equals(CommandTags.REMOVE_CARD.getLabel()))
//            Response.addMessage(removeCardFromDeck(request.getString("deck"),
//                    request.getString("card"),
//                    request.getString("option"),
//                    request.getString("token")));
//
//        else if (commandTag.equals(CommandTags.SHOW_ALL_DECKS.getLabel()))
//            Response.addMessage(showAllDecks(request.getString("token")));


//        else if (commandTag.equals(CommandTags.SHOW_DECK.getLabel()))
//            Response.addMessage(showDeck(request.getString("deck-name"), request.getString("option")));
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


//    private static String addCardToDeck(String cardName, String deckName, String option, User user) {
//
//        if (user.getWallet().doesCardExist(cardName)) {
//            if (user.doesDeckExist(deckName)) {
//                Deck deck = user.getDeck(deckName);
//                if (!deck.isDeckFull(option)) {
//                    if (!deck.canAddCard(cardName)) {
//                        Response.success();
//                        user.getDeck(deckName).addCard(cardName, option);
//                        user.updateDatabase();
//                        return Responses.ADD_CARD_SUCCESSFUL.getLabel();
//                    }
//                    Response.error();
//                    return String.format(Responses.ENOUGH_CARDS.getLabel(), cardName, deckName);
//                }
//                Response.error();
//                return (option.equals(CommandTags.SIDE.getLabel()) ? Responses.MAIN_DECK_IS_FULL : Responses.SIDE_DECK_IS_FULL).getLabel();
//            }
//            Response.error();
//            return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
//        }
//        Response.error();
//        return String.format(Responses.CARD_NOT_EXIST.getLabel(), cardName);
//    }


//    private static String removeCardFromDeck(String deckName, String cardName, String option, String username) {
//
//        User user = User.getUserByName(username);
//        if (user.doesDeckExist(deckName)) {
//            Deck deck = user.getDeck(deckName);
//            if (deck.isCardAvailableInDeck(cardName, option)) {
//                Response.success();
//                deck.removeCard(cardName, option);
//                user.getWallet().addCard(cardName);
//                return Responses.REMOVE_CARD_SUCCESSFUL.getLabel();
//
//            }
//            Response.error();
//            return String.format(Responses.CARD_NOT_EXIST_IN_DECK.getLabel(), cardName, option);
//        }
//        Response.error();
//        return String.format(Responses.DECK_NOT_EXIST.getLabel(), deckName);
//    }


//    private static String showAllDecks(String username) {
//
//        User user = User.getUserByName(username);
//        String decksTitle = "Decks:";
//        String activeDecksTitle = "Active deck:";
//        String activeDecks = "";
//        String otherDecksTitle = "Other decks:";
//        String otherDecks = "";
//
//
//        return decksTitle + "\n" + activeDecksTitle + "\n" + activeDecks + "\n" + otherDecksTitle + "\n" + otherDecks;
//    }


//    private static String showDeck(String deckName, String option) {
//
//        Deck deck = Deck.getDeckByName(deckName);
//        String deckNamePart = String.format("Deck: %s\n", deckName);
//        String optionPart;
//        if (option.equals("side")) {
//            optionPart = "Side deck:\n";
//        } else {
//            optionPart = "Main deck:\n";
//        }
//
//        String monsterPart = "Monsters:";
//        String monsterCards = "";
//        Collections.sort(deck.getCardNames());
//
//        monsterCards = collectCards(CardType.MONSTER, CardType.MONSTER, deck);
//
//        String trapSpellPart = "Spell and Traps:";
//        String spellAndTrapCards = "";
//        trapSpellPart = collectCards(CardType.SPELL, CardType.TRAP, deck);
//
//        return deckNamePart + optionPart + monsterPart + monsterCards + trapSpellPart + spellAndTrapCards;
//    }


//    private static String collectCards(CardType cardType1, CardType cardType2, Deck deck) {
//
//        String monsterCards = "";
//        for (String cardName : deck.getCardNames()) {
//
//            if (Card.getCardByName(cardName).getCardType().equals(cardType1) ||
//                Card.getCardByName(cardName).getCardType().equals(cardType2)) {
//                monsterCards += cardName + ": " + Card.getCardByName(cardName).getDescription() + "\n";
//            }
//        }
//        return monsterCards;
//    }


}