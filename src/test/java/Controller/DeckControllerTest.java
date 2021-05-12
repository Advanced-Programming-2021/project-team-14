package Controller;

import Controller.enums.Responses;
import model.Database;
import model.Strings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;


class DeckControllerTest {

    public void createNewDeckWithName(String deckName) {

        Request.setCommandTag(CommandTags.CREATE_DECK);
        Request.addDataToRequest(Regexes.CREATE_DECK.getLabel(), String.format("deck create %s", deckName), "deck");
        Request.send();
    }


    @BeforeEach
    public void initialize() {

        Database.readDataLineByLine("Resources\\Cards\\SpellTrap.csv");
        Database.readDataLineByLine("Resources\\Cards\\Monster.csv");
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.extractData("user login --username mainUser --password admin");
        Request.send();
        if (Request.isSuccessful()) {
            Request.getToken();
        }
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Database.prepareDatabase();
    }


    @Test
    public void createDeckTest() {

        createNewDeckWithName("newDeck");

        Assertions.assertEquals(Request.getResponse(), Responses.CREATE_DECK_SUCCESSFUL.getLabel());
    }


    @Test
    public void createDeckWithExistingNameTest() {

        createNewDeckWithName("existingDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());
        createNewDeckWithName("existingDeck");

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_ALREADY_EXIST.getLabel(), "existingDeck"));
    }


    @Test
    public void removeDeckTest() {

        createNewDeckWithName("removingDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.setCommandTag(CommandTags.DELETE_DECK);
        Request.addDataToRequest(Regexes.DELETE_DECK.getLabel(), "deck delete removingDeck", "deck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.DELETE_DECK_SUCCESSFUL.getLabel());
    }


    @Test
    public void removeDeckWithNotExistingOneTest() {

        Request.setCommandTag(CommandTags.DELETE_DECK);
        Request.addDataToRequest(Regexes.DELETE_DECK.getLabel(), "deck delete notExistingDeck", "deck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_NOT_EXIST.getLabel(), "notExistingDeck"));

    }


//    @Test
//    public void showAllDecksTest() {
//
//        Request.setCommandTag(CommandTags.CREATE_DECK);
//        Request.addDataToRequest(Regexes.CREATE_DECK.getLabel(), "deck create deckTemp", "deck");
//        Request.send();
//
//        Request.addData("view", Menus.DECK_MENU.getLabel());
//        Request.setCommandTag(CommandTags.SHOW_ALL_DECKS);
//        Request.send();
//
//        Assertions.assertEquals(Request.getResponse(), "Decks:\n" +
//                "َActive Deck:\n" +
//                "Other Decks:\n" +
//                "deckTemp: main deck 0, side deck 0, invalid");
//
//    }


    @Test
    public void setActiveDeck() {

        createNewDeckWithName("activatingDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());

        Request.setCommandTag(CommandTags.ACTIVATE_DECK);
        Request.addDataToRequest(Regexes.ACTIVATE_DECK.getLabel(), "deck set-activate activatingDeck", "deck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.ACTIVATE_DECK_SUCCESSFUL.getLabel());
    }


    @Test
    public void setActiveWithNotExistingDeck() {

        Request.setCommandTag(CommandTags.ACTIVATE_DECK);
        Request.addDataToRequest(Regexes.ACTIVATE_DECK.getLabel(), "deck set-activate notActivatingDeck", "deck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_NOT_EXIST.getLabel(), "notActivatingDeck"));

    }

    public void addCardToDeckTemp(String cardName, String deckName) {

        Request.addData("view", Menus.DECK_MENU.getLabel());

        Request.setCommandTag(CommandTags.ADD_CARD);
        Request.extractData(String.format("deck add-card --card %s --deck %s", cardName, deckName));
        Request.setOption(String.format("deck add-card --card %s --deck %s", cardName, deckName));
        Request.send();
    }

    @Test
    public void addCardToDeckTest() {

        Request.addDataToRequest("(mainUser)", "mainUser", "token");
        createNewDeckWithName("toAddedDeck");

        buyCardTemp("Haniwa");

        addCardToDeckTemp("Haniwa", "toAddedDeck");

        Assertions.assertEquals(Request.getResponse(), Responses.ADD_CARD_SUCCESSFUL.getLabel());
    }


    public void buyCardTemp(String cardName) {

        Request.addData("view", Menus.SHOP_MENU.getLabel());

        Request.setCommandTag(CommandTags.BUY_CARD);
        Request.addDataToRequest(Regexes.SHOP_BUY.getLabel(), String.format("shop buy %s", cardName), "cardName");
        Request.send();
    }


    @Test
    public void addCardToDeckWithEnoughCardsTest() {

        createNewDeckWithName("enoughCardsDeck");
        buyCardTemp("Bitron");
        buyCardTemp("Bitron");
        buyCardTemp("Bitron");
        buyCardTemp("Bitron");
        addCardToDeckTemp("Bitron", "enoughCardsDeck");
        addCardToDeckTemp("Bitron", "enoughCardsDeck");
        addCardToDeckTemp("Bitron", "enoughCardsDeck");
        addCardToDeckTemp("Bitron", "enoughCardsDeck");

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.ENOUGH_CARDS.getLabel(), "Bitron", "enoughCardsDeck"));
    }


    @Test
    public void addCardToNotExistingDeckTest() {


        buyCardTemp("Bitron");

        addCardToDeckTemp("Bitron", "NotExistingDeck");

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_NOT_EXIST.getLabel(), "NotExistingDeck"));
    }


    @Test
    public void addNotExistingCardToDeckTest() {

        addCardToDeckTemp("notExistingCard", "deckTemp");

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.CARD_NOT_EXIST.getLabel(), "notExistingCard"));
    }


    @Test
    public void addCardToFullSideDeckTest() {

        createNewDeckWithName("fullDeck");

        buyCardTemp("Haniwa");
        buyCardTemp("Haniwa");
        buyCardTemp("Haniwa");
        buyCardTemp("Bitron");
        buyCardTemp("Bitron");
        buyCardTemp("Bitron");
        buyCardTemp("Wattkid");
        buyCardTemp("Wattkid");
        buyCardTemp("Wattkid");
        buyCardTemp("Wattaildragon");
        buyCardTemp("Wattaildragon");
        buyCardTemp("Wattaildragon");
        buyCardTemp("Marshmallon");
        buyCardTemp("Marshmallon");
        buyCardTemp("Marshmallon");
        buyCardTemp("Scanner");

        addCardToDeckTemp("Haniwa", "fullDeck");
        addCardToDeckTemp("Haniwa", "fullDeck");
        addCardToDeckTemp("Haniwa", "fullDeck");
        addCardToDeckTemp("Bitron", "fullDeck");
        addCardToDeckTemp("Bitron", "fullDeck");
        addCardToDeckTemp("Bitron", "fullDeck");
        addCardToDeckTemp("Wattkid", "fullDeck");
        addCardToDeckTemp("Wattkid", "fullDeck");
        addCardToDeckTemp("Wattkid", "fullDeck");
        addCardToDeckTemp("Wattaildragon", "fullDeck");
        addCardToDeckTemp("Wattaildragon", "fullDeck");
        addCardToDeckTemp("Wattaildragon", "fullDeck");
        addCardToDeckTemp("Marshmallon", "fullDeck");
        addCardToDeckTemp("Marshmallon", "fullDeck");
        addCardToDeckTemp("Marshmallon", "fullDeck");
        addCardToDeckTemp("Scanner", "fullDeck");

        Assertions.assertEquals(Request.getResponse(), Responses.SIDE_DECK_IS_FULL.getLabel());
    }


    @Test
    public void removeCardFromDeckTest() {

        createNewDeckWithName("toRemovedDeck");

        buyCardTemp("Haniwa");

        addCardToDeckTemp("Haniwa", "toRemovedDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());

        Request.setCommandTag(CommandTags.REMOVE_CARD);
        Request.extractData("deck rm-card --card Haniwa --deck toRemovedDeck");
        Request.setOption("deck rm-card --card Haniwa --deck toRemovedDeck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), Responses.REMOVE_CARD_SUCCESSFUL.getLabel());
    }


    @Test
    public void removeNotExistingCardFromDeckTest() {

        createNewDeckWithName("toNotExistingRemovedDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());

        Request.setCommandTag(CommandTags.REMOVE_CARD);
        Request.extractData("deck rm-card --card notExistingCard --deck toNotExistingRemovedDeck");
        Request.setOption("deck rm-card --card notExistingCard --deck toNotExistingRemovedDeck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.CARD_NOT_EXIST_IN_DECK.getLabel(), "notExistingCard", Strings.MAIN_DECK.getLabel()));
    }


    @Test
    public void removeCardFromNotExistingDeckTest() {


        Request.setCommandTag(CommandTags.REMOVE_CARD);
        Request.extractData("deck rm-card --card Haniwa --deck notExistingDeck");
        Request.setOption("deck rm-card --card Haniwa --deck notExistingDeck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_NOT_EXIST.getLabel(), "notExistingDeck"));
    }


    @Test
    public void showMainDeckTest() {

        createNewDeckWithName("toShowMainDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());

        buyCardTemp("Haniwa");
        addCardToDeckTemp("Haniwa", "toShowMainDeck");

        Request.addData("view", Menus.DECK_MENU.getLabel());

        Request.setCommandTag(CommandTags.SHOW_DECK);
        Request.extractData("deck show --deck-name toShowMainDeck");
        Request.setOption("deck show --deck-name toShowMainDeck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), "Deck: toShowMainDeck\n" +
                "main Deck:\n" +
                "Monsters:\n" +
                "Haniwa : An earthen figure that protects the tomb of an ancient ruler.\n" +
                "Spells and Traps:");
    }


    @Test
    public void showNotExistingDeck() {

        Request.setCommandTag(CommandTags.SHOW_DECK);
        Request.extractData("deck show --deck-name notExistingDeck");
        Request.setOption("deck show --deck-name notExistingDeck");
        Request.send();

        Assertions.assertEquals(Request.getResponse(), String.format(Responses.DECK_NOT_EXIST.getLabel(), "notExistingDeck"));
    }
}