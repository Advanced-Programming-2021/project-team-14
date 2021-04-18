package view;

import Controller.MainController;
import org.json.JSONObject;
import view.enums.CommandTags;
import view.enums.Regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Request {
    private static final JSONObject request = new JSONObject();

    public static void addData(String key, String value) {
        request.put(key, value);
    }

    public static void setCommandTag(CommandTags commandTag) {
        request.put("command", commandTag.getLabel());
    }

    public static void extractData(String command){
        Pattern pattern = Pattern.compile(Regexes.DATA.getLabel());
        Matcher matcher = pattern.matcher(command);
        while(matcher.find()){
            request.put(matcher.group(1), matcher.group(2));
        }
    }

    public static String send() {
        String response = MainController.processCommand(request.toString());
        String viewTag = request.get("view").toString();
        clear();
        Request.addData("view", viewTag);
        return response;
    }

    public static void clear() {
        while(request.length() > 0)
            request.remove(request.keys().next());
    }
}
