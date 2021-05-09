package model.game;

public class Board {

    private String boardStructure =
            "%s:%s\n%s\n%s\n%s\n%s\n%s\t\t\t\t\t\t%s\n\n--------------------------\n\n%s\t\t\t\t\t\t%s\n%s\n%st\t\t\t\t\t%s\n%s\n%s:%s";

    private Player mainPlayer;

    private Player rivalPlayer;

    public Board(Player mainPlayer, Player rivalPlayer) {
        this.mainPlayer = mainPlayer;
        this.rivalPlayer = rivalPlayer;
    }

    @Override
    public String toString() {
        StringBuilder monsterZone = new StringBuilder(rivalPlayer.getMonster().toString());
        StringBuilder spellZone = new StringBuilder(rivalPlayer.getSpell().toString());

        return String.format(boardStructure, rivalPlayer.getNickname(), rivalPlayer.getLifePoint(),
                "\t" + rivalPlayer.getHand().toString(), rivalPlayer.getPlayingDeck().getRemainingCardsSize(),
                spellZone.reverse().toString(), monsterZone.reverse().toString(),
                rivalPlayer.getGraveYard().toString(), rivalPlayer.getFieldZone().toString(),
                mainPlayer.getFieldZone().toString(), mainPlayer.getGraveYard().toString(),
                mainPlayer.getMonster().toString(), mainPlayer.getSpell().toString(),
                mainPlayer.getPlayingDeck().getRemainingCardsSize(), mainPlayer.getHand().toString(),
                mainPlayer.getNickname(), mainPlayer.getLifePoint());
    }
}
