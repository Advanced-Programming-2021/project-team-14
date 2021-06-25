package Controller.Handlers;

import Controller.Response;
import model.card.SelectedCard;
import model.card.enums.CardType;
import model.game.Chain;
import model.game.Duel;
import model.game.Game;
import model.game.Zone;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ChainHandler {
    private static Game game;
    private static Duel duel;
    private static final ArrayList<Chain> chains;

    static {
        chains = new ArrayList<>();
    }

    public static void prepare(Duel duel) {
        ChainHandler.duel = duel;
        game = duel.getGame();
    }

    public static void add(SelectedCard selectedCard, JSONObject request) {
        chains.add(new Chain(selectedCard, request));
    }

    public static void run() {
        Collections.reverse(chains);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < chains.size(); i++) {
            game.changeTurn();
            Chain chain = chains.get(0);
            if (checkAvailability(chain)) {
                game.setSelectedCard(chain.getSelectedCard());
                result.append("\n").append(new TaskHandler().handle(chain.getRequest(), duel));
                chains.remove(0);
            }else{
                result.append("\n").append(" :( the chain has been spoiled :(");
            }
        }
        Response.addMessage(result.toString());
    }

    private static boolean checkAvailability(Chain chain) {
        Zone zone = chain.getSelectedCard().getCard().getCardType() == CardType.MONSTER ? game.getBoard().getMainPlayer().getMonsterZone() : game.getBoard().getMainPlayer().getSpellZone();
        return !zone.getCell(chain.getSelectedCard().getCard().getPositionIndex()).isEmpty();
    }

    public static Chain getLastChain() {
        return chains.get(0);
    }

}

