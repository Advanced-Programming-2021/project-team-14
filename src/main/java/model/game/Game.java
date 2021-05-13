package model.game;

import model.User;


public class Game {


    private String creatorNickname;

    private int round;
    private Board board;
    private Phase phase;


    public Game(User mainUser, User rivalUser, int round) {
        this.round = round;
        this.creatorNickname = mainUser.getNickname();
        this.board = new Board(new Player(mainUser), new Player(rivalUser));
    }

    public int getRound() {
        return round;
    }

    public Phase getPhase() {
        return phase;
    }

    private void changeTurn() {

    }

    public Board getBoard() {
        return board;
    }

    public void nextPhase() {

    }
}