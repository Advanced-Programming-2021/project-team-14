package Controller;

import Controller.Handlers.*;
import model.Strings;
import model.User;
import model.game.Game;
import org.json.JSONObject;
import view.enums.CommandTags;
import view.enums.Regexes;

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
            Response.addMessage(showSelectedCard(request));
        }  else if (command.equals(CommandTags.SET_POSITION.getLabel())){
            Response.addMessage(setPosition(request));
        }  else if (command.equals(CommandTags.SET.getLabel())){
            Response.addMessage(set(request));
        }

        Response.addObject("game", game.getGameObject());
    }

    private static String set(JSONObject request) {

        Handler set = new CardExistenceHandler();
        set.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());
        return null;
    }

    private static String setPosition(JSONObject request) {
        Handler setPosition = new CardExistenceHandler();
        setPosition.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new CardStateHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());

        return setPosition.handle(request, game);
    }

    private static String showSelectedCard(JSONObject request) {
        Handler showCard = new CardExistenceHandler();
        showCard.linksWith(new CardStateHandler())
                .linksWith(new TaskHandler());
        return showCard.handle(request, game);
    }

    private static String select(JSONObject request) {

        Handler selection = new PositionValidityHandler();
        selection.linksWith(new CardExistenceHandler())
                .linksWith(new TaskHandler());

        return selection.handle(request, game);
    }
}
