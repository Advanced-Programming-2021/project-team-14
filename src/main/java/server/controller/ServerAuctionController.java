package server.controller;

import Controller.enums.Responses;
import com.google.gson.Gson;
import model.Auction;
import model.User;
import model.card.Card;
import org.json.JSONObject;
import server.ServerResponse;
import view.enums.CommandTags;

public class ServerAuctionController {

    private static ServerResponse response;

    public static void processCommand(JSONObject request, ServerResponse response, User user) {
        ServerAuctionController.response = response;

        String commandTag = request.getString("command");


        if (commandTag.equals(CommandTags.CREATE_AUCTION.getLabel()))
            response.addMessage(createAuction(request.getString("card"), user));
        else if (commandTag.equals(CommandTags.GET_AUCTIONS.getLabel()))
            response.addMessage(new Gson().toJson(Auction.getAuctions()));
    }

    private static String createAuction(String card, User user) {

        User user1 = user;
        if (!user.doesAuctionExist(card)) {

            response.success();
            user.addAuction(new Auction(Card.getCardByName(card)));
            return Responses.CREATE_AUCTION_SUCCESSFULLY.getLabel();

        }
        response.error();
        return Responses.AUCTION_ALREADY_EXISTS.getLabel();
    }
}