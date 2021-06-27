package Controller;

import Controller.enums.Responses;
import model.Database;
import model.User;
import model.Wallet;
import model.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class ShopControllerTest {

    @Test
    public void increaseMoney() {
        login("main");
        Request.addData("view", Menus.SHOP_MENU.getLabel());
        Request.setCommandTag(CommandTags.INCREASE_MONEY);
        Request.addData("amount", "10000");
        Request.send();
        Assertions.assertEquals(Responses.INCREASE_MONEY.getLabel(), Request.getMessage());
    }

    @Test
    public void buyCard() {
        login("main");
        Request.addData("view", Menus.SHOP_MENU.getLabel());
        Request.setCommandTag(CommandTags.BUY_CARD);
        Request.addData("cardName", "Bitron");
        Request.send();
        Assertions.assertEquals(Responses.CARD_BOUGHT_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void notEnoughMoney() {
        login("MonsterRival");
        Request.addData("view", Menus.SHOP_MENU.getLabel());
        Request.setCommandTag(CommandTags.BUY_CARD);
        Wallet  wallet = User.getUserByUsername("MonsterRival").getWallet();
        wallet.decreaseCash(wallet.getCash()-10);
        Request.addData("cardName", "Bitron");
        Request.send();
        Assertions.assertEquals(Responses.NOT_ENOUGH_MONEY.getLabel(), Request.getMessage());
    }

    @Test
    public void buyNotExistingCard() {
        login("main");
        Request.addData("view", Menus.SHOP_MENU.getLabel());
        Request.setCommandTag(CommandTags.BUY_CARD);
        Request.addData("cardName", "aaaaaaaaa");
        Request.send();
        Assertions.assertEquals(Responses.THERE_IS_NO_CARD_WITH_THIS_NAME.getLabel(), Request.getMessage());
    }

    @Test
    public void showAllCards() {
        login("main");
        Request.addData("view", Menus.SHOP_MENU.getLabel());
        Request.setCommandTag(CommandTags.SHOP_SHOW_ALL);
        Request.send();
        ArrayList<Card> cards = Card.getCards();
        String response = ShopController.sort(cards).toString().substring(1, cards.toString().length() - 1).replace("., ", ".\n");

        Assertions.assertEquals(response, Request.getMessage());
    }

    private void login(String username) {
        Database.prepareDatabase();
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.addData("username", username);
        Request.addData("password", "123");
        Request.send();

        if (Request.isSuccessful())
            Request.getToken();
    }
}
