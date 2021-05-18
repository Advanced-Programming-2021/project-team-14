package model.game;

import model.Strings;
import model.User;
import model.card.Card;
import model.card.SelectedCard;
import org.json.JSONObject;


public class Game {

    private Board board;
    private Phase phase;
    private SelectedCard selectedCard;
    private TurnLogger turnLogger;

    public TurnLogger getTurnLogger() {
        return turnLogger;
    }

    public SelectedCard getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(SelectedCard selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Game(User mainUser, User rivalUser) {
        this.board = new Board(new Player(mainUser), new Player(rivalUser));
        this.turnLogger = new TurnLogger();
        this.phase = Phase.DRAW_PHASE;
        nextPhase();
    }

    public JSONObject getGameObject() {
        JSONObject game = new JSONObject();
        game.put("board", board.toString());
        game.put("phase", phase);
        return game;
    }

    public Phase getPhase() {
        return phase;
    }

    private void changeTurn() {
        Player temp = board.getMainPlayer();
        board.setMainPlayer(board.getRivalPlayer());
        board.setRivalPlayer(temp);
    }

    public Board getBoard() {
        return board;
    }

    public String nextPhase() {
        switch (phase){
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
                changeTurn();
                turnLogger.reset();
                phase = Phase.DRAW_PHASE;
                deselect();
                return String.format(Strings.CHANGE_TURN_PRINT.getLabel(), phase, board.getMainPlayer().getNickname(), draw());

        }
        return String.format(Strings.PHASE_PRINT.getLabel(), phase);
    }


    private String draw() {
        if (!board.getMainPlayer().getHand().isFull()) {
            Card card = board.getMainPlayer().drawCard();
            return String.format(Strings.NEW_CARD_ADDED_TO_HAND.getLabel(), card.getName());
        }
        return "";
    }


    public void endGame(Player winner, Player loser) {

        Duel.setWinner(winner.getNickname());
        Duel.setLoser(loser.getNickname());

    }


    public void deselect() {
        this.selectedCard = null;
    }
}
