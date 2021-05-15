package Controller;

import Controller.Handlers.*;
import model.Strings;
import model.User;
import model.card.SelectedCard;
import model.game.Game;
import model.game.Phase;
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
        }  else if (command.equals(CommandTags.NEXT_PHASE.getLabel())){
            nextPhase(request);
        }  else if (command.equals(CommandTags.ATTACK.getLabel())){
            Response.addMessage(attack(request));
        }

        Response.addObject("game", game.getGameObject());
    }

    private static String attack(JSONObject request) {
        Handler attack = new SelectedCardHandler();
        attack.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new CardExistenceHandler())
                .linksWith(new TaskHandler());

        return attack.handle(request, game);
    }

    private static void nextPhase(JSONObject request) {
        new TaskHandler().handle(request, game);
    }

    private static String set(JSONObject request) {

        Handler set = new SelectedCardHandler();
        set.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());
        return set.handle(request, game);
    }

    private static String setPosition(JSONObject request) {
        Handler setPosition = new SelectedCardHandler();
        setPosition.linksWith(new CardExistenceHandler())
                .linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new CardStateHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());

        return setPosition.handle(request, game);
    }

    private static String showSelectedCard(JSONObject request) {
        Handler showCard = new SelectedCardHandler();
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
