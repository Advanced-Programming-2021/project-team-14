package model.game;

import model.card.SelectedCard;
import org.json.JSONObject;

public class Chain {
    private final SelectedCard selectedCard;
    private final JSONObject request;

    public Chain(SelectedCard selectedCard, JSONObject request) {
        this.selectedCard = new SelectedCard(selectedCard.getCard(), selectedCard.isOpponent());
        this.request = new JSONObject(request, JSONObject.getNames(request));
    }



    public SelectedCard getSelectedCard() {
        return selectedCard;
    }

    public JSONObject getRequest() {
        return request;
    }

}
