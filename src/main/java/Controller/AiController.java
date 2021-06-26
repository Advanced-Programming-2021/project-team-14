package Controller;

import model.game.Game;
import model.game.Player;
import view.GamePlayMenu;

public class AiController {

    private static String command;

    public static void run(Game game) {
        String inputCommand;

        GamePlayMenu commandChecker = new GamePlayMenu();
        Player mainPlayer = game.getBoard().getMainPlayer();
        Player aiPlayer = game.getBoard().getMainPlayer();

        commandChecker.commandCheckers("select --hand 1");
        commandChecker.commandCheckers("");
        commandChecker.commandCheckers("select --hand 1");
    }
}
