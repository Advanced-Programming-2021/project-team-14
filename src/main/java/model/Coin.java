package model;


public class Coin {

    public static int head = 0;
    public static int face;

    public static String flip() {
        face = (int) (Math.random() * 2);
        if (face == head) {
            return "head";
        }
        return "tail";
    }
}
