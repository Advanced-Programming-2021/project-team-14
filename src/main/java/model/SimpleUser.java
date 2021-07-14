package model;

import java.util.ArrayList;

public class SimpleUser {

    private static ArrayList<SimpleUser> allUsers;

    static {
        allUsers = new ArrayList<>();
    }

    private String username;
    private String nickname;
    private int score;
    private int rank;
    private int gamesPlayed;


    public SimpleUser(User user){
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.score = user.getScore();
        this.rank = user.getRank();
        this.gamesPlayed = user.getGamesPlayed();
        allUsers.add(this);
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public static ArrayList<SimpleUser> getAllUsers() {
        return allUsers;
    }

    public String getNickname() {
        return nickname;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return this.score;
    }

    public int getRank() {
        return this.rank;
    }
}
