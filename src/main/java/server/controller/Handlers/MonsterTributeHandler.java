package server.controller.Handlers;

import Controller.Response;
import model.Strings;
import model.card.Monster;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import org.json.JSONObject;
import server.ServerResponse;
import view.Logger;

public class MonsterTributeHandler extends GameHandler {

    public String handle(JSONObject request, Duel duel, ServerResponse response) {
        Game game = duel.getGame();
        Logger.log("monster tribute handler", "checking ...");

        if (game.getSelectedCard().getCard().getCardType() != CardType.MONSTER) return super.handle(request, duel, response);

        Monster monster = (Monster) game.getSelectedCard().getCard();
        int level = monster.getLevel();
        if (request.has("data") && request.getString("data").equals("cancel")) {
            response.success();
            return "canceled successfully";
        }

        if (level == 5 || level == 6) {
            if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 1) { // check for tribute cardLoaders
                response.error();
                return Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
            }
            if (!request.has("data")) {
                response.choice();
                return "please select 1 monster to tribute. " + game.getBoard().getMainPlayer().getMonsterZone().occupiedCells();
            } else {
                try {
                    int card = Integer.parseInt(request.getString("data"));
                    if (game.getBoard().getMainPlayer().getMonsterZone().getCell(card).isEmpty()) {
                        response.choice();
                        return "there is no monster in this location, please enter a valid number. " + game.getBoard().getMainPlayer().getMonsterZone().occupiedCells();
                    }
                }catch (Exception e) {
                    response.choice();
                    return "please enter a valid location";
                }
            }

        } else if (level == 7 || level == 8) {
            if (game.getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() < 2) { // check for tribute cardLoaders
                response.error();
                return Strings.NOT_ENOUGH_CARDS_FOR_TRIBUTE.getLabel();
            }
            if (!request.has("data")) {
                response.choice();
                return "please select 2 monster to tribute. <first> <second>" + game.getBoard().getMainPlayer().getMonsterZone().occupiedCells();
            } else {

                try {
                    int firstCard = Integer.parseInt(request.getString("data").split(" ")[0]);
                    int secondCard = Integer.parseInt(request.getString("data").split(" ")[1]);
                    if (firstCard > 5 || secondCard > 5 || firstCard == secondCard) throw new Exception();
                    if (game.getBoard().getMainPlayer().getMonsterZone().getCell(firstCard).isEmpty() ||
                        game.getBoard().getMainPlayer().getMonsterZone().getCell(secondCard).isEmpty()) {
                        response.choice();
                        return "there is no monster in this location, please enter a valid number. " + game.getBoard().getMainPlayer().getMonsterZone().occupiedCells();
                    }
                }catch (Exception e) {
                    response.choice();
                    return "please enter a valid location";
                }
            }
        }
        response.success();
        return super.handle(request, duel, response);
    }

}
