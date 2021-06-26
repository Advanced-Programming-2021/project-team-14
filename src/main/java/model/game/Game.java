package model.game;

import Controller.AiController;
import model.Strings;
import model.card.Card;
import model.card.SelectedCard;
import org.json.JSONObject;

import java.util.ArrayList;


public class Game {

    private Board board;
    private Phase phase;
    private SelectedCard selectedCard;
    private String winner;
    private String loser;
    private Duel duel;
    private int winnerLifePoint;
    private int loserLifePoint;
    private boolean isEnded;
    private boolean isAI;


    public Game(Player mainUser, Player rivalUser, Duel duel, boolean isAI) {

        this.isAI = isAI;
        this.board = new Board(mainUser, rivalUser);
        this.duel = duel;
        this.phase = Phase.DRAW_PHASE;
        this.isEnded = false;
        nextPhase();
    }

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
        game.put("round", duel.getRound());
        return game;
    }

    public Phase getPhase() {
        return phase;
    }

    public void changeTurn() {
        if (!isAI) {
            Player temp = board.getMainPlayer();
            board.setMainPlayer(board.getRivalPlayer());
            board.setRivalPlayer(temp);
        } else {
            AiController.run(this);
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
            duel.startNewRound();
            if (duel.endDuelChecker()) return duel.endDuel("");
        }
        return "";
    }


    public void endGame(Player winner, Player loser) {

        setWinner(winner.getNickname());
        setLoser(loser.getNickname());
        setLoserLifePoint(loser.getLifePoint());
        setWinnerLifePoint(winner.getLifePoint());

        winner.increaseWinningRounds(1);
        this.isEnded = true;
        Duel.addGame(this);
    }

    public String getLoser() {
        return loser;
    }

    public void setLoser(String loser) {
        this.loser = loser;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public int getLoserLifePoint() {
        return loserLifePoint;
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
