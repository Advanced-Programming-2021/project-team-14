package Controller;

import Controller.Handlers.*;
import model.Strings;
import model.User;
import model.card.Card;
import model.game.Duel;
import org.json.JSONObject;
import view.enums.CommandTags;

public class GamePlayController {

    private static Duel duel;

    public static void startAGame(User first, User second, int rounds) {

        duel = new Duel(first, second, rounds);
    }

    public static void startGameWithAi(User first, int rounds) {

        duel = new Duel(first, rounds);
    }


    public static void processCommand(JSONObject request) {

        Response.add("isDuelEnded", "false");
        String command = request.getString(Strings.COMMAND.getLabel());
        FieldController.handle(duel.getGame());
        Response.error();
        if (!duel.getGame().getBoard().getMainPlayer().getTurnLogger().isTemporarilyChanged()) {
            if (command.equals(CommandTags.SET_POSITION.getLabel())) {
                Response.addMessage(setPosition(request));
            } else if (command.equals(CommandTags.FLIP_SUMMON.getLabel())) {
                Response.addMessage(flipSummon(request));
            } else if (command.equals(CommandTags.RITUAL_SUMMON.getLabel())) {
                Response.addMessage(ritualSummon(request));
            } else if (command.equals(CommandTags.SPECIAL_SUMMON.getLabel())) {
                Response.addMessage(specialSummon(request));
            } else if (command.equals(CommandTags.SUMMON.getLabel())) {
                Response.addMessage(summon(request));
            } else if (command.equals(CommandTags.SET.getLabel())) {
                Response.addMessage(set(request));
            } else if (command.equals(CommandTags.NEXT_PHASE.getLabel())) {
                Response.addMessage(nextPhase(request));
            } else if (command.equals(CommandTags.ATTACK.getLabel())) {
                Response.addMessage(attack(request));
            } else if (command.equals(CommandTags.DIRECT_ATTACK.getLabel())) {
                Response.addMessage(directAttack(request));
            }
        } else {
            Response.addMessage("its not your turn to do such an action :(");
        }
        if (command.equals(CommandTags.SHOW_SELECTED_CARD.getLabel())) {
            Response.addMessage(showSelectedCard(request));
        } else if (command.equals(CommandTags.SHOW_GRAVEYARD.getLabel())) {
            Response.addMessage(showGraveyard(request));
        } else if (command.equals(CommandTags.SHOW_CARD.getLabel())) {
            Response.addMessage(showCard(request));
        } else if (command.equals(CommandTags.SELECT.getLabel())) {
            Response.addMessage(select(request));
        } else if (command.equals(CommandTags.SELECT_FORCE.getLabel())) {
            Response.addMessage(selectForce(request));
        } else if (command.equals(CommandTags.WIN_GAME.getLabel())) {
            Response.addMessage(winGame(request));
        } else if (command.equals(CommandTags.DESELECT.getLabel())) {
            Response.addMessage(deselect(request));
        } else if (command.equals(CommandTags.ACTIVATE_EFFECT.getLabel())) {
            if (duel.getGame().getBoard().getMainPlayer().getTurnLogger().isTemporarilyChanged()) {
                Response.addMessage(activateEffectOnRivalsTurn(request));
            } else {
                Response.addMessage(activateEffect(request));
            }
        } else if (command.equals(CommandTags.INCREASE_LIFE_POINT.getLabel())) {
            Response.addMessage(increaseLifePoint(request));
        } else if (command.equals(CommandTags.CANCEL_ACTIVATION.getLabel())) {
            activationCanceled(request);
        }

        Response.addObject("game", duel.getGame().getGameObject());
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