package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class User {
    //    private Wallet wallet;
//    private Deck activeDeck;
    private static HashMap<String, User> users;
    private static ArrayList<String> nicknames;

    static {
        users = new HashMap<>();
        nicknames = new ArrayList<>();
    }

    private String username;
    private String password;
    private String nickname;
    private int score;
    private int rank;


//    private HashMap<String, Deck> decks;

//    public boolean doesDeckExist(String title){}
//
//    public void addDeck(String title){}
//
//    public void removeDeck(String title){}
//
//    public String showDecks(){}

//    public Deck getDeck(String title){}

    public User(String username, String password, String nickname) {
        Random random = new Random();
        this.score = random.nextInt(5);

        this.username = username;
        this.password = password;
        this.nickname = nickname;
        users.put(username, this);
        nicknames.add(nickname);
    }

//    public static void addUser(User user){}


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

    //    public void changePassword(String CurrentPassword, String newPassword){}
//
//    public void changeNickname(String newNickname){}
//
////    public void buyCard(Card card){}
//
//    public static User getUserByName(String username) {
//        return users.get(username);
//    }
}