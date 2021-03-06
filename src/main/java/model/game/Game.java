package model.game;

import Controller.AiController;
import model.Strings;
import model.User;
import model.card.Card;
import model.card.SelectedCard;
import org.json.JSONObject;

import java.util.ArrayList;


public class Game extends Duel{

    private Board board;
    private Phase phase;
    private SelectedCard selectedCard;
    private String gameWinner;
    private String gameLoser;
   // private Duel duel;
    private int winnerLifePoint;
    private int loserLifePoint;
    private boolean isEnded;

    public Game(User mainUser, User rivalUser, int round) {
        super(mainUser, rivalUser, round);
        this.board = new Board(super.getFirstPlayer(), super.getSecondPlayer());
        this.phase = Phase.DRAW_PHASE;
        this.isEnded = false;

    }


//    public Game(Player mainUser, Player rivalUser, Duel duel, boolean isAI, int round) {
//
//        this.isAI = isAI;
//        this.board = new Board(mainUser, rivalUser);
//        this.duel = duel;
//        this.phase = Phase.DRAW_PHASE;
//        this.isEnded = false;
//        this.round = round;
//
//        nextPhase();
//    }

    public SelectedCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(SelectedCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public JSONObject getGameObject() {
        JSONObject game = new JSONObject();
        game.put("board", board.toString());
        game.put("phase", phase);
        game.put("round", super.getRound());
        return game;
    }

    public Phase getPhase() {
        return phase;
    }

    public void changeTurn() {
        if (!super.isAI()) {
            Player temp = board.getMainPlayer();
            board.setMainPlayer(board.getRivalPlayer());
            board.setRivalPlayer(temp);
        } else {
            Player temp = board.getMainPlayer();
            board.setMainPlayer(board.getRivalPlayer());
            board.setRivalPlayer(temp);
            draw();
            AiController.run(this);
            phase = Phase.DRAW_PHASE;
            temp = board.getMainPlayer();
            board.setMainPlayer(board.getRivalPlayer());
            board.setRivalPlayer(temp);
        }
    }

    public Board getBoard() {
        return board;
    }

    public String nextPhase() {
        switch (phase) {
            case DRAW_PHASE:
                phase = Phase.STANDBY_PHASE;
                break;
            case STANDBY_PHASE:
                phase = Phase.MAIN_PHASE_1;
                break;
            case BATTLE_PHASE:
                phase = Phase.MAIN_PHASE_2;
                break;
            case MAIN_PHASE_1:
                phase = Phase.BATTLE_PHASE;
                break;
            case MAIN_PHASE_2:
                phase = Phase.END_PHASE;
                break;
            case END_PHASE:
                changeOwnerShips();
                board.getMainPlayer().getTurnLogger().reset();
                phase = Phase.DRAW_PHASE;
                deselect();
                changeTurn();
                return String.format(Strings.CHANGE_TURN_PRINT.getLabel(), phase, board.getMainPlayer().getNickname(), draw());

        }
        return String.format(Strings.PHASE_PRINT.getLabel(), phase);
    }

    private void changeOwnerShips() {
        ArrayList<Card> changedOwnershipCards = board.getMainPlayer().getTurnLogger().getChangedOwnerCards();
        if (changedOwnershipCards.size() > 0) {
            changedOwnershipCards.forEach(card -> {
                board.getRivalPlayer().getMonsterZone().placeCard(card);
                board.getMainPlayer().getMonsterZone().getCell(card.getPositionIndex()).removeCard();
            });
        }
        board.getMainPlayer().getTurnLogger().getChangedOwnerCards().clear();
    }


    private String draw() {
        if (!board.getMainPlayer().getHand().isFull() && board.getMainPlayer().getTurnLogger().canDrawCard()) {
            Card card = board.getMainPlayer().drawCard();
            if (card != null) {
                return String.format(Strings.NEW_CARD_ADDED_TO_HAND.getLabel(), card.getName());
            }
            endGame(board.getRivalPlayer(), board.getMainPlayer());
            super.startNewRound();
            if (super.endDuelChecker()) return super.endDuel("");
        }
        return "";
    }



    public void endGame(Player winner, Player loser) {

        setWinner(winner.getNickname());
        setGameLoser(loser.getNickname());
        setLoserLifePoint(loser.getLifePoint());
        setWinnerLifePoint(winner.getLifePoint());

        winner.increaseWinningRounds(1);
        this.isEnded = true;
        Duel.addGame(this);
    }

    public void setGameLoser(String gameLoser) {
        this.gameLoser = gameLoser;
    }

    public void setWinner(String winner) {
        this.gameWinner = winner;
    }

    public void setLoserLifePoint(int loserLifePoint) {
        this.loserLifePoint = loserLifePoint;
    }

    public int getWinnerLifePoint() {
        return winnerLifePoint;
    }

    public void setWinnerLifePoint(int winnerLifePoint) {
        this.winnerLifePoint = winnerLifePoint;
    }

    public void deselect() {
        this.selectedCard = null;
    }
}
