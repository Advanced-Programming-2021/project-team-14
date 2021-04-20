package Controller;

import model.ScoreBoard;
import org.json.JSONObject;
import view.enums.CommandTags;

public class ScoreboardController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.SHOW_SCOREBOARD.getLabel()))
            showScoreboard();

    }

    private static void showScoreboard() {
        ScoreBoard.getInstance().updateScoreboard();
        Response.success();
        Response.addMessage(ScoreBoard.getInstance().getScoreboard());
    }

}
