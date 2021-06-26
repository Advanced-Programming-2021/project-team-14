package model.game;

import model.Strings;
import model.User;
import model.card.Card;
import model.card.enums.Position;

import java.util.ArrayList;

public class Player {

    private String username;

    private String nickname;

    private PlayingDeck playingDeck;
    private TurnLogger turnLogger;

    public TurnLogger getTurnLogger() {
        return turnLogger;
    }
    private Hand hand;

    private Zone monsterZone;
    private ArrayList<Card> activatedCards;



    private Zone spellZone;

    private GraveYard graveYard;

    private FieldZone fieldZone;

    private int winningRounds;

    private int lifePoint;


    public Player(User user, Integer winningRounds) {
        this.winningRounds = winningRounds;
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        playingDeck = new PlayingDeck(user.getDeck(user.getActiveDeck()));
        fieldZone = new FieldZone();
        graveYard = new GraveYard();
        monsterZone = new Zone();
        spellZone = new Zone();
        this.turnLogger = new TurnLogger();
        lifePoint = 8000;
        hand = new Hand();
//        hand.addCard(Card.getCardByName("Battle OX"));
//        hand.addCard(Card.getCardByName("Leotron"));
//        hand.addCard(Card.getCardByName("Skull Guardian"));
        addNCardsToHand(6);
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
        card.setPosition(Position.HAND);
        hand.addCard(card);
        return card;
    }

    @Override
    public String toString() {
        return String.format(Strings.PLAYER_FORMAT.getLabel(), nickname, lifePoint);
    }

}
