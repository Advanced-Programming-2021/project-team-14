package model;

import Controller.Response;
import Controller.enums.DatabaseResponses;
import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import javafx.scene.image.Image;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.*;
import view.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static final String spellTrapDirectory = "Resources\\Cards\\SpellTrap.csv";
    private static final String monsterDirectory = "Resources\\Cards\\Monster.csv";
    private static final String resourcesDirectory = "Resources";
    private static final String usersDirectory = "Resources\\Users";
    private static final String SavedCardsDirectory = "Resources\\SavedCards";
    private static final String profileImagesDirectory = "Resources\\ProfileImages";

    private static void loadUsers() {

        // Get all files from Users directory
        File f = new File(usersDirectory);
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(".json"));

        if (matchingFiles == null)
            return;

        //Create User object foreach file
        for (File file : matchingFiles) {
            String data;
            try {
                data = new String(Files.readAllBytes(Paths.get(file.toString())));
                User.addUser(new Gson().fromJson(data, User.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static DatabaseResponses saveProfilePhoto(String path, String username) {
        File f = new File(path);
        File file = new File(profileImagesDirectory + "\\" + username + ".png");

        try {
            Files.copy(f.toPath(), file.toPath());
            return DatabaseResponses.SUCCESSFUL;
        } catch (IOException ex) {
            Response.error();
            return DatabaseResponses.NOT_EXIST_ERROR;
        }
    }

    public static Image getProfilePhoto(String username) {
        File file = new File(profileImagesDirectory + "\\" + username + ".png");
        if (file.exists()) {
            try {
                return new Image(String.valueOf(file.toURL()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DatabaseResponses removeProfilePhoto(String username) {
        File file = new File(profileImagesDirectory + "\\" + username + ".png");
        boolean hasDeleted = file.delete();
        if (hasDeleted)
            return DatabaseResponses.SUCCESSFUL;
        else
            return DatabaseResponses.NOT_EXIST_ERROR;

    }

    public static void readDataLineByLine(String file) {
        try {
            FileReader filereader = new FileReader(file);
            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader).build();
            List<String[]> allData = csvReader.readAll();
            String[] keys = allData.get(0);
            allData.remove(0);
            // add Cards :
            for (String[] row : allData) {
                HashMap<String, String> effects = new HashMap<>();

                if (file.contains("Monster")) {
                    setEffects(keys, effects, row, 9);
                    new Monster(row[0], Integer.parseInt(row[1]), Attribute.fromValue(row[2]),
                            MonsterType.fromValue(row[3]), Property.fromValue(row[4]),
                            Integer.parseInt(row[5]), Integer.parseInt(row[6]), row[7], Integer.parseInt(row[8]), effects);
                } else {
                    setEffects(keys, effects, row, 6);
                    new SpellTrap(row[0], CardType.fromValue(row[1]), Property.fromValue(row[2]),
                            row[3], Status.fromValue(row[4]), Integer.parseInt(row[5]), effects);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void prepareDatabase() {
        createDirectory();
        loadUsers();
        loadCards();
    }

    public static void loadCards() {
        Database.readDataLineByLine(spellTrapDirectory);
        Database.readDataLineByLine(monsterDirectory);
    }

    private static void createDirectory() {

        //Creating a File object
        File resourcesFile = new File(resourcesDirectory);
        File usersFile = new File(usersDirectory);
        File importedFile = new File(SavedCardsDirectory);
        File profileImagesFile = new File(profileImagesDirectory);

        //Creating the directory
        if (!resourcesFile.exists() && resourcesFile.mkdirs())
            Logger.log("database", "Resource folder created successfully!");
        if (!usersFile.exists() && usersFile.mkdirs())
            Logger.log("database", "User folder created successfully!");
        if (!importedFile.exists() && importedFile.mkdirs())
            Logger.log("database", "SavedCards folder created successfully!");
        if (!profileImagesFile.exists() && profileImagesFile.mkdirs())
            Logger.log("database", "profileImages folder created successfully!");
    }

    public static void saveUserInDatabase(User user) {
        String jsonString = new Gson().toJson(user);

        //create file address
        String userFileAddress = usersDirectory + "\\" + user.getUsername() + ".json";

        try (FileWriter file = new FileWriter(userFileAddress)) {
            //Write any JSONArray or JSONObject instance to the file
            file.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void setEffects(String[] keys, HashMap<String, String> effects, String[] row, int n) {
        for (int i = n; i < keys.length; i++) {
            effects.put(keys[i], row[i]);
        }
    }

    public static DatabaseResponses exportCard(String username, Card card) {
        createUserDirectoryInSavedCardsDirectory(username);
        String jsonString = new Gson().toJson(card);

        //create file address
        String cardFileAddress = SavedCardsDirectory + "\\" + username + "\\" + card.getName() + ".json";

        try (FileWriter file = new FileWriter(cardFileAddress)) {
            //Write any JSONArray or JSONObject instance to the file
            file.write(jsonString);
            return DatabaseResponses.SUCCESSFUL;
        } catch (IOException e) {
            e.printStackTrace();
            return DatabaseResponses.SAVE_ERROR;
        }
    }


    public static DatabaseResponses importCard(String username, String cardName) {

        // Get all files from Users directory
        File f = new File(SavedCardsDirectory + "\\" + username + "\\" + cardName + ".json");
        if (!f.exists()) {
            return DatabaseResponses.NOT_EXIST_ERROR;
        } else {
            File file = f.getAbsoluteFile();

            String data;
            try {
                data = new String(Files.readAllBytes(Paths.get(file.toString())));
                if (data.contains("Monster")) {
                    Card.addCard(new Gson().fromJson(data, Monster.class));
                } else {
                    Card.addCard(new Gson().fromJson(data, SpellTrap.class));
                }
            } catch (Exception e) {
                return DatabaseResponses.BAD_FORMAT_ERROR;
            }
            return DatabaseResponses.SUCCESSFUL;
        }
    }

    public static void deleteFile(String fileName) {

        String userFileAddress = usersDirectory + "\\" + fileName + ".json";

        File file = new File(userFileAddress);

        if (file.delete()) {
            Logger.log("database", "File deleted!");
        }
    }

    public static void createUserDirectoryInSavedCardsDirectory(String username) {
        File userFile = new File(SavedCardsDirectory + "\\" + username);

        //Creating the directory
        if (!userFile.exists() && userFile.mkdirs())
            Logger.log("database", "User folder created in SavedCards directory successfully!");

    }

}
