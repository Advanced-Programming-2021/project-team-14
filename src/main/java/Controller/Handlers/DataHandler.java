package Controller.Handlers;

import Controller.Response;
import Controller.enums.EffectsEnum;
import model.Strings;
import model.card.Card;
import model.card.SelectedCard;
import model.card.enums.CardType;
import model.card.enums.Property;
import model.card.enums.State;
import model.game.Cell;
import model.game.Duel;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.prefs.PreferenceChangeEvent;

public class DataHandler extends GameHandler {
    public String handle(JSONObject request, Duel duel) {
        SelectedCard selectedCard = duel.getGame().getSelectedCard();
        String command = request.getString(Strings.COMMAND.getLabel());
        String effectTime = selectedCard.getCard().getEffectValue(EffectsEnum.EFFECT_TIME.getLabel());
            if (!effectTime.matches("entered name exist|" + Strings.NONE.getLabel())){
                return "preparation is not ready :|";
            }


        if ("Mirror Force".equals(selectedCard.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel()))) {
            if (duel.getGame().getBoard().getRivalPlayer().getMonsterZone().getCellsByState(State.OFFENSIVE_OCCUPIED) == 0) {
                return "preparation is not ready, there is no monster in attacking position to destroy";
            }
        }




        if (selectedCard.getCard().getProperty() == Property.EQUIP) {
            ArrayList<Integer> suitableCards = duel.getGame().getBoard().getMainPlayer().getMonsterZone().getCellsByType(selectedCard.getCard().getEffectValue(EffectsEnum.FIRST_TARGET.getLabel()));
            if (suitableCards.size() > 0) {
                if (!request.has("data")) {
                    Response.choice();
                    return "So please select a card to equip: " + suitableCards.toString();
                }
            } else {
                return "the preparation is not ready yet, there is no suitable card in monster zone to equip.";
            }
        }
        if (selectedCard.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel()).equals("change owner")) {
            ArrayList<Integer> suitableCards = new ArrayList<>();
            for (Cell cell : duel.getGame().getBoard().getRivalPlayer().getMonsterZone().getCells()) {
                if (!cell.isEmpty()) {
                    suitableCards.add(cell.getPosition());
                }
            }
            if (suitableCards.size() > 0) {
                if (!request.has("data")) {
                    Response.choice();
                    return "please choose a monster card to get its ownership: " + suitableCards.toString();
                } else if (duel.getGame().getBoard().getMainPlayer().getMonsterZone().isFull()) {
                    return "the preparation is not ready yet, your monsterZone is full";
                }
            } else {
                return "the preparation is not ready yet, there is no monster to get its control";
            }
        }
        if (selectedCard.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel()).equals("destroy card")) {
            ArrayList<Integer> suitableCards = new ArrayList<>();
            for (Cell cell : duel.getGame().getBoard().getRivalPlayer().getSpellZone().getCells()) {
                if (!cell.isEmpty()) {
                    suitableCards.add(cell.getPosition());
                }
            }
            if (suitableCards.size() > 0) {
                if (!request.has("data")) {
                    Response.choice();
                    return "please choose a spell card to destroy: " + suitableCards.toString();
                }
            } else {
                return "the preparation is not ready yet, there is no spell to destroy";
            }
        }

        if (selectedCard.getCard().getName().equals("Magic Jammer")){
            if (!request.has("data")){
                return "please enter the number of card you want to discard, from 1 to " + duel.getGame().getBoard().getMainPlayer().getHand().getSize();
            }
        }

//        if ("add".matches(selectedCard.getCard().getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel()))) {
//            String from = selectedCard.getCard().getEffectValue(EffectsEnum.FROM.getLabel());
//            if (from.equals("graveyard")) {
//                if (duel.getGame().getBoard().getMainPlayer().getGraveYard().isEmpty(CardType.MONSTER)) {
//                    return "the preparation is not ready yet, there is no monster in your graveyard to special summon!";
//                } else if (!request.has("data")) {
//                    Response.choice();
//                    return "main:\n" + duel.getGame().getBoard().getMainPlayer().getGraveYard().getCards(CardType.MONSTER).toString();
//                }
//            } else if (from.equals("graveyard|both")) {
//                if (duel.getGame().getBoard().getMainPlayer().getGraveYard().isEmpty(CardType.MONSTER) && duel.getGame().getBoard().getRivalPlayer().getGraveYard().isEmpty(CardType.MONSTER)) {
//                    return "the preparation is not ready yet, there is no monster in either graveyards to special summon!";
//                } else if (!request.has("data")) {
//                    Response.choice();
//                    return "main:\n" + duel.getGame().getBoard().getMainPlayer().getGraveYard().getCards(CardType.MONSTER).toString() +
//                           "rival:\n" + duel.getGame().getBoard().getRivalPlayer().getGraveYard().getCards(CardType.MONSTER).toString();
//                }
//            }
//        }
        if ("entered name exist".equals(selectedCard.getCard().getEffectValue(EffectsEnum.EFFECT_TIME.getLabel()))) {
            if (!request.has("data")) {
                Response.choice();
                return "please enter a card name to apply the effect";
            }
            //            case "discard":
//
//                if (duel.getGame().getBoard().getMainPlayer().getHand().getSize() > 1){
//                    if (!request.has("data")) {
//                        Response.choice();
//                        return "please enter the number of card you want to discard, from 1 to " + duel.getGame().getBoard().getMainPlayer().getHand().getSize();
//                    }
//                }
        }
        Card card = selectedCard.getCard();
        switch (card.getEffectValue(EffectsEnum.CHANGE_DESTROY_ADD_CARD.getLabel())) {
            case "destroy":
                switch (card.getEffectValue(EffectsEnum.FROM.getLabel())) {
                    case "both":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                if (duel.getGame().getBoard().getMainPlayer().getMonsterZone().getNumberOfFullCells() == 0 &&
                                    duel.getGame().getBoard().getRivalPlayer().getMonsterZone().getNumberOfFullCells() == 0) {
                                    return "preparations of this card is not provided, there is no monster to destroy.";
                                }
                                break;
                            case "spells/traps":
                                if (duel.getGame().getBoard().getMainPlayer().getSpellZone().getNumberOfFullCells() == 0 &&
                                    duel.getGame().getBoard().getRivalPlayer().getSpellZone().getNumberOfFullCells() == 0) {
                                    return "preparations of this card is not provided, there is no spell/trap to destroy.";
                                }
                                break;
                        }
                        break;
                    case "rival board":
                        switch (card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel())) {
                            case "monsters":
                                if (duel.getGame().getBoard().getRivalPlayer().getMonsterZone().getNumberOfFullCells() == 0) {
                                    return "preparations of this card is not provided, there is no monster in opponents zone to destroy.";
                                }
                                break;
                            case "spells/traps":
                                if (duel.getGame().getBoard().getRivalPlayer().getSpellZone().getNumberOfFullCells() == 0) {
                                    return "preparations of this card is not provided, there is no spell/trap in opponents zone to destroy.";
                                }
                                break;
                        }
                }
                break;
            case "draw":
                int numberOfCards = Integer.parseInt(card.getEffectValue(EffectsEnum.NUMBER_OF_AFFECTED_CARDS.getLabel()));
                if (duel.getGame().getBoard().getMainPlayer().getHand().getRemainedPlaces() < numberOfCards) {
                    return "preparation in not ready, you dont have enough place in your hand to draw " + numberOfCards + " card";
                }
                break;
            case "add": // add from deck to hand
                if (duel.getGame().getBoard().getMainPlayer().getPlayingDeck().
                        getACard(Property.fromValue(card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel()))) == null) {
                    return "preparation in not ready, there is no " + card.getEffectValue(EffectsEnum.FIRST_TARGET.getLabel()) + " card to add";
                }
                break;
        }
        Response.success();
        return super.handle(request, duel);
    }

}
