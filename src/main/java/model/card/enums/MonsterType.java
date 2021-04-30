package model.card.enums;

public enum MonsterType {
    BEAST("Beast"),
    PYRO("Pyro"),
    FIEND("Fiend"),
    AQUA("Aqua"),
    ROCK("Rock"),
    INSECT("Insect"),
    MACHINE("Machine"),
    FAIRY("Fairy"),
    BEAST_WARRIOR("Beast-Warrior"),
    CYBERSE("Cyberse"),
    THUNDER("Thunder"),
    DRAGON("Dragon"),
    WARRIOR("Warrior"),
    SPELLCASTER("Spellcaster"),
    SEA_SERPENT("Sea Serpent");

    public final String label;

    MonsterType(String label) {
        this.label = label;
    }

    public static MonsterType fromValue(String givenName) {
        for (MonsterType direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
