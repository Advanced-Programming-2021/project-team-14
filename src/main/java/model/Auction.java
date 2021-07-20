package model;

import model.card.Card;

import java.util.ArrayList;

public class Auction {

    private static ArrayList<Auction> auctions = new ArrayList<>();
    private ArrayList<Bid> bids;
    private String cardName;


    public Auction(Card card) {
        this.bids = new ArrayList<>();
        this.cardName = card.getName();
        auctions.add(this);
    }

    public static ArrayList<Auction> getAuctions() {
        return auctions;
    }

    public void addBid(Bid bid) {
        this.bids.add(bid);
    }

    public Card getCard() {
        return Card.getCardByName(cardName);
    }

    public ArrayList<Bid> getBids() {
        return bids;
    }

    public Bid getBestBid() {
        if (bids.size() > 0) {
            Bid bestBid = bids.get(0);
            for (Bid bid : bids) {
                if (bid.getPrice() > bestBid.getPrice()) {
                    bestBid = bid;
                }
            }
            return bestBid;
        }
        return null;
    }
}
