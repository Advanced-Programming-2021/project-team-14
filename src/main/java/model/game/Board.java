package model.game;

import model.Strings;

public class Board {


    private Player mainPlayer;

    private Player rivalPlayer;


    public Board(Player mainPlayer, Player rivalPlayer) {
        this.mainPlayer = mainPlayer;
        this.rivalPlayer = rivalPlayer;
    }


    public Player getMainPlayer() {
        return mainPlayer;
    }

    public void setMainPlayer(Player mainPlayer) {
        this.mainPlayer = mainPlayer;
    }

    public void setRivalPlayer(Player rivalPlayer) {
        this.rivalPlayer = rivalPlayer;
    }

    public Player getRivalPlayer() {
        return rivalPlayer;
    }

    @Override
    public String toString() {

        return String.format(Strings.BOARD_STRUCTURE.getLabel(), rivalPlayer,
                rivalPlayer.getHand(), rivalPlayer.getPlayingDeck(),
                rivalPlayer.getSpellZone().toString(true), rivalPlayer.getMonsterZone().toString(true),
                rivalPlayer.getGraveYard(), rivalPlayer.getFieldZone(),
                mainPlayer.getFieldZone(), mainPlayer.getGraveYard(),
                mainPlayer.getMonsterZone().toString(false), mainPlayer.getSpellZone().toString(false),
                mainPlayer.getPlayingDeck(), mainPlayer.getHand(),
                mainPlayer);
    }
}
