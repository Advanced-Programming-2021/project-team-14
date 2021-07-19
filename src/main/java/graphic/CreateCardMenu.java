package graphic;

import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import graphic.animation.Shake;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Colors;
import model.Database;
import model.ImageCreator;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.*;
import sample.MainGraphic;
import view.Console;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateCardMenu extends MainMenu {
    public ImageView cardImageView;
    public AnchorPane mainPane;

    private static ArrayList<Circle> circles = new ArrayList<>();
    private static int currentCircleIndex;

    private static String name;
    private static CardType cardType;
    private static int price;
    private static MonsterType monsterType;
    private static Property property;
    private static String description;
    private static int attack;
    private static int defence;
    private static int level;
    private static BufferedImage bufferedImage;
    private static Graphics2D graphics;
    private static Attribute attribute;
    private static Status status;
    private static Card card;
    private static HashMap<String, String> effects = new HashMap<>();

    private static Stage popupStage;

    private static ArrayList<Node> sceneNodes = new ArrayList<>();
    public Label priceLabel;
    public AnchorPane cardPane;

    @FXML
    public void initialize() {
        effects.put("special summon", "NONE");
        effects.put("second attack/defense/lifePoint", "NONE");
        effects.put("second amount", "NONE");
        effects.put("second target", "NONE");
        effects.put("special command", "*");

        mainPane.getStylesheets().add(this.getClass().getResource("../sample/CSS/Snackbar.css").toExternalForm());
        mainPane.setStyle("-fx-background-color: #20202A");
        priceLabel.setStyle("-fx-font-size: 55; -fx-text-fill: WHITE; -fx-text-alignment: CENTER; -fx-alignment: CENTER;");
        cardPane.setStyle("-fx-border-style: dashed; -fx-stroke-width: 50; -fx-border-color: #8A9EAD;" +
                " -fx-border-width: 4; -fx-pref-height: 344; -fx-pref-width: 264;");
        attack = defence = level = -1;
        price = 0;
        createCircle(340, "general");
        createCircle(490, "special");
        createCircle(640, "save");
        setPrice(10);
        setExitButton();

        currentCircleIndex = 0;
        setGeneralScene();
    }

    private void setExitButton() {
        JFXButton exit = new JFXButton();
        exit.setLayoutX(80);
        exit.setText("EXIT");
        exit.setLayoutY(80);
        exit.setStyle("    -fx-font-size: 17; -fx-font-weight: bold; -fx-background-color: #6D5DD3;" +
                " -fx-text-fill: WHITE; -fx-pref-width: 150; -fx-background-radius: 20;");
        mainPane.getChildren().add(exit);
        exit.setOnAction(event -> {
            popupStage.close();
            MainGraphic.setRoot("MainMenu");
        });
    }

    private void setPrice(int amount) {
        price = price + amount;
        priceLabel.setText("PRICE: " + price);
    }

    private void setSaveScene() {
        currentCircleIndex = 2;
        circles.get(2).setFill(Color.DARKRED);

        if (cardType.equals(CardType.MONSTER)) {

            try {
                bufferedImage = ImageCreator.getBufferedImage("2");
                graphics = ImageCreator.getGraphics(bufferedImage);
                ImageCreator.createRectangle(graphics, Colors.DARK_RED);
                ImageCreator.createCircle(graphics);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageCreator.setName(graphics, name);
            ImageCreator.setLevel(graphics, String.valueOf(level));
            ImageCreator.setAttackAneDefence(graphics, String.valueOf(attack), String.valueOf(defence));
            ImageCreator.setMonsterTypeAndCardType(graphics, monsterType.getLabel());
            ImageCreator.setDescription(graphics, description);


            cardPane.setMinWidth((float) (bufferedImage.getWidth() / 3) + 8);
            cardPane.setMinHeight((float) (bufferedImage.getHeight() / 3) + 8);

            cardImageView.setFitHeight((float) (bufferedImage.getHeight()) / 3);
            cardImageView.setFitWidth((float) (bufferedImage.getWidth()) / 3);
            cardImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

            card = new Monster(name, level, attribute, monsterType, property, attack, defence, description, price, new HashMap<>());

        } else {

            try {
                bufferedImage = ImageCreator.getBufferedImage("1");
                graphics = ImageCreator.getGraphics(bufferedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }


            ImageCreator.createRectangle(graphics, Colors.GREEN);
            ImageCreator.setName(graphics, name);
            ImageCreator.setPropertyAndCardType(graphics, property.label, cardType.getLabel());
            ImageCreator.setDescription(graphics, description);

            cardPane.setMinWidth((float) (bufferedImage.getWidth() / 3) + 8);
            cardPane.setMinHeight((float) (bufferedImage.getHeight() / 3) + 8);

            cardImageView.setFitHeight((float) (bufferedImage.getHeight() / 3));
            cardImageView.setFitWidth((float) (bufferedImage.getWidth() / 3));
            cardImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));

            card = new SpellTrap(name, cardType, property, description, status, price, new HashMap<>());
        }

        JFXButton saveButton = new JFXButton();
        saveButton.setText("Save");
        saveButton.setLayoutX(100);
        saveButton.setLayoutY(300);
        saveButton.setStyle("    -fx-font-size: 17; -fx-font-weight: bold; -fx-background-color: #6D5DD3;" +
                " -fx-text-fill: WHITE;-fx-pref-width: 150; -fx-background-radius: 20;");


        saveButton.setOnAction(event -> {
            Request.setCommandTag(CommandTags.SAVE_CARD);
            Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
            Request.addData("card", new Gson().toJson(card));
            Request.send();

            if (!Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, mainPane);
            } else {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, mainPane);
            }
            Console.print(Request.getMessage());

        });


        mainPane.getChildren().add(saveButton);
        sceneNodes.add(saveButton);
    }

    private void setSpecialScene() {
        currentCircleIndex = 1;
        circles.get(1).setFill(Color.DARKRED);
        if (cardType.equals(CardType.MONSTER)) {
            setMonsterSpecialScene();
        } else {
            setSpellTrapSpecialScene();
        }
    }

    private void setMonsterSpecialScene() {

        JFXTextField levelField = setLevelTextField();
        levelField.setLayoutX(100);
        levelField.setLayoutY(140);

        JFXTextField attackField = setAttackTextField();
        attackField.setLayoutX(100);
        attackField.setLayoutY(220);

        JFXTextField defenceField = setDefenceTextField();
        defenceField.setLayoutY(300);
        defenceField.setLayoutX(100);

        JFXComboBox<Label> monsterTypeBox = setMonsterTypeBox();
        monsterTypeBox.setLayoutX(100);
        monsterTypeBox.setLayoutY(380);

        JFXComboBox<Label> monsterPropertyBox = setMonsterPropertyBox();
        monsterPropertyBox.setLayoutX(100);
        monsterPropertyBox.setLayoutY(460);

        JFXComboBox<Label> monsterAttributeBox = setMonsterAttribute();
        monsterAttributeBox.setLayoutX(100);
        monsterAttributeBox.setLayoutY(540);

        JFXTextArea descriptionArea = setDescriptionArea();
        descriptionArea.setLayoutX(650);
        descriptionArea.setLayoutY(140);


        JFXButton next = setNextLastButton(">", 730, 707);
        JFXButton last = setNextLastButton("<", 180, 707);

        next.setOnAction(event -> nextScene());
        last.setOnAction(event -> lastScene());

        sceneNodes.add(attackField);
        sceneNodes.add(defenceField);
        sceneNodes.add(next);
        sceneNodes.add(last);
        sceneNodes.add(monsterTypeBox);
        sceneNodes.add(monsterAttributeBox);
        sceneNodes.add(monsterPropertyBox);
        sceneNodes.add(levelField);
        sceneNodes.add(descriptionArea);


        mainPane.getChildren().add(descriptionArea);
        mainPane.getChildren().add(attackField);
        mainPane.getChildren().add(defenceField);
        mainPane.getChildren().add(last);
        mainPane.getChildren().add(next);
        mainPane.getChildren().add(monsterTypeBox);
        mainPane.getChildren().add(levelField);
        mainPane.getChildren().add(monsterPropertyBox);
        mainPane.getChildren().add(monsterAttributeBox);

    }

    private void setSpellTrapSpecialScene() {

        JFXComboBox<Label> propertyBox = setSpellTrapPropertyBox();
        propertyBox.setLayoutX(100);
        propertyBox.setLayoutY(130);

        JFXComboBox<Label> statusBox = setSpellTrapStatusBox();
        statusBox.setLayoutX(100);
        statusBox.setLayoutY(200);

        JFXComboBox<Label> numberOfAffectedCards = setNumberOfAffectedCardsBox();
        numberOfAffectedCards.setLayoutX(100);
        numberOfAffectedCards.setLayoutY(270);

        JFXComboBox<Label> effectTime = setEffectTimeBox();
        effectTime.setLayoutX(100);
        effectTime.setLayoutY(340);

        JFXComboBox<Label> changeDestroyAddCardLoader = setChangeDestroyAddCardLoaderBox();
        changeDestroyAddCardLoader.setLayoutX(100);
        changeDestroyAddCardLoader.setLayoutY(410);

        JFXComboBox<Label> from = setFromBox();
        from.setLayoutX(100);
        from.setLayoutY(480);

        JFXComboBox<Label> to = setToBox();
        to.setLayoutX(100);
        to.setLayoutY(550);

        JFXComboBox<Label> attackDefenseLifePoint = setFirstAttackDefenseLifePointBox();
        attackDefenseLifePoint.setLayoutX(650);
        attackDefenseLifePoint.setLayoutY(410);

        JFXComboBox<Label> firstTarget = setFirstTargetBox();
        firstTarget.setLayoutX(650);
        firstTarget.setLayoutY(480);


        JFXComboBox<Label> firstAmount = setFirstTargetAmountBox();
        firstAmount.setLayoutX(650);
        firstAmount.setLayoutY(550);


        JFXTextArea descriptionArea = setDescriptionArea();
        descriptionArea.setLayoutX(650);
        descriptionArea.setLayoutY(100);


        JFXButton next = setNextLastButton(">", 730, 707);
        JFXButton last = setNextLastButton("<", 180, 707);

        next.setOnAction(event -> nextScene());
        last.setOnAction(event -> lastScene());

        sceneNodes.add(propertyBox);
        sceneNodes.add(statusBox);
        sceneNodes.add(next);
        sceneNodes.add(last);
        sceneNodes.add(descriptionArea);
        sceneNodes.add(numberOfAffectedCards);
        sceneNodes.add(effectTime);
        sceneNodes.add(changeDestroyAddCardLoader);
        sceneNodes.add(from);
        sceneNodes.add(to);
        sceneNodes.add(attackDefenseLifePoint);
        sceneNodes.add(firstTarget);
        sceneNodes.add(firstAmount);


        mainPane.getChildren().add(descriptionArea);
        mainPane.getChildren().add(propertyBox);
        mainPane.getChildren().add(statusBox);
        mainPane.getChildren().add(last);
        mainPane.getChildren().add(next);
        mainPane.getChildren().add(numberOfAffectedCards);
        mainPane.getChildren().add(effectTime);
        mainPane.getChildren().add(changeDestroyAddCardLoader);
        mainPane.getChildren().add(from);
        mainPane.getChildren().add(to);
        mainPane.getChildren().add(attackDefenseLifePoint);
        mainPane.getChildren().add(firstTarget);
        mainPane.getChildren().add(firstAmount);


    }

    private JFXComboBox<Label> setNumberOfAffectedCardsBox() {
        JFXComboBox<Label> numberOfAffectedCardsBox = new JFXComboBox<>();
        numberOfAffectedCardsBox.getItems().add(new Label("NONE"));
        numberOfAffectedCardsBox.getItems().add(new Label("all"));
        numberOfAffectedCardsBox.getItems().add(new Label("1"));
        numberOfAffectedCardsBox.getItems().add(new Label("2"));
        numberOfAffectedCardsBox.getItems().add(new Label("3"));
        numberOfAffectedCardsBox.getItems().add(new Label("4"));

        numberOfAffectedCardsBox.setPromptText("number of affected cards");
        numberOfAffectedCardsBox.setLabelFloat(true);
        numberOfAffectedCardsBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        numberOfAffectedCardsBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("number of affected cardLoaders"))
                effects.remove("number of affected cardLoaders");

            effects.put("number of affected cardLoaders", newValue.getText());
            setPrice(200);
        });

        return numberOfAffectedCardsBox;

    }


    private JFXComboBox<Label> setChangeDestroyAddCardLoaderBox() {
        JFXComboBox<Label> changeDestroyAddCardLoaderBox = new JFXComboBox<>();
        changeDestroyAddCardLoaderBox.getItems().add(new Label("NONE"));
        changeDestroyAddCardLoaderBox.getItems().add(new Label("change"));
        changeDestroyAddCardLoaderBox.getItems().add(new Label("destroy"));
        changeDestroyAddCardLoaderBox.getItems().add(new Label("add"));
        changeDestroyAddCardLoaderBox.getItems().add(new Label("draw"));


        changeDestroyAddCardLoaderBox.setPromptText("change/destroy/add cardLoader");
        changeDestroyAddCardLoaderBox.setLabelFloat(true);
        changeDestroyAddCardLoaderBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        changeDestroyAddCardLoaderBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("change/destroy/add cardLoader"))
                effects.remove("change/destroy/add cardLoader");

            effects.put("change/destroy/add cardLoader", newValue.getText());
        });
        return changeDestroyAddCardLoaderBox;
    }

    private JFXComboBox<Label> setEffectTimeBox() {
        JFXComboBox<Label> effectTimeBox = new JFXComboBox<>();
        effectTimeBox.getItems().add(new Label("NONE"));
        effectTimeBox.getItems().add(new Label("activate effect"));
        effectTimeBox.getItems().add(new Label("rival attack"));
        effectTimeBox.getItems().add(new Label("attack"));
        effectTimeBox.getItems().add(new Label("activate effect"));
        effectTimeBox.getItems().add(new Label("entered name exist"));
        effectTimeBox.getItems().add(new Label("summon"));


        effectTimeBox.setPromptText("effect time");
        effectTimeBox.setLabelFloat(true);
        effectTimeBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        effectTimeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("effect time"))
                effects.remove("effect time");

            effects.put("effect time", newValue.getText());
        });
        return effectTimeBox;
    }

    private JFXComboBox<Label> setFirstAttackDefenseLifePointBox() {
        JFXComboBox<Label> firstAttackDefenseLifePointBox = new JFXComboBox<>();
        firstAttackDefenseLifePointBox.setOnMouseClicked(event -> {
            ArrayList<String> values = new ArrayList<>();
            if (effects.containsKey("change/destroy/add cardLoader")) {
                switch (effects.get("change/destroy/add cardLoader")) {
                    case "add":
                    case "destroy":
                    case "draw":
                        values.add("NONE");
                        break;
                    case "NONE":
                        values.add("NONE");
                        values.add("lifePoint");
                        break;
                    case "change":
                        values.add("attack");
                        break;
                }
            } else {
                new SnackBarComponent("please set change/destroy/add cardLoader first!", ResultState.ERROR, mainPane);
            }
            firstAttackDefenseLifePointBox.setItems(setBoxItems(values));
        });
        firstAttackDefenseLifePointBox.setPromptText("first attack/defense/lifePoint");
        firstAttackDefenseLifePointBox.setLabelFloat(true);
        firstAttackDefenseLifePointBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        firstAttackDefenseLifePointBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("first attack/defense/lifePoint"))
                effects.remove("first attack/defense/lifePoint");

            effects.put("first attack/defense/lifePoint", newValue.getText());
        });
        return firstAttackDefenseLifePointBox;
    }

    private JFXComboBox<Label> setFirstTargetBox() {
        JFXComboBox<Label> firstTargetBox = new JFXComboBox<>();

        firstTargetBox.setOnMouseClicked(event -> {
            ArrayList<String> values = new ArrayList<>();
            if (effects.containsKey("first attack/defense/lifePoint")) {
                switch (effects.get("first attack/defense/lifePoint")) {
                    case "attack":
                        values.add("NONE");
                        values.add("Beast");
                        values.add("DARK");
                        values.add("Aqua");
                        break;

                    case "lifePoint":
                        values.add("player");
                        break;

                    case "NONE":
                        switch (effects.get("change/destroy/add cardLoader")) {
                            case "add":
                                values.add("NONE");
                                values.add("Field");
                                break;
                            case "destroy":
                                values.add("monsters");
                                values.add("spells/traps");
                                break;
                            case "draw":
                            case "NONE":
                                values.add("NONE");
                                break;
                        }
                }
            } else {
                new SnackBarComponent("please set first attack/defense/lifePoint first!", ResultState.ERROR, mainPane);
            }
            firstTargetBox.setItems(setBoxItems(values));
        });

        firstTargetBox.setPromptText("first target");
        firstTargetBox.setLabelFloat(true);
        firstTargetBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        firstTargetBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("first target"))
                effects.remove("first target");

            effects.put("first target", newValue.getText());
        });
        return firstTargetBox;
    }


    private JFXComboBox<Label> setFirstTargetAmountBox() {
        JFXComboBox<Label> firstTargetAmountBox = new JFXComboBox<>();

        firstTargetAmountBox.setOnMouseClicked(event -> {
            ArrayList<String> values = new ArrayList<>();
            if (effects.containsKey("first attack/defense/lifePoint")) {
                switch (effects.get("first attack/defense/lifePoint")) {
                    case "attack":
                        values.add("graveyard");
                        values.add("100");
                        values.add("200");
                        values.add("300");
                        values.add("400");
                        values.add("500");
                        values.add("600");
                        break;

                    case "lifePoint":
                        values.add("100");
                        values.add("200");
                        values.add("300");
                        values.add("400");
                        values.add("500");
                        values.add("1000");
                        values.add("2000");
                        values.add("5000");
                        break;

                    case "NONE":
                        switch (effects.get("change/destroy/add cardLoader")) {
                            case "add":
                            case "destroy":
                            case "draw":
                            case "NONE":
                                values.add("NONE");
                                break;
                        }
                }
            } else {
                new SnackBarComponent("please set first attack/defense/lifePoint first!", ResultState.ERROR, mainPane);
            }

            firstTargetAmountBox.setItems(setBoxItems(values));
        });

        firstTargetAmountBox.setPromptText("first amount");
        firstTargetAmountBox.setLabelFloat(true);
        firstTargetAmountBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        firstTargetAmountBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("first amount"))
                effects.remove("first amount");

            effects.put("first amount", newValue.getText());
        });
        return firstTargetAmountBox;
    }

    private ObservableList<Label> setBoxItems(ArrayList<String> names) {
        ArrayList<Label> labels = new ArrayList<>();
        for (String name : names) {
            labels.add(new Label(name));
        }
        return FXCollections.observableArrayList(labels);
    }

    private JFXComboBox<Label> setFromBox() {
        JFXComboBox<Label> fromBox = new JFXComboBox<>();

        fromBox.setOnMouseClicked(event -> {
            ArrayList<String> values = new ArrayList<>();
            if (effects.containsKey("change/destroy/add cardLoader")) {
                switch (effects.get("change/destroy/add cardLoader")) {
                    case "add":
                        values.add("deck");
                        break;
                    case "NONE":
                        values.add("NONE");
                        break;
                    case "change":
                        values.add("NONE");
                        values.add("player board");
                        values.add("both");
                        break;
                    case "destroy":
                        values.add("both");
                        values.add("rival board");
                        break;
                    case "draw":
                        values.add("NONE");
                        break;
                }
            } else {
                new SnackBarComponent("please set change/destroy/add cardLoader first!", ResultState.ERROR, mainPane);
            }
            fromBox.setItems(setBoxItems(values));
        });


        fromBox.setPromptText("from");
        fromBox.setLabelFloat(true);
        fromBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");


        fromBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (effects.containsKey("from"))
                effects.remove("from");

            effects.put("from", newValue.getText());
        });
        return fromBox;
    }

    private JFXComboBox<Label> setToBox() {
        JFXComboBox<Label> toBox = new JFXComboBox<>();

        toBox.setOnMouseClicked(event -> {
            ArrayList<String> values = new ArrayList<>();
            if (effects.containsKey("change/destroy/add cardLoader")) {
                switch (effects.get("change/destroy/add cardLoader")) {
                    case "add":
                        values.add("hand");
                        break;
                    case "NONE":
                    case "change":
                    case "destroy":
                    case "draw":
                        values.add("NONE");
                        break;
                }
            } else {
                new SnackBarComponent("please set change/destroy/add cardLoader first!", ResultState.ERROR, mainPane);
            }
            toBox.setItems(setBoxItems(values));
        });

        toBox.setPromptText("to");
        toBox.setLabelFloat(true);
        toBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        toBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (effects.containsKey("to"))
                effects.remove("to");

            effects.put("to", newValue.getText());
        });
        return toBox;
    }

    private JFXComboBox<Label> setSpellTrapStatusBox() {
        JFXComboBox<Label> monsterPropertyBox = new JFXComboBox<>();
        monsterPropertyBox.getItems().add(new Label("Unlimited"));
        monsterPropertyBox.getItems().add(new Label("Limited"));

        monsterPropertyBox.setPromptText("status");
        monsterPropertyBox.setLabelFloat(true);
        monsterPropertyBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        monsterPropertyBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (newValue.toString().contains("Unlimited"))
                status = Status.UNLIMITED;
            else if (newValue.toString().contains("Limited"))
                status = Status.LIMITED;
        });

        return monsterPropertyBox;
    }

    private JFXComboBox<Label> setSpellTrapPropertyBox() {
        JFXComboBox<Label> monsterTypeBox = new JFXComboBox<>();
        monsterTypeBox.getItems().add(new Label("Continuous"));
        monsterTypeBox.getItems().add(new Label("Counter"));
        monsterTypeBox.getItems().add(new Label("Equip"));
        monsterTypeBox.getItems().add(new Label("Field"));
        monsterTypeBox.getItems().add(new Label("Normal"));
        monsterTypeBox.getItems().add(new Label("Quick-play"));
        monsterTypeBox.getItems().add(new Label("Ritual"));
        monsterTypeBox.setPromptText("property");
        monsterTypeBox.setLabelFloat(true);
        monsterTypeBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        monsterTypeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (newValue.toString().contains("Ritual"))
                property = Property.RITUAL;
            else if (newValue.toString().contains("Quick-play"))
                property = Property.QUICK_PLAY;
            else if (newValue.toString().contains("Normal"))
                property = Property.NORMAL;
            else if (newValue.toString().contains("Field"))
                property = Property.FIELD;
            else if (newValue.toString().contains("Equip"))
                property = Property.EQUIP;
            else if (newValue.toString().contains("Counter"))
                property = Property.COUNTER;
            else if (newValue.toString().contains("Continuous"))
                property = Property.CONTINUOUS;


            ImageCreator.setPropertyAndCardType(graphics, property.label, "Monster");
            updateImage();
        });

        return monsterTypeBox;
    }

    private void setGeneralScene() {
        currentCircleIndex = 0;
        circles.get(0).setFill(Color.DARKRED);

        JFXTextField nameField = setNameTextField();
        nameField.setLayoutX(100);
        nameField.setLayoutY(250);

        JFXComboBox<Label> cardTypeBox = setCardType();
        cardTypeBox.setLayoutX(630);
        cardTypeBox.setLayoutY(250);

        JFXButton next = setNextLastButton(">", 730, 707);


        next.setOnAction(event -> nextScene());

        sceneNodes.add(nameField);
        sceneNodes.add(cardTypeBox);
        sceneNodes.add(next);
        mainPane.getChildren().add(next);
        mainPane.getChildren().add(nameField);
        mainPane.getChildren().add(cardTypeBox);

    }

    private JFXButton setNextLastButton(String label, int x, int y) {
        JFXButton next = new JFXButton();
        next.setText(label);
        next.setLayoutX(x);
        next.setLayoutY(y);
        next.setStyle(" -fx-font-size: 20; -fx-background-color: #6D5DD3; -fx-text-fill: WHITE;" +
                " -fx-pref-height: 45; -fx-min-height: 45; -fx-max-height: 45; -fx-pref-width: 45;" +
                "    -fx-min-width: 45; -fx-max-width: 45; -fx-background-radius: 22.5;");
        return next;
    }

    private JFXComboBox<Label> setCardType() {
        JFXComboBox<Label> cardTypeBox = new JFXComboBox<>();
        cardTypeBox.getItems().add(new Label("Monster"));
        cardTypeBox.getItems().add(new Label("Spell"));
        cardTypeBox.getItems().add(new Label("Trap"));
        cardTypeBox.setPromptText("card type");
        cardTypeBox.setLabelFloat(true);
        cardTypeBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        cardTypeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            Database.createUserDirectoryInCacheDirectory(currentUser.getUsername());


            if (newValue.toString().contains("Monster")) {
                cardType = CardType.MONSTER;
                try {
                    bufferedImage = ImageCreator.getBufferedImage("2");
                    graphics = ImageCreator.getGraphics(bufferedImage);
                    ImageCreator.createRectangle(graphics, Colors.DARK_RED);
                    ImageCreator.createCircle(graphics);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (newValue.toString().contains("Spell")) {
                cardType = CardType.SPELL;
                try {
                    bufferedImage = ImageCreator.getBufferedImage("1");
                    graphics = ImageCreator.getGraphics(bufferedImage);
                    ImageCreator.createRectangle(graphics, Colors.GREEN);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                try {

                    bufferedImage = ImageCreator.getBufferedImage("1");
                    graphics = ImageCreator.getGraphics(bufferedImage);
                    ImageCreator.createRectangle(graphics, Colors.GREEN);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                cardType = CardType.TRAP;
            }

            updateImage();
        });

        cardTypeBox.setLayoutX(640);
        cardTypeBox.setLayoutY(250);
        return cardTypeBox;
    }

    private JFXComboBox<Label> setMonsterTypeBox() {
        JFXComboBox<Label> monsterTypeBox = new JFXComboBox<>();
        monsterTypeBox.getItems().add(new Label("Aqua"));
        monsterTypeBox.getItems().add(new Label("Beast"));
        monsterTypeBox.getItems().add(new Label("Cyberse"));
        monsterTypeBox.getItems().add(new Label("Dragon"));
        monsterTypeBox.getItems().add(new Label("Fairy"));
        monsterTypeBox.getItems().add(new Label("Fiend"));
        monsterTypeBox.getItems().add(new Label("Insect"));
        monsterTypeBox.getItems().add(new Label("Machine"));
        monsterTypeBox.getItems().add(new Label("Pyro"));
        monsterTypeBox.getItems().add(new Label("Rock"));
        monsterTypeBox.getItems().add(new Label("Sea Serpent"));
        monsterTypeBox.getItems().add(new Label("Spellcaster"));
        monsterTypeBox.getItems().add(new Label("Thunder"));
        monsterTypeBox.getItems().add(new Label("Warrior"));
        monsterTypeBox.getItems().add(new Label("Beast-Warrior"));
        monsterTypeBox.setPromptText("monster type");
        monsterTypeBox.setLabelFloat(true);
        monsterTypeBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        monsterTypeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (newValue.toString().contains("Aqua"))
                monsterType = MonsterType.AQUA;
            else if (newValue.toString().contains("Beast"))
                monsterType = MonsterType.BEAST;
            else if (newValue.toString().contains("Cyberse"))
                monsterType = MonsterType.CYBERSE;
            else if (newValue.toString().contains("Dragon"))
                monsterType = MonsterType.DRAGON;
            else if (newValue.toString().contains("Fairy"))
                monsterType = MonsterType.FAIRY;
            else if (newValue.toString().contains("Fiend"))
                monsterType = MonsterType.FIEND;
            else if (newValue.toString().contains("Insect"))
                monsterType = MonsterType.INSECT;
            else if (newValue.toString().contains("Machine"))
                monsterType = MonsterType.MACHINE;
            else if (newValue.toString().contains("Pyro"))
                monsterType = MonsterType.PYRO;
            else if (newValue.toString().contains("Rock"))
                monsterType = MonsterType.ROCK;
            else if (newValue.toString().contains("Sea Serpent"))
                monsterType = MonsterType.SEA_SERPENT;
            else if (newValue.toString().contains("Beast-Warrior"))
                monsterType = MonsterType.BEAST_WARRIOR;
            else if (newValue.toString().contains("Warrior"))
                monsterType = MonsterType.WARRIOR;
            else if (newValue.toString().contains("Thunder"))
                monsterType = MonsterType.THUNDER;
            else if (newValue.toString().contains("Spellcaster"))
                monsterType = MonsterType.SPELLCASTER;

            ImageCreator.setMonsterTypeAndCardType(graphics, monsterType.label);
            updateImage();
            setPrice(100);
        });

        return monsterTypeBox;
    }


    private JFXComboBox<Label> setMonsterPropertyBox() {
        JFXComboBox<Label> monsterPropertyBox = new JFXComboBox<>();
        monsterPropertyBox.getItems().add(new Label("Normal"));
        monsterPropertyBox.getItems().add(new Label("Effect"));
        monsterPropertyBox.getItems().add(new Label("Ritual"));
        monsterPropertyBox.setPromptText("monster property");
        monsterPropertyBox.setLabelFloat(true);
        monsterPropertyBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        monsterPropertyBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (newValue.toString().contains("Normal"))
                property = Property.NORMAL;
            else if (newValue.toString().contains("Effect"))
                property = Property.EFFECT;
            else if (newValue.toString().contains("Ritual"))
                property = Property.RITUAL;

            setPrice(100);
        });

        return monsterPropertyBox;
    }

    private JFXComboBox<Label> setMonsterAttribute() {
        JFXComboBox<Label> attributeBox = new JFXComboBox<>();
        attributeBox.getItems().add(new Label("Dark"));
        attributeBox.getItems().add(new Label("Light"));
        attributeBox.getItems().add(new Label("Earth"));
        attributeBox.getItems().add(new Label("Fire"));
        attributeBox.setPromptText("monster attribute");
        attributeBox.setLabelFloat(true);
        attributeBox.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        attributeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            if (newValue.toString().contains("Dark"))
                attribute = Attribute.DARK;
            else if (newValue.toString().contains("Light"))
                attribute = Attribute.LIGHT;
            else if (newValue.toString().contains("Earth"))
                attribute = Attribute.EARTH;
            else if (newValue.toString().contains("Fire"))
                attribute = Attribute.FIRE;

        });

        return attributeBox;
    }

    private JFXTextField setNameTextField() {
        JFXTextField nameField = new JFXTextField();

        nameField.setPromptText("card name");
        nameField.setStyle("-fx-font-size: 17; -fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        nameField.setLabelFloat(true);
        nameField.textProperty().addListener((obs, oldText, newText) -> {
            name = newText;
            if (bufferedImage == null)
                new SnackBarComponent("please select your card type!", ResultState.ERROR, mainPane);
            else if (Card.doesCardExist(newText))
                new SnackBarComponent(String.format(Responses.CARD_ALREADY_EXIST.getLabel(), newText), ResultState.ERROR, mainPane);
            else {
                ImageCreator.setName(graphics, newText);
                updateImage();
                if (name.length() < 10)
                    setPrice(50);
                else
                    setPrice(100);

            }
        });
        return nameField;
    }

    private JFXTextField setAttackTextField() {
        JFXTextField attackField = new JFXTextField();
        attackField.setPromptText("attack");
        attackField.setStyle("-fx-font-size: 17; -fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        attackField.setLabelFloat(true);
        attackField.textProperty().addListener((obs, oldText, newText) -> {

            if (!newText.matches("[\\d]+"))
                new SnackBarComponent("please enter an integer!", ResultState.ERROR, mainPane);
            else {
                attack = Integer.parseInt(newText);
                if (defence != -1)
                    ImageCreator.setAttackAneDefence(graphics, newText, String.valueOf(defence));

                updateImage();

                if (attack < 1000)
                    setPrice(150);
                else
                    setPrice(250);

            }
        });
        return attackField;
    }

    private JFXTextArea setDescriptionArea() {
        JFXTextArea descriptionField = new JFXTextArea();
        descriptionField.setPromptText("description...");
        descriptionField.setStyle("-fx-font-size: 17; -fx-text-fill: #8A9EAD; -fx-pref-width: 150;");
        descriptionField.setLabelFloat(true);
        descriptionField.textProperty().addListener((obs, oldText, newText) -> {
            description = newText;
            ImageCreator.setDescription(graphics, newText);
            updateImage();

            if (description.length() > 250)
                setPrice(10000);
            else
                setPrice(150);
        });
        return descriptionField;
    }

    private JFXTextField setLevelTextField() {
        JFXTextField attackField = new JFXTextField();
        attackField.setPromptText("level");
        attackField.setStyle("-fx-font-size: 17; -fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        attackField.setLabelFloat(true);
        attackField.textProperty().addListener((obs, oldText, newText) -> {

            if (!newText.matches("[\\d]"))
                new SnackBarComponent("please enter an integer!", ResultState.ERROR, mainPane);
            else {
                level = Integer.parseInt(newText);
                ImageCreator.setLevel(graphics, newText);
                updateImage();
                if (level < 5)
                    setPrice(150);
                else
                    setPrice(250);
            }
        });
        return attackField;
    }

    private JFXTextField setDefenceTextField() {
        JFXTextField defenceField = new JFXTextField();
        defenceField.setLayoutX(100);
        defenceField.setLayoutY(310);
        defenceField.setPromptText("defence");
        defenceField.setStyle("-fx-font-size: 17; -fx-text-fill: #8A9EAD; -fx-pref-width: 200;");
        defenceField.setLabelFloat(true);
        defenceField.textProperty().addListener((obs, oldText, newText) -> {

            if (bufferedImage == null)
                new SnackBarComponent("please select your card type!", ResultState.ERROR, mainPane);
            else if (!newText.matches("[\\d]+"))
                new SnackBarComponent("please enter an integer!", ResultState.ERROR, mainPane);
            else {
                defence = Integer.parseInt(newText);
                if (attack != -1)
                    ImageCreator.setAttackAneDefence(graphics, String.valueOf(attack), newText);


                updateImage();

                if (defence < 1000)
                    setPrice(150);
                else
                    setPrice(250);
            }
        });
        return defenceField;
    }

    private void updateImage() {

        cardImageView.setFitHeight(bufferedImage.getHeight() / 3.5);
        cardImageView.setFitWidth(bufferedImage.getWidth() / 3.5);
        cardImageView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
    }

    private void createCircle(int x, String label) {
        Circle circle = new Circle(x, 730, 22);
        circle.setStyle("-fx-pref-width: 100; -fx-pref-height: 100; -fx-font-size: 50; -fx-alignment: center;");
        circle.setFill(Color.valueOf("6d5dd3"));
        Label username = new Label(label);
        username.setLayoutX(x - 15);
        username.setLayoutY(720);
        username.setStyle("-fx-font-size: 10; -fx-text-fill: WHITE; -fx-text-alignment: RIGHT;");
        if (x != 640) {
            Label arrow = new Label("------>");
            arrow.setStyle(" -fx-font-size: 25; -fx-font-weight: bold; -fx-text-fill: #8A9EAD; -fx-text-alignment: RIGHT;");
            arrow.setLayoutX(x + 40);
            arrow.setLayoutY(710);
            mainPane.getChildren().add(arrow);
        }
        circles.add(circle);
        mainPane.getChildren().add(circle);
        mainPane.getChildren().add(username);
    }

    public void nextScene() {
        switch (currentCircleIndex) {
            case 0:
                if (name.isEmpty() || cardType == null) {
                    new SnackBarComponent("please fill the fields", ResultState.ERROR, mainPane);
                    break;
                }
                mainPane.getChildren().removeAll(sceneNodes);
                sceneNodes.addAll(sceneNodes);
                circles.get(0).setFill(Color.valueOf("6d5dd3"));
                setSpecialScene();
                break;

            case 1:

                if ((cardType.equals(CardType.MONSTER)) &&
                        (level == -1 || attribute == null || monsterType == null
                                || property == null || attack == -1 || defence == -1 ||
                                description == null || price == -1 )) {
                    new SnackBarComponent("please fill the fields", ResultState.ERROR, mainPane);
                    break;
                } else if ((cardType.equals(CardType.SPELL) || cardType.equals(CardType.TRAP)) &&
                        ((status == null || property == null || description == null || price == -1 || effects.size() != 13))) {
                    new SnackBarComponent("please fill the fields", ResultState.ERROR, mainPane);
                    break;
                }

                mainPane.getChildren().removeAll(sceneNodes);
                sceneNodes.addAll(sceneNodes);
                circles.get(1).setFill(Color.valueOf("6d5dd3"));

                setSaveScene();
                break;


        }
    }

    public void lastScene() {
        switch (currentCircleIndex) {
            case 1:
                mainPane.getChildren().removeAll(sceneNodes);
                sceneNodes.addAll(sceneNodes);
                circles.get(1).setFill(Color.valueOf("6d5dd3"));
                setGeneralScene();
                break;
            case 2:
                mainPane.getChildren().removeAll(sceneNodes);
                sceneNodes.addAll(sceneNodes);
                circles.get(2).setFill(Color.valueOf("6d5dd3"));
                setSpecialScene();
                break;

        }
    }

    public void run() {
        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.setWidth(980);
        popupStage.setHeight(800);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        Pane pane;
        try {
            CreateCardMenu.popupStage = popupStage;
            pane = FXMLLoader.load(getClass().getResource("..//sample//fxml//CreateCard.fxml"));
            popupStage.setScene(new Scene(pane, Color.DARKGREY));
            popupStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
