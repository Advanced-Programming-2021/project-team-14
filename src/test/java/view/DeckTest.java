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

    @BeforeEach
    public void prepare() {
        user = new User("user", "pass", "nikoo");
        User.addUser(user);
        login();
        Request.getToken();
    }
    @BeforeAll
    public static void prepareDataBase(){
        Database.loadCards();
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
        String response = sendRequestToBuyCard("Battle OX", "cardDeck");
        Assertions.assertEquals(String.format(Responses.CARD_NOT_EXIST.getLabel(), "Battle OX"), response);
    }

    @Test
    void addToNotExistingDeck() {
        user.getWallet().addCard("Battle OX");
        String response = sendRequestToBuyCard("Battle OX", "cardDeck");
        Assertions.assertEquals(String.format(Responses.DECK_NOT_EXIST.getLabel(), "cardDeck"), response);
    }

//    @Test
//    void addCardToFullDeckMain() {
//        createDeck("fullDeck");
//        fillTheDeck("fullDeck");
//        String response = sendRequestToBuyCard("Battle OX", "fullDeck");
//        Assertions.assertEquals(Responses.MAIN_DECK_IS_FULL.getLabel(), response);
//    }

    private void fillTheDeck(String deck) {
        for (int i = 0; i < 70; i++) {
            String cardName = Card.getCards().get(i).getName();
            user.getWallet().addCard(cardName);
            user.getDeck(deck).addCard(cardName, false);
        }
    }

    private void createDeck(String deck) {
        Request.addData(Strings.DECK.getLabel(), deck);
        sendRequest(CommandTags.CREATE_DECK);
    }

    private String sendRequestToBuyCard(String card, String deck) {
        Request.setCommandTag(CommandTags.ADD_CARD);
        Request.addData("view", Menus.DECK_MENU.getLabel());
        Request.addData(Strings.CARD.getLabel(), card);
        Request.addData(Strings.DECK.getLabel(), deck);
        Request.addBooleanData("side", false);
        Request.send();
        return Request.getMessage();
    }
}
