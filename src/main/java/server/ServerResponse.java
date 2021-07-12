package server;

import Controller.enums.Responses;
import org.json.JSONObject;

public class ServerResponse {
    private  JSONObject response = new JSONObject();
    private  String token;

    public  void addMessage(String message) {
        response.put("message", message);
    }

    public  JSONObject getResponse() {
        return response;
    }


    public  void success() {
        response.put("type", Responses.SUCCESS.getLabel());
    }

    public  void choice() {
        response.put("type", Responses.CHOICE.getLabel());
    }

    public  void add(String key, String value) {
        response.put(key, value);
    }

    public  void error() {
        response.put("type", Responses.ERROR.getLabel());
    }

    public  void addObject(String key, JSONObject jsonObject) {
        response.put(key, jsonObject);
    }

    public  void addToken(String generatedToken) {
        token = generatedToken;
        response.put("token", generatedToken);
    }
}
