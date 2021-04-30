package model.card;

import model.card.enums.CardType;
import model.card.enums.Property;
import model.card.enums.Status;

public class SpellTrap extends Card {
    private Property property;
    private Status status;

    public SpellTrap(String name, CardType cardType, Property property, String description, Status status, int price) {
        super(name, description, cardType, price);
        this.property = property;
        this.status = status;
        addCard(this);
    }

}
