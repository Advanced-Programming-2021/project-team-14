package model.game;

import model.Deck;
import model.User;
import model.card.Card;

public class Player {

    private String username;

    private String nickname;

    private PlayingDeck playingDeck;

    private Hand hand;

    private Card selectedCard;

    private Zone monster;

    private Zone spell;

    private GraveYard graveYard;

    private FieldZone fieldZone;

    private int lifePoint;


    public Player(User user, Deck playingDeck) {

        this.username = user.getUsername();
        this.nickname = user.getNickname();


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

    public Zone getMonster() {
        return monster;
    }

    public void setMonster(Zone monster) {
        this.monster = monster;
    }

    public Zone getSpell() {
        return spell;
    }

    public void setSpell(Zone spell) {
        this.spell = spell;
    }
}
