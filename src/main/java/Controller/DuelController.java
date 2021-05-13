package Controller;

import model.Strings;
import model.User;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class DuelController {
    private static Game game;
    public static void processCommand(JSONObject request) {

        String command = request.getString("view");
        if (command.equals(CommandTags.START_DUEL.getLabel()))
            Response.addMessage(startGame(request));
    }

    private static String startGame(JSONObject request) {
        if (!User.doesUsernameExist(request.getString("second-player")))
            return Strings.PLAYER_NOT_EXIST.getLabel();
        User first = User.getUserByName(request.getString("token"));
        User second = User.getUserByName(request.getString("second-player"));
        if (first.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), first.getUsername());
        if (second.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), second.getUsername());
        if (!first.getDeck(first.getActiveDeck()).isValid()) return String.format(Strings.INVALID_DECK.getLabel(), first.getUsername());
        if (!second.getDeck(second.getActiveDeck()).isValid()) return String.format(Strings.INVALID_DECK.getLabel(), first.getUsername());
        int rounds = request.getInt("round");
        if (rounds != 1 && rounds != 3)
            return Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel();
        game = new Game(first, second, rounds);
        return String.format(Strings.START_DUEL.getLabel(), first.getUsername(), second.getUsername());
    }
}
