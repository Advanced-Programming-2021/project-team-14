package server.controller;

import model.Strings;
import model.User;
import org.json.JSONObject;
import server.ServerResponse;
import view.enums.CommandTags;


public class ServerDuelController {
    private static ServerResponse response;
    public static void processCommand(JSONObject request, ServerResponse response, User user) {
        System.out.println("hahhahhahahhh");
        ServerDuelController.response = response;
        String command = request.getString("command");
        if (command.equals(CommandTags.START_DUEL.getLabel()))
            response.addMessage(startGame(request, user));
        else if (command.equals(CommandTags.START_DUEL_AI.getLabel()))
            response.addMessage(startGameAi(request, user));
        }

    private static String startGame(JSONObject request,  User user) {
        response.error();
        if (!User.doesUsernameExist(request.getString(Strings.SECOND_PLAYER.getLabel())))
            return Strings.PLAYER_NOT_EXIST.getLabel();
        if (request.getString(Strings.SECOND_PLAYER.getLabel()).equals(user.getUsername()))
            return Strings.SAME_SECOND_PLAYER.getLabel();
        User second = User.getUserByUsername(request.getString(Strings.SECOND_PLAYER.getLabel()));
        if (!user.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), user.getUsername());
        if (!second.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), second.getUsername());
        if (!user.getDeck(user.getActiveDeck()).isValid())
            return String.format(Strings.INVALID_DECK.getLabel(), user.getUsername());
        if (!second.getDeck(second.getActiveDeck()).isValid())
            return String.format(Strings.INVALID_DECK.getLabel(), user.getUsername());
        int rounds = request.getInt(Strings.ROUNDS_NUMBER.getLabel());
        if (rounds != 1 && rounds != 3)
            return Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel();
        response.success();
        user.increaseGamesPlayed();
        second.increaseGamesPlayed();
        ServerGamePlayController.startAGame(user, second, rounds);
        return String.format(Strings.START_DUEL.getLabel(), user.getUsername(), second.getUsername());
    }

    private static String startGameAi(JSONObject request, User user) {
        response.error();
        if (!user.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), user.getUsername());
        if (!user.getDeck(user.getActiveDeck()).isValid())
            return String.format(Strings.INVALID_DECK.getLabel(), user.getUsername());
        int rounds = request.getInt(Strings.ROUNDS_NUMBER.getLabel());
        if (rounds != 1 && rounds != 3)
            return Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel();
        response.success();
        user.increaseGamesPlayed();
        ServerGamePlayController.startGameWithAi(user, rounds,response);
        return String.format(Strings.START_DUEL.getLabel(), user.getUsername(), "AI");
    }
}
