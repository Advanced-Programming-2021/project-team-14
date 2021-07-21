package server.controller;

import Controller.Handlers.*;
import com.google.gson.Gson;
import model.SaveGameLogs;
import model.Strings;
import model.TV;
import model.User;
import model.card.Card;
import model.game.Duel;
import org.json.JSONObject;
import server.ServerResponse;
import view.enums.CommandTags;

public class ServerGamePlayController {

    private static Duel duel;
    private static SaveGameLogs recorder;
    private static ServerResponse response;

    public static void startAGame(User first, User second, int rounds) {
        duel = new Duel(first, second, rounds);
        recorder = new SaveGameLogs(duel);
        TV.addOnlineGame(duel);
//        response.add("duel", new Gson().toJson(duel));
//        response.add("recorder", new Gson().toJson(recorder));
    }

    public static void startGameWithAi(User first, int rounds, ServerResponse response) {
        duel = new Duel(first, rounds);
        recorder = new SaveGameLogs(duel);
        TV.addOnlineGame(duel);
        response.add("Duel", new Gson().toJson(duel));
//        response.add("recorder", new Gson().toJson(recorder));
    }


    public static void processCommand(JSONObject request, ServerResponse response, User user) {
        ServerGamePlayController.response = response;

        response.add("isDuelEnded", "false");
        String command = request.getString(Strings.COMMAND.getLabel());
        FieldController.handle(duel.getGame());
        response.error();

        if (!duel.getGame().getBoard().getMainPlayer().getTurnLogger().isTemporarilyChanged()) {
            if (command.equals(CommandTags.SET_POSITION.getLabel())) {
                response.addMessage(setPosition(request));
            } else if (command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
                response.addMessage(flipSummon(request));
            } else if (command.equals(CommandTags.RITUAL_SUMMON.getLabel())) {
                response.addMessage(ritualSummon(request));
            } else if (command.equals(CommandTags.SPECIAL_SUMMON.getLabel())) {
                response.addMessage(specialSummon(request));
            } else if (command.equals(CommandTags.SUMMON.getLabel())) {
                response.addMessage(summon(request));
            } else if (command.equals(CommandTags.SET.getLabel())) {
                response.addMessage(set(request));
            } else if (command.equals(CommandTags.NEXT_PHASE.getLabel())) {
                response.addMessage(nextPhase(request));
            } else if (command.equals(CommandTags.ATTACK.getLabel())) {
                response.addMessage(attack(request));
            } else if (command.equals(CommandTags.DIRECT_ATTACK.getLabel())) {
                response.addMessage(directAttack(request));
            }
        } else {
            response.addMessage("its not your turn to do such an action :(");
        }
        if (command.equals(CommandTags.SHOW_SELECTED_CARD.getLabel())) {
            response.addMessage(showSelectedCard(request));
        } else if (command.equals(CommandTags.SHOW_GRAVEYARD.getLabel())) {
            response.addMessage(showGraveyard(request));
        } else if (command.equals(CommandTags.SHOW_CARD.getLabel())) {
            response.addMessage(showCard(request));
        } else if (command.equals(CommandTags.SELECT.getLabel())) {
            response.addMessage(select(request));
        } else if (command.equals(CommandTags.SELECT_FORCE.getLabel())) {
            response.addMessage(selectForce(request));
        } else if (command.equals(CommandTags.WIN_GAME.getLabel())) {
            response.addMessage(winGame(request));
        } else if (command.equals(CommandTags.DESELECT.getLabel())) {
            response.addMessage(deselect(request));
        } else if (command.equals(CommandTags.ACTIVATE_EFFECT.getLabel())) {
            if (duel.getGame().getBoard().getMainPlayer().getTurnLogger().isTemporarilyChanged()) {
                response.addMessage(activateEffectOnRivalsTurn(request));
            } else {
                response.addMessage(activateEffect(request));
            }
        } else if (command.equals(CommandTags.INCREASE_LIFE_POINT.getLabel())) {
            response.addMessage(increaseLifePoint(request));
        } else if (command.equals(CommandTags.CANCEL_ACTIVATION.getLabel())) {
            activationCanceled(request);
        }

        if (response.isSuccess())
            recorder.addLog(request);
        response.addObject("game", duel.getGame().getGameObject());
    }

    private static String specialSummon(JSONObject request) {
        return "there is no way you could special summon a monster";
    }

    private static String ritualSummon(JSONObject request) {

        return "there is no way you could ritual summon a monster";
    }

    private static String winGame(JSONObject request) {

        String string = String.format("Unexpectedly user %s won!", duel.getGame().getBoard().getMainPlayer().getNickname());
        return duel.endDuel(string);
    }

    private static String selectForce(JSONObject request) {

        if (Card.doesCardExist((request.getString("card")).trim())) {
            duel.getGame().getBoard().getMainPlayer().getHand().addCard(Card.getCardByName((request.getString("card")).trim()));
            return CommandTags.CARD_ADDED_SUCCESSFULLY.getLabel();
        }
        return CommandTags.CARD_NOT_FOUND.getLabel();
    }

    private static String showCard(JSONObject request) {

        if (Card.doesCardExist(request.getString("card"))) {
            return Card.getCardByName(request.getString("card")).show();
        }
        return CommandTags.CARD_NOT_FOUND.getLabel();
    }

    private static void activationCanceled(JSONObject request) {
        ChainHandler.run();
        duel.getGame().getBoard().getRivalPlayer().getTurnLogger().setTemporarilyChanged(false);
    }

    private static String activateEffectOnRivalsTurn(JSONObject request) {
        Handler activateEffect = new SelectedCardHandler();
        activateEffect.linksWith(new CardTypeHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new DataHandler())
                .linksWith(new TaskHandler());
        return activateEffect.handle(request, duel);
    }


    private static String increaseLifePoint(JSONObject request) {
        Handler taskHandler = new TaskHandler();
        return taskHandler.handle(request, duel);
    }

    private static String activateEffect(JSONObject request) {

        Handler activateEffect = new SelectedCardHandler();
        activateEffect.linksWith(new CardTypeHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new EffectHandler())
                .linksWith(new DataHandler())
                .linksWith(new TaskHandler());
        return activateEffect.handle(request, duel);
    }

    private static String attack(JSONObject request) {
        Handler attack = new SelectedCardHandler();
        attack.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new CardExistenceHandler())
                .linksWith(new EffectHandler())
                .linksWith(new TaskHandler());
        return attack.handle(request, duel);
    }

    private static String directAttack(JSONObject request) {
        Handler directAttack = new SelectedCardHandler();
        directAttack.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TaskHandler());
        return directAttack.handle(request, duel);
    }

    private static String nextPhase(JSONObject request) {
        return new TaskHandler().handle(request, duel);
    }

    private static String summon(JSONObject request) {

        Handler summon = new SelectedCardHandler();
        summon.linksWith(new CardPositionHandler())
                .linksWith(new CardTypeHandler())
                .linksWith(new MonsterCardTypeHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new MonsterTributeHandler())
                .linksWith(new EffectHandler())
                .linksWith(new TaskHandler());
        return summon.handle(request, duel);
    }


    private static String flipSummon(JSONObject request) {
        Handler set = new SelectedCardHandler();
        set.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new CardStateHandler())
                .linksWith(new TaskHandler());
        return set.handle(request, duel);
    }


    private static String set(JSONObject request) {
        Handler set = new SelectedCardHandler();
        set.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new EmptyPlaceHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new MonsterTributeHandler())
                .linksWith(new TaskHandler());
        return set.handle(request, duel);
    }

    private static String setPosition(JSONObject request) {
        Handler setPosition = new SelectedCardHandler();
        setPosition.linksWith(new CardPositionHandler())
                .linksWith(new PhaseHandler())
                .linksWith(new CardStateHandler())
                .linksWith(new TurnLogHandler())
                .linksWith(new TaskHandler());

        return setPosition.handle(request, duel);
    }

    private static String showSelectedCard(JSONObject request) {
        Handler showCard = new SelectedCardHandler();
        showCard.linksWith(new CardStateHandler())
                .linksWith(new TaskHandler());
        return showCard.handle(request, duel);
    }


    private static String showGraveyard(JSONObject request) {

        Handler set = new TaskHandler();

        return set.handle(request, duel);
    }


    private static String select(JSONObject request) {
        Handler selection = new PositionValidityHandler();
        selection.linksWith(new CardExistenceHandler())
                .linksWith(new TaskHandler());
        return selection.handle(request, duel);
    }

    private static String deselect(JSONObject request) {
        Handler selection = new SelectedCardHandler();
        selection.linksWith(new TaskHandler());
        return selection.handle(request, duel);
    }
}
