package model.game;

import model.User;

public class Duel {

    private static String winner;
    private static String loser;
    private String creatorNickname;
    private int round;
    private Game game;


    public Duel(User mainUser, User rivalUser, int round) {

        this.round = round;
        this.game = new Game(mainUser, rivalUser);
    }

    public static String getWinner() {
        return winner;
    }

    public static void setWinner(String winner) {
        winner = winner;
    }

    public static String getLoser() {
        return loser;
    }

    public static void setLoser(String loser) {
        loser = loser;

    }

    public Game getGame() {
        return game;
    }

    public int getRound() {
        return round;
    }

}
