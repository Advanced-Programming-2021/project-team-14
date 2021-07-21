package model;

import model.game.Duel;
import model.game.Game;

import java.util.ArrayList;

public class TV {
    private static ArrayList<Duel> onlineGames = new ArrayList<>();
  private static ArrayList<Duel> offlineGames = new ArrayList<>();

    public static void addOnlineGame(Duel duel) {
        onlineGames.add(duel);
    }
    public static void addOfflineGame(Duel duel) {
        offlineGames.add(duel);
    }

    public static void removeOnlineGame(Duel duel) {
        onlineGames.add(duel);
    }

    public static ArrayList<Duel> getOfflineGames() {
        return offlineGames;
    }

    public static ArrayList<Duel> getOnlineGames() {
        return onlineGames;
    }

    public static Game showDuel(String mainUser, String rivalUser) {
        for (Duel duel : onlineGames) {
            if (duel.getMainUser().getUsername().equals(mainUser) &&
                    duel.getRivalUser().getUsername().equals(rivalUser)) {
               return duel.getGame();
            }
        }
        return null;
    }


}
