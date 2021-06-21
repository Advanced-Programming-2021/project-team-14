package model.game;

import model.User;

import java.util.ArrayList;

public class Duel {

    private static ArrayList<Game> games;

    static {
        games = new ArrayList<>();
    }

    private String winner;
    private String loser;
    private int winnerLifePoint;
    private int loserLifePoint;
    private String creatorNickname;
    private Player firstPlayer;
    private Game game;
    private Player secondPlayer;
    private int numberOfRounds;
    private boolean isAI;


    public Duel(User mainUser, User rivalUser, int round) {       // for 2 players mode

        this.isAI = false;
        this.firstPlayer = new Player(mainUser);
        this.secondPlayer = new Player(rivalUser);
        setNumberOfRounds(round);
        setGame(new Game(firstPlayer, secondPlayer, this, false));
    }

    public Duel(User mainUser, int round) {                         // for ai player

        this.isAI = true;
        this.firstPlayer = new Player(mainUser);
        this.secondPlayer = new Player(User.getUserByUsername("aiPlayer"));
        setNumberOfRounds(round);
        setGame(new Game(firstPlayer, secondPlayer, this, true));
    }

    public static void addGame(Game game) {
        games.add(game);
    }

    public void startNewRound() {
        if (isAI)
            setGame(new Game(firstPlayer, secondPlayer, this, true));
        else
            setGame(new Game(firstPlayer, secondPlayer, this, false));
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

            winnerUser.increaseScore(1000);
            winnerUser.getWallet().increaseCash(1000 + winnerLifePoint);
            loserUser.getWallet().increaseCash(100);

        } else {

            winnerUser.increaseScore(3000);
            winnerUser.getWallet().increaseCash(3000 + 3 * findMaxLifePoint());
            loserUser.getWallet().increaseCash(300);

        }
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

            this.loser = game.getLoser();
            this.winner = game.getWinner();

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

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public int getNumberOfRounds() {
        return numberOfRounds;
    }

    public void setNumberOfRounds(int numberOfRounds) {
        this.numberOfRounds = numberOfRounds;
    }

    public String getCreatorNickname() {
        return creatorNickname;
    }

    public void setCreatorNickname(String creatorNickname) {
        this.creatorNickname = creatorNickname;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String endDuel(String response) {

        return String.format("%s\nuser %s won Duel and user %s lost!", response, winner, loser);
    }

}