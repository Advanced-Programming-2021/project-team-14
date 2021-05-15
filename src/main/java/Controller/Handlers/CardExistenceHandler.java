package Controller.Handlers;

import model.Strings;
import model.game.Game;
import model.game.Player;
import model.game.Zone;
import org.json.JSONObject;
import view.Logger;

public class CardExistenceHandler extends GameHandler {
    public String handle(JSONObject request, Game game) {
        Logger.log("card existence handler", "checking ...");
        if (!request.getString(Strings.COMMAND.getLabel()).equals("select")) {
            if (game.getSelectedCard() == null) return "no card is selected yet";
        } else {
            String area = request.getString(Strings.AREA.getLabel());
            boolean isOpponent = request.getBoolean(Strings.OPPONENT_OPTION.getLabel());
            Player player = isOpponent ? game.getBoard().getRivalPlayer() : game.getBoard().getMainPlayer();
            int position = 1;

            switch (area) {
                case "monster":
                    position = request.getInt(Strings.POSITION.getLabel());
                    Zone monsterZone = player.getMonsterZone();
                    if (monsterZone.getCell(position).isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
                    break;
                case "spell":
                    Zone spellTrapZone = player.getSpellZone();
                    if (spellTrapZone.getCell(position).isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
                    break;
                case "field":
                    if (player.getFieldZone().isEmpty()) return Strings.NO_CARD_FOUND.getLabel();
                    break;
            }

        }

        return super.handle(request, game);
    }

}
