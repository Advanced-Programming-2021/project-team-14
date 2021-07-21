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
import model.game.Duel;
import model.game.Game;
import org.apache.commons.io.FileExistsException;
import org.json.JSONObject;
import view.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static com.sun.org.apache.xml.internal.security.utils.XMLUtils.encodeToString;

public class Database {
    private static final String spellTrapDirectory = "Resources\\Cards\\SpellTrap.csv";
    private static final String monsterDirectory = "Resources\\Cards\\Monster.csv";
    private static final String resourcesDirectory = "Resources";
    private static final String usersDirectory = "Resources\\Users";
    private static final String savedCardsDirectory = "Resources\\SavedCards";
    private static final String profileImagesDirectory = "Resources\\ProfileImages";
    private static final String cacheDirectory = "Resources\\Cache";
    private static final String savedGames = "Resources\\SavedGames";


    private static ArrayList<String> draggedCardNames = new ArrayList<>();

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


    public static ArrayList<String> loadCanExportedCards(String username) {

        // Get all files from Users directory
        File f = new File(savedCardsDirectory + "\\" + username);
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(".json"));

        if (matchingFiles == null)
            return null;

        ArrayList<String> cardNames = new ArrayList<>();
        //Create User object foreach file
        for (File file : matchingFiles) {
            String data;
            try {
                data = new String(Files.readAllBytes(Paths.get(file.toString())));

                if (data.contains("MONSTER")) {
                    Card.addCard(new Gson().fromJson(data, Monster.class));
                } else {
                    Card.addCard(new Gson().fromJson(data, SpellTrap.class));
                }

                cardNames.add(file.getName().replace(".json", ""));


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cardNames;
    }

    public static byte[] bufferedImageToByteArray(BufferedImage image, String format) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, format, baos);
        return baos.toByteArray();
    }

    public static String convertImageToString(BufferedImage image) {
        try {
            byte[] imageByteArray = bufferedImageToByteArray(image, "png");
            return encodeToString(imageByteArray);
        } catch (Exception e) {
            return "";
        }
    }

    public static Image convertStringToImage(String imageString) {
        try {
            byte[] imageByteArray = Base64.getDecoder().decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByteArray);
            BufferedImage bufferedImage = ImageIO.read(bis);
            return new Image(String.valueOf(bufferedImage));
        } catch (Exception e) {
            return null;
        }
    }

    public static DatabaseResponses saveProfilePhoto(String path, String username) {
        File f = new File(path);
        File file = new File(profileImagesDirectory + "\\" + username + ".png");

        if (file.exists())
            file.delete();
        try {
            Files.copy(f.toPath(), file.toPath());
            return DatabaseResponses.SUCCESSFUL;
        } catch (IOException ex) {
            Response.error();
            return DatabaseResponses.NOT_EXIST_ERROR;
        }
    }

    public static void saveProfileCircle(BufferedImage rawImage, String username) {
        File file = new File(profileImagesDirectory + "\\" + username + "_circle" + ".png");
        try {
            ImageIO.write(rawImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image getProfilePhoto(String username) throws Exception {
        File file = new File(profileImagesDirectory + "\\" + username + ".png");
        if (file.exists()) {
            try {
                return new Image(String.valueOf(file.toURL()));
            } catch (Exception e) {
                throw new FileNotFoundException();
            }
        }
        return null;
    }


    public static BufferedImage getBufferedImage(BufferedImage rawImage, String path) {
        try {
            File f = new File(path);
            rawImage = ImageIO.read(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rawImage;
    }

    public static Image getCardImage(String path) {
        File file = new File(path);
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
        File importedFile = new File(savedCardsDirectory);
        File profileImagesFile = new File(profileImagesDirectory);
        File cacheFile = new File(cacheDirectory);
        File savedGamesFile = new File(savedGames);

        //Creating the directory
        if (!resourcesFile.exists() && resourcesFile.mkdirs())
            Logger.log("database", "Resource folder created successfully!");
        if (!usersFile.exists() && usersFile.mkdirs())
            Logger.log("database", "User folder created successfully!");
        if (!importedFile.exists() && importedFile.mkdirs())
            Logger.log("database", "SavedCards folder created successfully!");
        if (!profileImagesFile.exists() && profileImagesFile.mkdirs())
            Logger.log("database", "ProfileImages folder created successfully!");
        if (!cacheFile.exists() && cacheFile.mkdirs())
            Logger.log("database", "Cache folder created successfully!");
        if (!savedGamesFile.exists() && savedGamesFile.mkdirs())
            Logger.log("database", "SavedGames folder created successfully!");
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

    public static void saveGameInDatabase(SaveGameLogs recorder) {
        String jsonString = new Gson().toJson(recorder);
        Duel duel = recorder.getDuel();
        //create file address
        String userFileAddress = savedGames + "\\" + duel.getMainUser().getUsername() + "_" +
                duel.getRivalUser().getUsername() + "_" + recorder.getStartTime().toString() + ".json";

        try (FileWriter file = new FileWriter(userFileAddress)) {
            //Write any JSONArray or JSONObject instance to the file
            file.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGames(){

        // Get all files from Users directory
        File f = new File(savedGames);
        File[] matchingFiles = f.listFiles((dir, name) -> name.endsWith(".json"));

        if (matchingFiles == null)
            return;

        //Create User object foreach file
        for (File file : matchingFiles) {
            String data;
            try {
                data = new String(Files.readAllBytes(Paths.get(file.toString())));
                SaveGameLogs.addGame(new Gson().fromJson(data, SaveGameLogs.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        String cardFileAddress = savedCardsDirectory + "\\" + username + "\\" + card.getName() + ".json";

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
        File f = new File(savedCardsDirectory + "\\" + username + "\\" + cardName + ".json");
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

    public static ArrayList<String> getDraggedCardNames() {
        return draggedCardNames;
    }

    public static DatabaseResponses openDraggedCardFile(String path) {
        File f = new File(path);
        draggedCardNames = new ArrayList<>();


        if (!f.exists()) {
            return DatabaseResponses.NOT_EXIST_ERROR;
        } else if (path.endsWith(".csv")) {
            try {
                FileReader filereader = new FileReader(f);
                // create csvReader object and skip first Line
                CSVReader csvReader = new CSVReaderBuilder(filereader).build();
                List<String[]> allData = csvReader.readAll();
                String[] keys = allData.get(0);
                allData.remove(0);
                // add Cards :

                for (String[] row : allData) {
                    if (!Card.doesCardExist(row[0])) {
                        HashMap<String, String> effects = new HashMap<>();
                        draggedCardNames.add(row[0]);
                        if (keys[3].trim().equals("Monster Type")) {
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
                }
                if (draggedCardNames.size() == 0)
                    return DatabaseResponses.CARD_ALREADY_EXIST;
                else
                    return DatabaseResponses.SUCCESSFUL;
            } catch (Exception e) {
                return DatabaseResponses.BAD_FORMAT_ERROR;
            }

        } else if (path.endsWith(".json")) {

            File file = f.getAbsoluteFile();

            String data;
            try {
                data = new String(Files.readAllBytes(Paths.get(file.toString())));
                JSONObject jsonObject = new JSONObject(data);
                String name = jsonObject.getString("name");
                if (!Card.doesCardExist(name)) {
                    draggedCardNames.add(name);
                    if (data.contains("monsterType")) {
                        Card.addCard(new Gson().fromJson(data, Monster.class));
                    } else {
                        Card.addCard(new Gson().fromJson(data, SpellTrap.class));
                    }
                    return DatabaseResponses.SUCCESSFUL;
                } else {
                    return DatabaseResponses.CARD_ALREADY_EXIST;
                }
            } catch (Exception e) {
                return DatabaseResponses.BAD_FORMAT_ERROR;
            }


        } else {
            return DatabaseResponses.BAD_FORMAT_RESPONSE;
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
        File userFile = new File(savedCardsDirectory + "\\" + username);

        //Creating the directory
        if (!userFile.exists() && userFile.mkdirs())
            Logger.log("database", "User folder created in SavedCards directory successfully!");

    }

    public static void createUserDirectoryInCacheDirectory(String username) {
        File userFile = new File(cacheDirectory + "\\" + username);

        //Creating the directory
        if (!userFile.exists() && userFile.mkdirs())
            Logger.log("database", "User folder created in Cache directory successfully!");

    }

}
