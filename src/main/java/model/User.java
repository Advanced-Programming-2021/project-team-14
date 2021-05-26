package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private Wallet wallet;
    private String activeDeck;
    private static final HashMap<String, User> users;
    private static final ArrayList<String> nicknames;
    private HashMap<String, Deck> decks;

    static {
        users = new HashMap<>();
        nicknames = new ArrayList<>();
    }

    private String username;
    private String password;
    private String nickname;
    private int score;
    private int rank;

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.wallet = new Wallet();
        users.put(username, this);
        nicknames.add(nickname);
        this.decks = new HashMap<>();
        this.activeDeck = null;
        updateDatabase();
    }

    public boolean doesDeckExist(String deckName) {
        return decks.containsKey(deckName);
    }


    public User(User user, String username) {
        users.put(username, user);
    }


    public void removeDeck(String deckName) {
        decks.remove(deckName);
    }


    public static void addUser(User user) {
        new User(user, user.getUsername());
    }

    public HashMap<String, Deck> getDecks() {
        return decks;
    }

    public String getActiveDeck() {
        return activeDeck;
    }

    public void addDeck(String deckName) {
        decks.put(deckName, new Deck(deckName));
    }

    public void updateDatabase() {
        Database.saveUserInDatabase(this);
    }

    public void setActiveDeck(String deckName) {
        this.activeDeck = deckName;
    }

    public String getUsername() {
        return username;
    }

    public static boolean doesUsernameExist(String username) {
        return users.containsKey(username);
    }

    public static boolean doesNicknameExist(String nickname) {
        return nicknames.contains(nickname);
    }

    public static boolean isPasswordCorrect(String username, String password) {
        return users.get(username).password.equals(password);
    }

    public static ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public int getScore() {
        return this.score;
    }

    public static User getUserByUsername(String username) {
        return users.get(username);
    }

    public Wallet getWallet() {
        return wallet;
    }

    public String getNickname() {
        return nickname;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return String.format("%-3d | %-20s : %d", rank, nickname, score);
    }

    public static User getUserByNickname(String nickname) {

        for (User user : users.values()) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }


    public void changeNickname(String newNickname) {
        nicknames.remove(this.nickname);    // remove old nickname
        nicknames.add(newNickname);         // add new nickname
        this.nickname = newNickname;
    }

    public Deck getDeck(String deckName) {
        return decks.get(deckName);
    }

    public boolean doesHaveActiveDeck() {
        return activeDeck != null;
    }
}