package graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXProgressBar;
import graphic.component.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.ImageCreator;
import model.TV;
import model.card.Card;
import model.card.Monster;
import model.card.enums.CardType;
import model.card.enums.Property;
import model.game.Duel;
import model.game.Game;
import sample.Main;
import sample.MainGraphic;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Television {

    public Label gameName;
    public AnchorPane tvShow;
    public AnchorPane mainPane;


    public AnchorPane downPlayerFieldZone, downPlayerDeckZone, downPlayerGraveYard,
            upperPlayerDeckZone, upperPlayerGraveYard, upperPlayerFieldZone;

    public ImageView background;
    public ImageView noisySignal;

    @FXML
    private AnchorPane view;
    @FXML
    private VBox sideCard;
    @FXML
    private ImageView selectedCardImage;
    @FXML
    private Text attack;
    @FXML
    private Text defense;
    @FXML
    private Text price;
    @FXML
    private Text description;
    @FXML
    private HBox phases;
    @FXML
    private HBox upperPlayerHand;
    @FXML
    private HBox upperPlayerSpellZone;
    @FXML
    public HBox upperPlayerMonsterZone;
    @FXML
    private HBox downPlayerHand;
    @FXML
    private HBox downPlayerSpellZone;
    @FXML
    private HBox downPlayerMonsterZone;
    @FXML
    private JFXButton pauseButton;
    @FXML
    private HBox upperPlayer;
    @FXML
    private HBox downPlayer;
    @FXML
    private Text downPlayerNickname;
    @FXML
    private Text upperPlayerNickname;
    @FXML
    private Text downPlayerLifePoint;
    @FXML
    private Text upperPlayerLifePoint;
    @FXML
    private Circle upperPlayerPhoto;
    @FXML
    private Circle downPlayerPhoto;
    @FXML
    private JFXProgressBar upperPlayerProgress;
    @FXML
    private JFXProgressBar downPlayerProgress;

    private Game game;

    public void initialize() {

        JFXComboBox<Label> onlineGames = setOnlineGames();
        onlineGames.setLayoutX(250);
        onlineGames.setLayoutY(650);

        JFXComboBox<Label> offlineGames = setOfflineGames();
        offlineGames.setLayoutX(600);
        offlineGames.setLayoutY(650);

        mainPane.getChildren().add(onlineGames);
        mainPane.getChildren().add(offlineGames);

    }


//
//    private void initGraveYard() {
//        downPlayerGraveYard.getChildren().add(new GraveYard(view, game, true));
//        upperPlayerGraveYard.getChildren().add(new GraveYard(view, game, false));
//    }
//
//
//    private void initDuelistInfo() {
//        downPlayerNickname.setText(game.getBoard().getMainPlayer().getNickname());
//        downPlayerLifePoint.setText(String.valueOf(game.getBoard().getMainPlayer().getLifePoint()));
//        downPlayerPhoto.setStroke(Color.TRANSPARENT);
////        Image image = Database.getProfilePhoto(game.getBoard().getMainPlayer().getUsername());
////        downPlayerPhoto.setFill(new ImagePattern(image));
//        upperPlayerNickname.setText(game.getBoard().getRivalPlayer().getNickname());
//        upperPlayerLifePoint.setText(String.valueOf(game.getBoard().getRivalPlayer().getLifePoint()));
//        upperPlayerPhoto.setStroke(Color.TRANSPARENT);
////        image = Database.getProfilePhoto(game.getBoard().getRivalPlayer().getUsername());
////        upperPlayerPhoto.setFill(new ImagePattern(image));
//        upperPlayerProgress.setStyle("-fx-accent: #1F545D");
//        double alaki = game.getBoard().getRivalPlayer().getLifePoint();
//        upperPlayerProgress.setProgress(game.getBoard().getRivalPlayer().getLifePoint() / 8000.0);
//        downPlayerProgress.setStyle("-fx-accent: #1f545d");
//        downPlayerProgress.setProgress(game.getBoard().getMainPlayer().getLifePoint() / 8000.0);
//
////        downPlayer.getChildren().add(new DuelistInfo(game, game.getBoard().getMainPlayer()));
////        upperPlayer.getChildren().add(new DuelistInfo(game, game.getBoard().getRivalPlayer()));
//
//    }
//
//    public void setImage(Image image) {
//        selectedCardImage.setImage(image);
//    }
//
//
//    public void setSpecification(CardLoader cardLoader) {
//
//        Card card = Card.getCardByName(cardLoader.getName());
//        if (card.getCardType() == CardType.MONSTER) {
//            attack.setText(String.valueOf(((Monster) card).getAttack()));
//            defense.setText(String.valueOf(((Monster) card).getDefence()));
//        } else {
//            attack.setText("");
//            defense.setText("");
//        }
//        price.setText(String.valueOf(card.getPrice()));
//        description.setText(card.getDescriptionGraphic());
//    }
//
//
//    public void setSpecificationForCard(Card card) {
//        if (card.getCardType() == CardType.MONSTER) {
//            attack.setText(String.valueOf(((Monster) card).getAttack()));
//            defense.setText(String.valueOf(((Monster) card).getDefence()));
//        } else {
//            attack.setText("");
//            defense.setText("");
//        }
//        price.setText(String.valueOf(card.getPrice()));
//        description.setText(card.getDescriptionGraphic());
//    }
//
//
//    private void initZones() {
//        downPlayerFieldZone.getChildren().add(new FieldZone(this));
//        upperPlayerFieldZone.getChildren().add(new FieldZone(this));
//        upperPlayerSpellZone.getChildren().add(new SpellZone(game, false, this));
//        downPlayerSpellZone.getChildren().add(new SpellZone(game, true, this));
//        downPlayerMonsterZone.getChildren().add(new MonsterZone(game, true, this));
//        upperPlayerMonsterZone.getChildren().add(new MonsterZone(game, false, this));
//        downPlayerDeckZone.getChildren().add(new DeckZone(game, game.getBoard().getMainPlayer(), true));
//        upperPlayerDeckZone.getChildren().add(new DeckZone(game, game.getBoard().getRivalPlayer(), false));
//    }
//
//
//
//    private void initHands() {
//        upperPlayerHand.getChildren().add(new RivalHand(game));
//        downPlayerHand.getChildren().add(new Hand(game, this));
//    }
//
//    public AnchorPane getView() {
//        return view;
//    }
//
//    private void initPhases() {
//        phases.getChildren().add(new Phases(game, this));
//    }
//
//    public void back(ActionEvent actionEvent) {
//        MainGraphic.setRoot("MainMenu");
//    }
//
//    public Game getGame() {
//        return game;
//    }
//
//    public void update() {
//        initDuelistInfo();
//        ((MonsterZone) downPlayerMonsterZone.getChildren().get(0)).update(true);
//        ((MonsterZone) upperPlayerMonsterZone.getChildren().get(0)).update(false);
//        ((SpellZone) downPlayerSpellZone.getChildren().get(0)).update(true);
//        ((SpellZone) upperPlayerSpellZone.getChildren().get(0)).update(false);
//        ((Hand) downPlayerHand.getChildren().get(0)).update();
//        ((RivalHand) upperPlayerHand.getChildren().get(0)).update();
//        ((DeckZone) downPlayerDeckZone.getChildren().get(0)).update(true);
//        ((DeckZone) upperPlayerDeckZone.getChildren().get(0)).update(false);
//        ((GraveYard) upperPlayerGraveYard.getChildren().get(0)).update(false);
//        ((GraveYard) downPlayerGraveYard.getChildren().get(0)).update(true);
//
//    }
//
//    public void updateFieldZone(Card card) {
//        ((FieldZone) downPlayerFieldZone.getChildren().get(0)).setCard(card);
//        background.setImage(new Image(MainGraphic.class.getResource("PNG/background/" + card.getName() + ".png").toString()));
//    }


    private JFXComboBox<Label> setOnlineGames() {
        JFXComboBox<Label> onlineGames = new JFXComboBox<>();
        onlineGames.getItems().setAll(setBoxItems(TV.getOnlineGames()));

        onlineGames.setPromptText("online games");
        onlineGames.setLabelFloat(true);
        onlineGames.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        onlineGames.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            Pattern pattern = Pattern.compile("(\\S) VS (\\S)");
            Matcher matcher = pattern.matcher(newValue.getText());

            if (matcher.find()) {
                // game = TV.showDuel(matcher.group(1), matcher.group(2));
                gameName.setText(newValue.getText());
//                initZones();
//                initPhases();
//                initDuelistInfo();
//                initGraveYard();
            }

        });
        return onlineGames;
    }

    private JFXComboBox<Label> setOfflineGames() {
        JFXComboBox<Label> onlineGames = new JFXComboBox<>();
        onlineGames.getItems().setAll(setBoxItems(TV.getOfflineGames()));

        onlineGames.setPromptText("offline games");
        onlineGames.setLabelFloat(true);
        onlineGames.setStyle("-fx-font-size: 17;-fx-text-fill: #8A9EAD; -fx-pref-width: 200;");

        onlineGames.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {

            Pattern pattern = Pattern.compile("(\\S) VS (\\S)");
            Matcher matcher = pattern.matcher(newValue.getText());

            if (matcher.find()) {
                // game = TV.showDuel(matcher.group(1), matcher.group(2));
                gameName.setText(newValue.getText());
//                initZones();
//                initPhases();
//                initDuelistInfo();
//                initGraveYard();
            }

        });
        return onlineGames;
    }


    private ObservableList<Label> setBoxItems(ArrayList<Duel> duels) {
        ArrayList<Label> labels = new ArrayList<>();
        for (Duel duel : duels) {
            labels.add(new Label(duel.getMainUser().getUsername() + " VS " + duel.getRivalUser().getUsername()));
        }
        return FXCollections.observableArrayList(labels);
    }


}

