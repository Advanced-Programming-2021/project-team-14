package Controller.Handlers;

import Controller.Response;
import model.Strings;
import model.card.Monster;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import view.Logger;

public class MonsterTributeHandler extends GameHandler {

    public String handle(JSONObject request, Duel duel) {
        Game game = duel.getGame();
        Logger.log("monster tribute handler", "checking ...");

        String command = request.getString("command");

        Monster monster = (Monster) game.getSelectedCard().getCard();
        int level = monster.getLevel();

        if (level == 5 || level == 6) {

            if (request.getString("tributeCardAddress1").equals("")) {

                if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 1) { // check for tribute cards
                    response = Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
                    Response.error();
                    return response;
                }

                tributeChecker(Integer.parseInt(request.getString("tributeCardAddress1")), 1000, game);
                if (response == null) {

                    return super.handle(request, duel);
                } else {
                    Response.error();
                    return response;
                }
            }

            response = Strings.TRIBUTE_ONE_CARD.getLabel();
            Response.add("needTribute", "true");
            Response.add("tributeNumber", "1");
            Response.error();
            return response;

        } else if (level == 7 || level == 8) {

            if (request.getString("tributeCardAddress1").equals("")) {

                if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 2) { // check for tribute cards
                    response = Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
                    Response.error();
                    return response;
                }

                tributeChecker(Integer.parseInt(request.getString("tributeCardAddress1")),
                        Integer.parseInt(request.getString("tributeCardAddress2")), game);
                if (response == null) {

                    return super.handle(request, duel);
                } else {
                    Response.error();
                    return response;
                }
            }

            response = Strings.TRIBUTE_TWO_CARD.getLabel();
            Response.add("needTribute", "true");
            Response.add("tributeNumber", "2");
            Response.error();
            return response;
        }

        return super.handle(request, duel);
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

            if (tributeCardAddress2 != 1000 && game.getBoard().getMainPlayer().getMonsterZone().getCell(tributeCardAddress2).isEmpty()) {

                response = Strings.NO_MONSTERS_ON_THIS_ADDRESS_TWO_TRIBUTE.getLabel();
            }
        }
    }
}
