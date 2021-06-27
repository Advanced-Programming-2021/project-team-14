package view;

import Controller.enums.Responses;
import model.Database;
import model.Strings;
import model.User;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.enums.CommandTags;
import view.enums.Menus;

public class DeckTest {
    private User user;

    @BeforeAll
    public static void prepareDataBase() {
        Database.loadCards();
    }

    @BeforeEach
    public void prepare() {
        user = new User("user", "pass", "nikoo");
        User.addUser(user);
        login();
        Request.getToken();
    }

    private void login() {
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.addData("username", "user");
        Request.addData("password", "pass");
        Request.send();
        Request.getToken();
    }

    private String sendRequest(CommandTags commandTag) {
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.setCommandTag(commandTag);
        Request.send();
        return Request.getMessage();
    }

    @Test
    void createSimpleDeck() {
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        String response = sendRequest(CommandTags.CREATE_DECK);
        Assertions.assertTrue(user.doesDeckExist("firstDeck"));
        Assertions.assertEquals(Responses.CREATE_DECK_SUCCESSFUL.getLabel(), response);
    }

    @Test
    void createDeckWithSimilarName() {
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        sendRequest(CommandTags.CREATE_DECK);
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        String response = sendRequest(CommandTags.CREATE_DECK);
        Assertions.assertEquals(String.format(Responses.DECK_ALREADY_EXIST.getLabel(), "firstDeck"), response);
    }

    @Test
    void removeExistingDeck() {
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        sendRequest(CommandTags.CREATE_DECK);
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        String response = sendRequest(CommandTags.DELETE_DECK);
        Assertions.assertFalse(user.doesDeckExist("firstDeck"));
        Assertions.assertEquals(Responses.DELETE_DECK_SUCCESSFUL.getLabel(), response);
    }

    @Test
    void removeNotExistingDeck() {
        Request.addData(Strings.DECK.getLabel(), "notExistingDeckName");
        String response = sendRequest(CommandTags.DELETE_DECK);
        Assertions.assertFalse(user.doesDeckExist("notExistingDeckName"));
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "notExistingDeckName"), response);
    }

    @Test
    void setExistingDeckAsActive() {
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        sendRequest(CommandTags.CREATE_DECK);
        Request.addData(Strings.DECK.getLabel(), "firstDeck");
        String response = sendRequest(CommandTags.ACTIVATE_DECK);
        Assertions.assertTrue(user.doesHaveActiveDeck());
        Assertions.assertEquals("firstDeck", user.getActiveDeck());
        Assertions.assertEquals(Responses.ACTIVATE_DECK_SUCCESSFUL.getLabel(), response);
    }

    @Test
    void setNotExistingDeckAsActive() {
        Request.addData(Strings.DECK.getLabel(), "testDeck");
        String response = sendRequest(CommandTags.ACTIVATE_DECK);
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "testDeck"), response);
    }

    @Test
    void addNotExistingCard() {
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "cardDeck", false);
        Assertions.assertEquals(String.format(Responses.CARD_NOT_EXIST.getLabel(), "Battle OX"), response);
    }

    @Test
    void addToNotExistingDeck() {
        user.getWallet().addCard("Battle OX");
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "cardDeck", false);
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "cardDeck"), response);
    }

    @Test
    void addCardToFullDeckMain() {
        createDeck("fullDeck");
        fillTheDeck("fullDeck", false);
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "fullDeck", false);
        Assertions.assertEquals(Responses.MAIN_DECK_IS_FULL.getLabel(), response);
    }

    @Test
    void addCardToFullDeckSide() {
        createDeck("fullDeck");
        fillTheDeck("fullDeck", true);
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "fullDeck", true);
        Assertions.assertEquals(Responses.SIDE_DECK_IS_FULL.getLabel(), response);
    }

    @Test
    void addForthCard() {
        createDeck("threeCardDeck");
        for (int i = 0; i < 3; i++) {
            user.getWallet().addCard("Battle OX");
            user.getDeck("threeCardDeck").addCard("Battle OX", false);
        }
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "threeCardDeck", false);
        Assertions.assertEquals(String.format(Responses.ENOUGH_CARDS.getLabel(), "Battle OX", "threeCardDeck"), response);
    }

    @Test
    void addCard() {
        createDeck("simpleDeck");
        user.getWallet().addCard("Battle OX");
        String response = sendRequestForCardFeatures(CommandTags.ADD_CARD, "Battle OX", "simpleDeck", false);
        Assertions.assertEquals(Responses.ADD_CARD_SUCCESSFUL.getLabel(), response);
    }

    @Test
    void removeCard() {
        createDeck("deckToRemoveCardFrom");
        fillTheDeck("deckToRemoveCardFrom", false);
        String response = sendRequestForCardFeatures(CommandTags.REMOVE_CARD, "Battle OX", "deckToRemoveCardFrom", false);
        Assertions.assertEquals(Responses.REMOVE_CARD_SUCCESSFUL.getLabel(), response);
    }

    @Test
    void removeCardFromNotExistingDeck() {
        String response = sendRequestForCardFeatures(CommandTags.REMOVE_CARD, "Battle OX", "notExistingDeck", false);
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "notExistingDeck"), response);
    }

    @Test
    void removeNotExistingCard() {
        createDeck("emptyDeck");
        String response = sendRequestForCardFeatures(CommandTags.REMOVE_CARD, "Battle OX", "emptyDeck", false);
        Assertions.assertEquals(String.format(Responses.CARD_NOT_EXIST_IN_DECK.getLabel(), "Battle OX", "main"), response);
    }

    @Test
    void showAllDecks() {
        createDeck("#1");
        createDeck("#2");
        createDeck("#3");
        Request.setCommandTag(CommandTags.SHOW_ALL_DECKS);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.send();
        String expected = "Decks:\n" +
                "َActive Deck:\n" +
                "Other Decks:\n" +
                "#3: main deck 0, side deck 0, invalid\n" +
                "#1: main deck 0, side deck 0, invalid\n" +
                "#2: main deck 0, side deck 0, invalid";
        Assertions.assertEquals(expected, Request.getMessage());
    }

    @Test
    void showAnEmptyDeck() {
        createDeck("insideDeck");

        Request.setCommandTag(CommandTags.SHOW_DECK);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.addData(Strings.DECK_NAME.getLabel(), "insideDeck");
        Request.addBooleanData(Strings.SIDE_OPTION.getLabel(), false);
        Request.send();
        String expected = "Deck: insideDeck\n" +
                "main Deck:\n" +
                "Monsters:\n" +
                "Spells and Traps:";
        Assertions.assertEquals(expected, Request.getMessage());
    }

    @Test
    void showFilledDeck() {
        createDeck("insideFilledDeck");
        user.getWallet().addCard("Battle OX");
        user.getDeck("insideFilledDeck").addCard("Battle OX", false);
        user.getWallet().addCard("Battle OX");
        user.getDeck("insideFilledDeck").addCard("Trap Hole", false);
        Request.setCommandTag(CommandTags.SHOW_DECK);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.addData(Strings.DECK_NAME.getLabel(), "insideFilledDeck");
        Request.addBooleanData(Strings.SIDE_OPTION.getLabel(), false);
        Request.send();
        String expected = "Deck: insideFilledDeck\n" +
                "main Deck:\n" +
                "Monsters:\n" +
                "Battle OX : A monster with tremendous power, it destroys enemies with a swing of its axe.\n" +
                "Spells and Traps:\n" +
                "Trap Hole : When your opponent Normal or Flip Summons 1 monster with 1000 or more ATK: Target that monster; destroy that target.";
        Assertions.assertEquals(expected, Request.getMessage());
    }

    @Test
    void showNotExistingDeck() {
        Request.setCommandTag(CommandTags.SHOW_DECK);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.addData(Strings.DECK_NAME.getLabel(), "imaginaryDeck");
        Request.addBooleanData(Strings.SIDE_OPTION.getLabel(), false);
        Request.send();
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "imaginaryDeck"), Request.getMessage());
    }

    @Test
    void monsterSpellSeparatorTest() {

    }

    private void fillTheDeck(String deck, boolean isSide) {
        for (int i = 0; i < 61; i++) {
            String cardName = Card.getCards().get(i).getName();
            user.getWallet().addCard(cardName);
            user.getDeck(deck).addCard(cardName, isSide);
        }
    }

    private void createDeck(String deck) {
        Request.addData(Strings.DECK.getLabel(), deck);
        sendRequest(CommandTags.CREATE_DECK);
    }

    private String sendRequestForCardFeatures(CommandTags commandTags, String card, String deck, boolean isSide) {
        Request.setCommandTag(commandTags);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.addData(Strings.CARD.getLabel(), card);
        Request.addData(Strings.DECK.getLabel(), deck);
        Request.addBooleanData("side", isSide);
        Request.send();
        return Request.getMessage();
    }
}
