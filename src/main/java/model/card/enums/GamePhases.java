package model.card.enums;

public enum GamePhases {

    DRAW_PHASE("draw-phase"),
    STANDBY_PHASE("standby-phase"),
    MAIN_PHASE1("main-phase1"),
    BATTLE_PHASE("battle-phase"),
    MAIN_PHASE2("main-phase2"),
    END_PHASE("end-phase");

    public final String label;

    GamePhases(String label) {
        this.label = label;
    }


    public static GamePhases fromValue(String givenName) {
        for (GamePhases direction : values())
            if (direction.label.equals(givenName))
                return direction;
        return null;
    }

    public String getLabel() {
        return label;
    }
}
