package view;

import Controller.MainController;
import org.json.JSONObject;
import view.enums.CommandTags;
import view.enums.Regexes;
import view.enums.Responses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static JSONObject request = new JSONObject();
    private static JSONObject response;
    private static String token = null;

    public static void getToken() {
        token = response.getString("token");
    }

    public static void addData(String key, String value) { // adding data with key and value
        request.put(key, value);
    }

    public static void setCommandTag(CommandTags commandTag) { // set the request main command
        request.put("command", commandTag.getLabel());
    }

    public static void extractData(String command) { // extract data from the input with the "--key value" format
        Pattern pattern = Pattern.compile(Regexes.DATA.getLabel());
        Matcher matcher = pattern.matcher(command);
        while (matcher.find())
            request.put(matcher.group(1), matcher.group(2));
    }

    public static void send() { // sending the request to the main controller
        Request.setToken();
        response = new JSONObject(MainController.processCommand(request.toString()));
        clear();
    }

    public static String getResponse() {
        return response.toString();
    }

    public static void clear() {
        request = new JSONObject();
    }

    private static void setToken() {
        request.put("token", token);
    }

    public static boolean isSuccessful() { // check whether the command was successful or not
        return response.getString("type").equals(Responses.SUCCESS.getLabel());
    }
}
