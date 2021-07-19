package server.controller;

import Controller.enums.CommandTags;
import Controller.enums.Responses;
import com.google.gson.Gson;
import model.User;
import model.Wallet;
import model.card.Card;
import org.json.JSONObject;
import server.ServerResponse;

import java.util.ArrayList;
import java.util.Comparator;

public class ServerShopController {

    private static ServerResponse response;

    public static void processCommand(JSONObject request, ServerResponse response) {
        ServerShopController.response = response;

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.BUY_CARD.getLabel()))
            response.addMessage(buyCard(request.getString("cardName"), request.getString("username")));

        else if (commandTag.equals(CommandTags.SHOP_SHOW_ALL.getLabel()))
            response.addMessage(showAllCards());

        else if (commandTag.equals(CommandTags.GET_ALL_CARDS.getLabel()))
            response.addMessage(new Gson().toJson(Card.getCards()));

        else if (commandTag.equals(CommandTags.INCREASE_MONEY.getLabel()))
            response.addMessage(increaseMoney(request.getString("amount"), request.getString("token")));

        else if (commandTag.equals(view.enums.CommandTags.SHOW_CARD.getLabel()))
            response.addMessage(showCard(request));
    }


    private static String showCard(JSONObject request) {

        if (Card.doesCardExist(request.getString("card"))) {
            return Card.getCardByName(request.getString("card")).show();
        }
        return view.enums.CommandTags.CARD_NOT_FOUND.getLabel();
    }


    private static String increaseMoney(String amount, String username) {       //cheat increase money
        User.getUserByUsername(username).getWallet().increaseCash(Integer.parseInt(amount));
        response.success();
        return Responses.INCREASE_MONEY.getLabel();
    }


    private static String showAllCards() {
        ArrayList<Card> cardLoaders = Card.getCards();
        return sort(cardLoaders).toString().substring(1, cardLoaders.toString().length() - 1).replace("., ", ".\n");
    }

    public static ArrayList<Card> sort(ArrayList<Card> array) {
        array.sort(Comparator.comparing(Card::getName));
        return array;
    }

    private static String buyCard(String cardName, String username) {
        if (!Card.doesCardExist(cardName)) {
            response.error();
            return Responses.THERE_IS_NO_CARD_WITH_THIS_NAME.getLabel();   // card does not exist
        }

        User user = User.getUserByUsername(username);
        int price = Card.getCardByName(cardName).getPrice();
        Wallet userWallet = user.getWallet();
        if (!userWallet.isCashEnough(price)) {
            response.error();
            return Responses.NOT_ENOUGH_MONEY.getLabel();   // cash is not enough
        }

        if (Card.getCardByName(cardName).getNumber() == 0) {
            response.error();
            return Responses.CARD_NOT_AVAILABLE.getLabel();
        }

        userWallet.addCard(cardName);
        userWallet.decreaseCash(price);
        Card.getCardByName(cardName).changeNumber(-1);
        user.updateDatabase();
        response.success();
        return Responses.CARD_BOUGHT_SUCCESSFULLY.getLabel();   // card bought successfully
    }
}
