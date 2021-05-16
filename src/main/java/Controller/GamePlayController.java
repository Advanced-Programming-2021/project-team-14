package Controller;

import Controller.Handlers.*;
import model.Strings;
import model.User;
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
            Response.addMessage(showSelectedCard(request));
        }  else if (command.equals(CommandTags.SET_POSITION.getLabel())) {
            Response.addMessage(setPosition(request));
        } else if (command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
            Response.addMessage(flipSummon(request));
        } else if (command.equals(CommandTags.SUMMON.getLabel())) {
            Response.addMessage(summon(request));
        } else if (command.equals(CommandTags.SET.getLabel())) {
            Response.addMessage(set(request));
        } else if (command.equals(CommandTags.NEXT_PHASE.getLabel())) {
            Response.addMessage(nextPhase(request));
        } else if (command.equals(CommandTags.ATTACK.getLabel())) {
            Response.addMessage(attack(request));
        }else if (command.equals(CommandTags.DIRECT_ATTACK.getLabel())) {
            Response.addMessage(directAttack(request));
        }else if (command.equals(CommandTags.DESELECT.getLabel())) {
            Response.addMessage(deselect(request));
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

    private static String directAttack(JSONObject request) {
        Handler directAttack = new SelectedCardHandler();
        directAttack.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());
        return directAttack.handle(request, game);
    }

    private static String nextPhase(JSONObject request) {
        return new TaskHandler().handle(request, game);
    }


    private static String summon(JSONObject request) {
        Handler summon = new SelectedCardHandler();
        summon.linksWith(new MonsterTributeHandler())
                .linksWith(new CardPositionHandler())
                .linksWith(new CardTypeHandler("Monster"))
                .linksWith(new MonsterCardTypeHandler("Ritual"))
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());
        return summon.handle(request, game);
    }


    private static String flipSummon(JSONObject request) {
        Handler set = new SelectedCardHandler();
        set.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());
        return set.handle(request, game);
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
        setPosition.linksWith(new CardPositionHandler())
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

    private static String deselect(JSONObject request) {
        Handler selection = new SelectedCardHandler();
        selection.linksWith(new TaskHandler());
        return selection.handle(request, game);
    }
}
