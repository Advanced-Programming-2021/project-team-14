package Controller.Handlers;

import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class MonsterCardTypeHandler extends GameHandler {

    private String monsterCardType;

    public MonsterCardTypeHandler(String monsterCardType, JSONObject request, Game game) {

        this.monsterCardType = monsterCardType;
        handle(request, game);
    }


    public String handle(JSONObject request, Game game) {
        Logger.log("monster card type handler", "checking ...");

        String command = request.getString("command");

        if (game.getBoard().getMainPlayer().getPlayingDeck().
                getMonsterByName(game.getSelectedCard().getCard().getName()).
                getMonsterCardType().getLabel().equals(monsterCardType)) {

            response = Strings.CARD_NOT_EXIST_IN_HAND_SUMMON.getLabel();
            return response;
        }

        return super.handle(request, game);
    }
}
