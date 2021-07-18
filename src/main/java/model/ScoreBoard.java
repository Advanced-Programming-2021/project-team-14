package model;

import java.util.ArrayList;

public class ScoreBoard {
    private static ArrayList<User> users;

    static {
        users = new ArrayList<>();
    }

    public static void setRankUsers(ArrayList<User> ranked) {
        users = ranked;
    }

    public static void setSortedUsers(ArrayList<User> users) {
        ScoreBoard.users = users;
    }

    public static ArrayList<User> getSortedUsers() {
        return users;
    }
}