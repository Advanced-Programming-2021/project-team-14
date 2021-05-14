package view;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public abstract class Console {
    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static String scan(){
        return scanner.nextLine();
    }
    public static void print(String output) {
        try {
            JSONObject result = new JSONObject(output);
            System.out.println(result.get("message").toString());
        } catch (JSONException e) {
            System.out.println(output);
        }
    }

    public static void printBoard(JSONObject jsonObject) {
        JSONObject game = jsonObject.getJSONObject("game");
        System.out.println("\033[1;36m" + game.getString("board") + "\033[0m");
        System.out.println("phase: " + game.getString("phase"));
    }
}
