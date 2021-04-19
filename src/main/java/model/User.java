package model;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    //    private Wallet wallet;
//    private Deck activeDeck;
    private static HashMap<String, User> users;
    private static ArrayList<String> nicknames;
    private String username;
    private String password;
    private String nickname;
    private int score;


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

        this.username = username;
        this.password = password;
        this.nickname = nickname;
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