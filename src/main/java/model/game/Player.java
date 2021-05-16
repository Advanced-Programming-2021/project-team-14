package model.game;

import model.Strings;
import model.User;
import model.card.Card;
import org.w3c.dom.CDATASection;

public class Player {

    private String username;

    private String nickname;

    private PlayingDeck playingDeck;

    private Hand hand;

    private Card selectedCard;

    private Zone monsterZone;

    private Zone spellZone;

    private GraveYard graveYard;

    private FieldZone fieldZone;

    private int lifePoint;


    public Player(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        playingDeck = new PlayingDeck(user.getDeck(user.getActiveDeck()));
        fieldZone = new FieldZone();
        graveYard = new GraveYard();
        monsterZone = new Zone();
        spellZone = new Zone();
        lifePoint = 8000;
        hand = new Hand();
        addNCardsToHand(6);
    }

    private void addNCardsToHand(int number) {
        for (int j = 0; j < number; j++) {
            hand.addCard(playingDeck.drawCard());
        }
    }


    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public PlayingDeck getPlayingDeck() {
        return playingDeck;
    }

    public void setPlayingDeck(PlayingDeck playingDeck) {
        this.playingDeck = playingDeck;
    }

    public FieldZone getFieldZone() {
        return fieldZone;
    }

    public void setFieldZone(FieldZone fieldZone) {
        this.fieldZone = fieldZone;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(GraveYard graveYard) {
        this.graveYard = graveYard;
    }

    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public int getLifePoint() {
        return lifePoint;
    }

    public void setLifePoint(int lifePoint) {
        this.lifePoint = lifePoint;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Zone getMonsterZone() {
        return monsterZone;
    }

    public void setMonsterZone(Zone monsterZone) {
        this.monsterZone = monsterZone;
    }

    public Zone getSpellZone() {
        return spellZone;
    }

    public void setSpellZone(Zone spellZone) {
        this.spellZone = spellZone;
    }

    @Override
    public String toString() {
        return String.format(Strings.PLAYER_FORMAT.getLabel(), nickname, lifePoint);
    }

    public void decreaseLP(int damage) {
        this.lifePoint -= damage;
    }
}
