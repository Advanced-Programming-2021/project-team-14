package Controller.Handlers;

import Controller.Response;
import model.Strings;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class MonsterTributeHandler extends GameHandler {

    public String handle(JSONObject request, Game game) {
        Logger.log("monster tribute handler", "checking ...");

        String command = request.getString("command");

        int level = game.getBoard().getMainPlayer().getPlayingDeck().
                getMonsterByName(game.getSelectedCard().getCard().getName()).getLevel();

        if (level == 5 || level == 6) {

            if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 1) { // check for tribute cards
                response = Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
                return response;
            }

            if (request.getString("tributeCardAddress1") != null) {

                tributeChecker(Integer.parseInt(request.getString("tributeCardAddress1")), 1000, game);
            }

            response = Strings.TRIBUTE_ONE_CARD.getLabel();
            Response.add("needTribute", "true");
            Response.add("tributeNumber", "1");
            return response;

        } else if (level == 7 || level == 8) {

            if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 2) { // check for tribute cards
                response = Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
                return response;
            }

            if (request.getString("tributeCardAddress1") != null) {

                tributeChecker(Integer.parseInt(request.getString("tributeCardAddress1")),
                        Integer.parseInt(request.getString("tributeCardAddress2")), game);
            }

            response = Strings.TRIBUTE_TWO_CARD.getLabel();
            Response.add("needTribute", "true");
            Response.add("tributeNumber", "2");
            return response;
        }

        return super.handle(request, game);
    }


    public void tributeChecker(int tributeCardAddress1, int tributeCardAddress2, Game game) {

        if (game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress1).isEmpty()) {
            if (tributeCardAddress2 == 1000) {
                response = Strings.NO_MONSTERS_ON_THIS_ADDRESS_ONE_TRIBUTE.getLabel();
            } else if (game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress2).isEmpty()) {

                response = Strings.NO_MONSTERS_ON_THIS_ADDRESS_TWO_TRIBUTE.getLabel();
            } else if (!game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress2).isEmpty()) {

                response = Strings.NO_MONSTERS_ON_THIS_ADDRESS_TWO_TRIBUTE.getLabel();
            }

        } else if (!game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress1).isEmpty()) {

            if (game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress2).isEmpty()) {

                response = Strings.NO_MONSTERS_ON_THIS_ADDRESS_TWO_TRIBUTE.getLabel();
            }
        }
    }
}
