package Controller;

import Controller.Handlers.CardExistenceHandler;
import Controller.Handlers.Handler;
import Controller.Handlers.PositionValidityHandler;
import Controller.Handlers.SelectCardHandler;
import model.Strings;
import model.User;
import model.card.enums.State;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;

public class GamePlayController {
    private static Game game;

    public static void startAGame(User first, User second, int rounds) {
        game = new Game(first, second, rounds);
    }

    public static void processCommand(JSONObject request) {
        String command = request.getString(Strings.COMMAND.getLabel());

        if (command.equals(CommandTags.SELECT.getLabel())){
            Response.addMessage(select(request));
        } else if (command.equals(CommandTags.SHOW_SELECTED_CARD.getLabel())){
            Response.addMessage(showSelectedCard());
        }

        Response.addObject("game", game.getGameObject());
    }

    private static String showSelectedCard() {
        if (game.getSelectedCard() == null) return "no card is selected yet";
        if (game.getSelectedCard().getState() == State.DEFENSIVE_HIDDEN) return "card is not visible";
        return game.getSelectedCard().show();
    }

    private static String select(JSONObject request) {

        Handler selection = new PositionValidityHandler();
        selection.linksWith(new CardExistenceHandler())
                .linksWith(new SelectCardHandler());

        return selection.handle(request, game);
    }
}
