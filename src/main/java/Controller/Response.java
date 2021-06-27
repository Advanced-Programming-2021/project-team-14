package Controller;

import Controller.enums.Responses;
import org.json.JSONObject;

public class Response {
    private static JSONObject response = new JSONObject();
    private static String token;

    public static void addMessage(String message) {
        response.put("message", message);
    }

    public static JSONObject getResponse() {
        return response;
    }


    public static void success() {
        response.put("type", Responses.SUCCESS.getLabel());
    }

    public static void choice() {
        response.put("type", Responses.CHOICE.getLabel());
    }

    public static void add(String key, String value) {
        response.put(key, value);
    }

    public static void error() {
        response.put("type", Responses.ERROR.getLabel());
    }

    public static void addObject(String key, JSONObject jsonObject) {
        response.put(key, jsonObject);
    }

    public static void addToken(String generatedToken) {
        token = generatedToken;
        response.put("token", generatedToken);
    }
}
