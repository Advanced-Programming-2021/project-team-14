package Controller;

import model.game.Game;
import model.game.Player;
import view.GamePlayMenu;

public class AiController {

    private static String command;

    public static void run(Game game) {
        String inputCommand;

        GamePlayMenu commandChecker = new GamePlayMenu();
        Player aiPlayer = game.getBoard().getMainPlayer();
        Player rivalPlayer = game.getBoard().getRivalPlayer();

        commandChecker.commandCheckers("select --hand 1");
        commandChecker.commandCheckers(">");
        commandChecker.commandCheckers(">");
        if (!aiPlayer.getMonsterZone().isFull()) {
            commandChecker.commandCheckers("summon");
        }
        commandChecker.commandCheckers(">");
        if (!rivalPlayer.getMonsterZone().isEmpty()) {
            commandChecker.commandCheckers("attack 1");
        } else {
            commandChecker.commandCheckers("attack direct");
        }
        commandChecker.commandCheckers(">");
        commandChecker.commandCheckers(">");

    }
}
