package model;

import java.util.ArrayList;

public class ScoreBoard {
    private static ScoreBoard single_instance = null;
    private ArrayList<User> users;

    private ScoreBoard() {
    }

    public static ScoreBoard getInstance() {
        if (single_instance == null)
            single_instance = new ScoreBoard();
        return single_instance;
    }

    public void updateScoreboard() {
        users = User.getUsers();
        sortUsers();
        rankUsers();
    }

    private void rankUsers() {
        int rank = 1;
        int lastScore = users.get(0).getScore();
        users.get(0).setRank(rank);
        for (int i = 1; i < users.size(); i++) {
            if (users.get(i).getScore() != lastScore) rank++;
            lastScore = users.get(i).getScore();
            users.get(i).setRank(rank);
        }
    }

    public String getScoreboard() {
        return users.toString().substring(1, users.toString().length() - 1).replace(", ", "\n");
    }


    public ArrayList<User> getSortedUsers() {
        users = User.getUsers();
        sortUsers();
        return users;
    }


    private void sortUsers() {
        users.sort((user1, user2) -> {
            if (user1.getScore() == user2.getScore())
                return user1.getNickname().compareTo(user2.getNickname());
            return user1.getScore() > user2.getScore() ? -1 : 1;
        });
    }
}