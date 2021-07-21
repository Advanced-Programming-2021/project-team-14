package model.game;

import Controller.Response;
import model.User;

import java.util.ArrayList;

public class Duel {
    private static Duel currentDuel;
    private static ArrayList<Game> games;

    static {
        games = new ArrayList<>();
    }

    private String winner;
    private String loser;
    private int winnerScore;
    private int loserScore;
    private String creatorNickname;
    private Player firstPlayer;
    private User mainUser;
    private Player secondPlayer;
    private User rivalUser;
    private Game game;
    private int numberOfRounds;
    private boolean isAI;
    private int round;


    public Duel(User mainUser, User rivalUser, int round) {

        this.isAI = false;
        this.round = 1;
        this.firstPlayer = new Player(mainUser, 0);
        this.secondPlayer = new Player(rivalUser, 0);
        this.mainUser = mainUser;
        this.rivalUser = rivalUser;
        setNumberOfRounds(round);
        currentDuel = this;
    }

    public Duel(User mainUser, int round) {                         // for ai player

        this.isAI = true;
        this.round = 1;
        this.firstPlayer = new Player(mainUser, 0);
        this.secondPlayer = new Player(User.getUserByUsername("aiPlayer"), 0);
        this.mainUser = mainUser;
        this.rivalUser = User.getUserByUsername("aiPlayer");
        setNumberOfRounds(round);
        setGame(new Game(mainUser, rivalUser, round));
        currentDuel = this;

    }

    public void setPlayers(User firstUser, User secondUser) {

        this.firstPlayer = new Player(firstUser, 0);
        this.secondPlayer = new Player(secondUser, 0);
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public static void emptyGames() {

        for (int i = 0; i < games.size(); i++) {
            games.remove(i);
        }
    }

    public static void setCurrentDuel(Duel currentDuel) {
        Duel.currentDuel = currentDuel;
    }

    public static Duel getCurrentDuel() {
        return currentDuel;
    }

    public void startNewRound() {
        round++;
        this.firstPlayer = new Player(User.getUserByUsername(firstPlayer.getUsername()), firstPlayer.getWinningRounds());
        this.secondPlayer = new Player(User.getUserByUsername(secondPlayer.getUsername()), secondPlayer.getWinningRounds());
        if (isAI)
            setGame(new Game(mainUser, rivalUser, round));
        else
            setGame(new Game(mainUser, rivalUser, round));
    }

    public boolean endDuelChecker() {

        if (this.numberOfRounds == 1) {
            if (games.size() == 1) {
                rewardPlayers();
                return true;
            }
        } else {

            if (firstPlayer.getWinningRounds() == 2 || secondPlayer.getWinningRounds() == 2) {
                rewardPlayers();
                return true;
            }
        }
        return false;
    }

    private void rewardPlayers() {
        findLoserAndWinner();
        User winnerUser = User.getUserByNickname(winner);
        User loserUser = User.getUserByNickname(loser);
        assert winnerUser != null;
        assert loserUser != null;

        if (this.numberOfRounds == 1) {

            winnerScore = 1000;
            winnerUser.increaseScore(1000);
            winnerUser.getWallet().increaseCash(1000 + findMaxLifePoint());
            loserUser.getWallet().increaseCash(100);

        } else {

            winnerScore = 3000;
            winnerUser.increaseScore(3000);
            winnerUser.getWallet().increaseCash(3000 + 3 * findMaxLifePoint());
            loserUser.getWallet().increaseCash(300);

        }


        loserScore = 0;
        winnerUser.updateDatabase();
        loserUser.updateDatabase();
    }

    private int findMaxLifePoint() {
        int maxLifePoint = 0;

        for (Game game : games) {
            if (game.getWinnerLifePoint() > maxLifePoint) {
                maxLifePoint = game.getWinnerLifePoint();
            }
        }
        return maxLifePoint;
    }

    private void findLoserAndWinner() {

        if (this.numberOfRounds == 1) {

            if (firstPlayer.getWinningRounds() == 1) {
                this.winner = firstPlayer.getNickname();
                this.loser = secondPlayer.getNickname();
            } else {
                this.winner = secondPlayer.getNickname();
                this.loser = firstPlayer.getNickname();
            }

        } else {

            if (firstPlayer.getWinningRounds() == 2) {
                this.winner = firstPlayer.getNickname();
                this.loser = secondPlayer.getNickname();
            } else {

                this.winner = secondPlayer.getNickname();
                this.loser = firstPlayer.getNickname();
            }
        }
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public int getRound() {
        return this.round;
    }

    public Game getGame() {
        return this.game;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public User getMainUser() {
        return mainUser;
    }

    public boolean isAI() {
        return this.isAI;
    }


    public User getRivalUser() {
        return rivalUser;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String endDuel(String response) {


        Duel.emptyGames();
        Response.add("isDuelEnded", "true");

        if (response.startsWith("Unexpectedly")) {
            return response;
        }

        return String.format("%s\nuser %s won Duel with score %d" +
                " and user %s lost with score %d!\n", response, winner, winnerScore, loser, loserScore);

    }
}