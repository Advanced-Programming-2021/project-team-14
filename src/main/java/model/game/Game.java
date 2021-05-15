package model.game;

import model.User;
import model.card.Card;
import model.card.SelectedCard;
import org.json.JSONObject;


public class Game {


    private String creatorNickname;

    private int round;
    private Board board;
    private Phase phase = Phase.START;
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

    public Game(User mainUser, User rivalUser, int round) {
        this.round = round;
        this.creatorNickname = mainUser.getNickname();
        this.board = new Board(new Player(mainUser), new Player(rivalUser));
        this.turnLogger = new TurnLogger();
    }
    public JSONObject getGameObject(){
        JSONObject game = new JSONObject();
        game.put("board", board.toString());
        game.put("phase", phase);
        return game;
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
            case START:
            case END_PHASE:
                phase = Phase.DRAW_PHASE;
                break;

        }
    }

    public void deselect() {
        this.selectedCard = null;
    }
}
