package view;

import Controller.MainController;
import model.Strings;
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

    public static void setOption(String command, String option) { // extract option if available
        addBooleanData(option, command.contains("--" + option));
    }

    public static String getMessage() {
        return response.getString("message");
    }

    public static void extractData(String command) { // extract data from the input with the "--key value" format
        Pattern pattern = Pattern.compile(Regexes.DATA.getLabel());
        Matcher matcher = pattern.matcher(command);
        while (matcher.find())
            request.put(matcher.group(1), matcher.group(2).trim());
    }

    public static void send() { // sending the request to the main controller
        Request.setToken();
        Logger.log("client", request.toString());
        response = new JSONObject(MainController.processCommand(request.toString()));
        clear();
    }

    public static JSONObject getResponse() {
        return response;
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

    public static void addDataToRequest(String regex, String command, String key) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            Request.addData(key, matcher.group(1));
        }
    }

    public static void addShortData(String command) {
        if (command.contains("--sp "))
            Request.addData("area", "spell");
        if (command.contains("--mn "))
            Request.addData("area", "monster");
        if (command.contains("--hd "))
            Request.addData("area", "hand");
        if (command.contains("--pos ") && command.contains("att"))
            Request.addData("position", "attack");
        if (command.contains("--pos ") && command.contains("def"))
            Request.addData("position", "defense");

        if (command.contains("--un "))
            extractShortData("--un", command, "username");
        if (command.contains("--nn "))
            extractShortData("--nn", command, "nickname");
        if (command.contains("--pw "))
            extractShortData("--pw", command, "password");
        if (command.contains("--cur "))
            extractShortData("--cur", command, "current");
        if (command.contains("--new "))
            extractShortData("--new", command, "new");
        if (command.contains("--dn "))
            extractShortData("--dn", command, "deck-name");
        if (command.contains("--sec-p "))
            extractShortData("--sec-p", command, "second-player");
        if (command.contains("--rou "))
            extractShortData("--rou", command, "rounds");
    }

    private static void extractShortData(String regex, String command, String key) {
        Pattern pattern = Pattern.compile(Regexes.EXTRACT_SHORT_DATA.getLabel().replace("--", regex));
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            Request.addData(key, matcher.group(1).trim());
        }
    }

    public static boolean isChoice() {
        return response.getString("type").equals(Responses.CHOICE.getLabel());
    }

    public static void addBooleanData(String option, boolean bool) {
        request.put(option, bool);
    }
}
