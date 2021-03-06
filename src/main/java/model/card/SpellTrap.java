package model.card;

import model.card.enums.CardType;
import model.card.enums.Property;
import model.card.enums.Status;

import java.util.ArrayList;
import java.util.HashMap;

public class SpellTrap extends Card {

    private Status status;
    private static ArrayList<SpellTrap> spellTraps;
    private boolean isActivated;

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    static {
        spellTraps = new ArrayList<>();
    }

    public SpellTrap(String name, CardType cardType, Property property, String description,
                     Status status, int price, HashMap<String, String> effects) {
        super(name, description, cardType, price);
        this.property = property;
        this.status = status;
        this.effects = effects;
        addCard(this);
        spellTraps.add(this);

    }

    public static ArrayList<SpellTrap> getSpellTraps() {
        return spellTraps;
    }

    public Status getStatus() {
        return status;
    }
    
    public SpellTrap(SpellTrap card) {
        super(card);
        this.status = card.getStatus();
        this.property = card.getProperty();
        this.effects = card.getEffects();
    }

    @Override
    public String show() {

        return showCard.getHorizontalLine() + showCard.getTypeLine(getCardType().getLabel()) +
                showCard.getFreeLine() + showCard.getNameLine(getName()) +
                showCard.getPriceLine(getPrice()) +
                showCard.getFreeLine() + showCard.getDescriptionLine(getDescription()) +
                showCard.getHorizontalLine();
    }

    @Override
    public String getDescriptionGraphic() {
        return showCard.getDescriptionGraphic(getDescription());
    }
}
