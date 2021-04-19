package Controller;

import Controller.enums.Responses;
import org.json.JSONObject;

public class Response {
    private static JSONObject response = new JSONObject();

    public static void addMessage(String message) {
        response.put("message", message);
    }

    public static JSONObject getResponse() {
        return response;
    }


    public static void success() {
        response.put("type", Responses.SUCCESS.getLabel());
    }

    public static void error() {
        response.put("type", Responses.ERROR.getLabel());
    }
}
