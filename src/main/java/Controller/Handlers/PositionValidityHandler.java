package Controller.Handlers;

import model.Strings;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;

public class PositionValidityHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel) {
        Game game = duel.getGame();

        String area = request.getString(Strings.AREA.getLabel());
        int position = request.getInt(Strings.POSITION.getLabel());

        switch (area) {
            case "monster":
            case "spell":
                if (isPositionValid(position, 5))
                    return Strings.INVALID_SELECTION.getLabel();
            case "hand":
                if (isPositionValid(position, game.getBoard().getMainPlayer().getHand().getSize()))
                    return Strings.INVALID_SELECTION.getLabel();
        }

        return super.handle(request, duel);
    }

    private boolean isPositionValid(int position, int limitation) {
        if (position < 1) return true;
        return position > limitation;
    }
}
