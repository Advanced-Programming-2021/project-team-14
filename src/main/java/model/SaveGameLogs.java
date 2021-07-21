package model;

import com.google.gson.JsonObject;
import model.game.Duel;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class SaveGameLogs {

    private static ArrayList<SaveGameLogs> allGames = new ArrayList<>();

    private Duel duel;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private ArrayList<JSONObject> logs;

    public SaveGameLogs(Duel duel){
        this.duel = duel;
        this.startTime = LocalDateTime.now();
        this.logs = new ArrayList<>();
    }

    public void addLog(JSONObject log){
        logs.add(log);
    }

    public void finishGame(){
        endTime = LocalDateTime.now();
        TV.removeOnlineGame(this.getDuel());
    }

    public static void addGame(SaveGameLogs game) {
        allGames.add(game);
        TV.addOfflineGame(game.getDuel());
    }

    public Duel getDuel() {
        return duel;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

}
