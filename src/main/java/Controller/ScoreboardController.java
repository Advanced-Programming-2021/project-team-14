package Controller;

import model.ScoreBoard;
import model.User;
import org.json.JSONObject;
import view.enums.CommandTags;

import java.util.ArrayList;

public class ScoreboardController {

    public static void processCommand(JSONObject request) {

        String commandTag = request.getString("command");

        if (commandTag.equals(CommandTags.SHOW_SCOREBOARD.getLabel()))
            Response.addMessage(stringify(getScoreboard()));

    }

    private static ArrayList<User> getScoreboard() {
        Response.success();
        ArrayList<User> users = rankUsers(sortUsers(User.getUsers()));
        ScoreBoard.setRankUsers(users);
        return users;
    }

    public static ArrayList<User> sortUsers(ArrayList<User> users) {
        users.sort((user1, user2) -> {
            if (user1.getScore() == user2.getScore())
                return user1.getNickname().compareTo(user2.getNickname());
            return user1.getScore() > user2.getScore() ? -1 : 1;
        });
        return users;
    }

    public static ArrayList<User> rankUsers(ArrayList<User> users) {
        int rank = 1;
        int lastScore = users.get(0).getScore();
        users.get(0).setRank(rank);
        for (int i = 1; i < users.size(); i++) {
            if (users.get(i).getScore() != lastScore) rank++;
            lastScore = users.get(i).getScore();
            users.get(i).setRank(rank);
        }
        return users;
    }

    public static String stringify(ArrayList<User> users) {
        return users.toString().substring(1, users.toString().length() - 1).replace(", ", "\n");
    }
}
