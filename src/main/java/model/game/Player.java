package model.game;

import model.Strings;
import model.User;
import model.card.Card;

import java.util.ArrayList;

public class Player {

    private String username;

    private String nickname;

    private PlayingDeck playingDeck;

    private Hand hand;

    private Zone monsterZone;
    private ArrayList<Card> activatedCards;

    public ArrayList<Card> getActivatedCards() {
        return activatedCards;
    }

    private Zone spellZone;

    private GraveYard graveYard;

    private FieldZone fieldZone;

    private int winningRounds;

    private int lifePoint;

    public void addToActiveCards(Card card) {
        activatedCards.add(card);
    }

    public Player(User user) {
        this.winningRounds = 0;
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
//        hand.addCard(Card.getCardByName("Closed Forest"));
//        hand.addCard(Card.getCardByName("Silver Fang"));
//        hand.addCard(Card.getCardByName("Forest"));
        this.activatedCards = new ArrayList<>();
    }

    private void addNCardsToHand(int number) {
        for (int j = 0; j < number; j++) {
            hand.addCard(playingDeck.drawCard());
        }
    }


    public PlayingDeck getPlayingDeck() {
        return playingDeck;
    }


    public FieldZone getFieldZone() {
        return fieldZone;
    }

    public GraveYard getGraveYard() {
        return graveYard;
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

    public void increaseLifePoint(int lifePoint) {
        this.lifePoint += lifePoint;
    }

    public void increaseWinningRounds(int amount) {
        this.winningRounds += amount;
    }

    public void decreaseLifePoint(int lifePoint) {
        this.lifePoint -= lifePoint;
    }

    public int getWinningRounds() {
        return winningRounds;
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

    public Zone getSpellZone() {
        return spellZone;
    }

    public Card drawCard() {
        Card card = playingDeck.drawCard();
        if (card != null) {
            hand.addCard(card);
        }
        return card;
    }

    @Override
    public String toString() {
        return String.format(Strings.PLAYER_FORMAT.getLabel(), nickname, lifePoint);
    }

}
