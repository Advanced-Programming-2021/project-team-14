package view;

public class Token {

    private static String currentUser;

    public Token(String username) {

        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}
