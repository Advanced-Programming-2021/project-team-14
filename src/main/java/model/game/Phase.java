package model.game;

import graphic.component.Phases;

public enum Phase {
    END_PHASE(1),
    MAIN_PHASE_2(2),
    BATTLE_PHASE(3),
    MAIN_PHASE,
    MAIN_PHASE_1(4), //
    STANDBY_PHASE(5),
    DRAW_PHASE(6),
    START;

    private int index;
    Phase(int index){
        this.index = index;
    }

    Phase(){}

    public int getIndex() {
        return index;
    }
}
