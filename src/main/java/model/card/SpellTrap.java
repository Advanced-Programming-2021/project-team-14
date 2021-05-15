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

    public Status getStatus() {
        return status;
    }

    public Property getProperty() {
        return property;
    }

    public SpellTrap(SpellTrap card) {
        super(card);
        this.status = card.getStatus();
        this.property = card.getProperty();
    }

    @Override
    public String show() {
        return showCard.getHorizontalLine() + showCard.getTypeLine(getCardType().getLabel()) +
                showCard.getFreeLine() + showCard.getNameLine(getName()) +
                showCard.getPriceLine(getPrice()) +
                showCard.getFreeLine() +
                showCard.getDescriptionLine(getDescription()) +
                showCard.getHorizontalLine();
    }
}
