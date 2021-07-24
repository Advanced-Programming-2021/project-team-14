//package Controller;
//
//import model.Strings;
//import model.User;
//import org.json.JSONObject;
//import view.enums.CommandTags;
//
//public class DuelController {
//    public static void processCommand(JSONObject request) {
//
//        String command = request.getString("command");
//        if (command.equals(CommandTags.START_DUEL.getLabel()))
//            Response.addMessage(startGame(request));
//        else if (command.equals(CommandTags.START_DUEL_AI.getLabel()))
//            Response.addMessage(startGameAi(request));
//    }
//
//    private static String startGame(JSONObject request) {
//        Response.error();
//        if (!User.doesUsernameExist(request.getString(Strings.SECOND_PLAYER.getLabel())))
//            return Strings.PLAYER_NOT_EXIST.getLabel();
//        if (request.getString(Strings.SECOND_PLAYER.getLabel()).equals(request.getString(Strings.TOKEN.getLabel())))
//            return Strings.SAME_SECOND_PLAYER.getLabel();
//        User first = User.getUserByUsername(request.getString(Strings.TOKEN.getLabel()));
//        User second = User.getUserByUsername(request.getString(Strings.SECOND_PLAYER.getLabel()));
//        if (!first.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), first.getUsername());
//        if (!second.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), second.getUsername());
//        if (!first.getDeck(first.getActiveDeck()).isValid())
//            return String.format(Strings.INVALID_DECK.getLabel(), first.getUsername());
//        if (!second.getDeck(second.getActiveDeck()).isValid())
//            return String.format(Strings.INVALID_DECK.getLabel(), first.getUsername());
//        int rounds = request.getInt(Strings.ROUNDS_NUMBER.getLabel());
//        if (rounds != 1 && rounds != 3)
//            return Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel();
//        Response.success();
//        first.increaseGamesPlayed();
//        second.increaseGamesPlayed();
//        GamePlayController.startAGame(first, second, rounds);
//        return String.format(Strings.START_DUEL.getLabel(), first.getUsername(), second.getUsername());
//    }
//
//    private static String startGameAi(JSONObject request) {
//        Response.error();
//        User first = User.getUserByUsername(request.getString(Strings.TOKEN.getLabel()));
//        if (!first.doesHaveActiveDeck()) return String.format(Strings.NO_ACTIVE_DECK.getLabel(), first.getUsername());
//         if (!first.getDeck(first.getActiveDeck()).isValid())
//            return String.format(Strings.INVALID_DECK.getLabel(), first.getUsername());
//        int rounds = request.getInt(Strings.ROUNDS_NUMBER.getLabel());
//        if (rounds != 1 && rounds != 3)
//            return Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel();
//        Response.success();
//        first.increaseGamesPlayed();
//        GamePlayController.startGameWithAi(first, rounds);
//        return String.format(Strings.START_DUEL.getLabel(), first.getUsername(), "AI");
//    }
//}
