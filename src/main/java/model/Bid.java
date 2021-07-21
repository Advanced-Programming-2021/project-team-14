package model;

public class Bid {

    private User user;
    private int price;


    public Bid(int price, User user) {

        this.price = price;
        this.user = user;
    }

    public int getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }
}
