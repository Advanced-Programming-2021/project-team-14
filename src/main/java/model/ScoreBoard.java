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
}
