package graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import graphic.component.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.card.Card;
import model.card.Monster;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;
import view.GamePlayMenu;


public class GamePlay extends Menu {

    public AnchorPane downPlayerFieldZone, downPlayerDeckZone, downPlayerGraveYard,
            upperPlayerDeckZone, upperPlayerGraveYard, upperPlayerFieldZone;

    public ImageView background;
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


    public Pane root;
    public AnchorPane addCardArea;
    private Game game;

    @FXML
    public void initialize() {
        Medias.GAMEPLAY_BACKGROUND.loop();
        Medias.GAMEPLAY_BACKGROUND.reduceVolume();
        KeyCombination cheat = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        game = Duel.getCurrentDuel().getGame();

        view.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (cheat.match(e)) {

                view.setEffect(new GaussianBlur());

                HBox pauseRoot = new HBox(20);
                pauseRoot.setEffect(new DropShadow());
                pauseRoot.setStyle("-fx-background-color: rgba(39, 39, 49, 0.95); -fx-background-radius: 15; -fx-border-radius: 15; -fx-pref-width: 340; -fx-pref-height: 50;");
                ImageView icon = new ImageView(new Image(MainGraphic.class.getResource("PNG/terminal.png").toString()));
                icon.setFitHeight(25);
                icon.setFitWidth(25);
                pauseRoot.getChildren().add(icon);
                pauseRoot.setAlignment(Pos.TOP_LEFT);
                pauseRoot.setPadding(new Insets(20));

                JFXTextField textField = new JFXTextField();
                textField.setPrefWidth(300);
//                textField.setPadding(new Insets());
                textField.setStyle("-fx-text-fill: WHITE; -fx-font-size: 15; -fx-font-family: 'a Astro Space'");

                textField.setFocusColor(Color.TRANSPARENT);
                textField.setUnFocusColor(Color.TRANSPARENT);
                pauseRoot.getChildren().add(textField);


                Stage cheatStage = new Stage(StageStyle.TRANSPARENT);
//            popupStage.initOwner(primaryStage);
                cheatStage.initStyle(StageStyle.TRANSPARENT);
                cheatStage.initModality(Modality.APPLICATION_MODAL);
                cheatStage.setScene(new Scene(pauseRoot, Color.TRANSPARENT));
                cheatStage.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
                    if (event.getCode() == KeyCode.ENTER){
                        System.out.println("enter");
                        new GamePlayMenu().commandCheckers(textField.getText());
                        update();
                        view.setEffect(null);
                        cheatStage.hide();
                    }else if (event.getCode() == KeyCode.ESCAPE){
                        System.out.println("cancel");
                        view.setEffect(null);
                        cheatStage.hide();
                    }
                });


                cheatStage.show();
            }
        });

        pauseButton.setOnAction(e -> {

            view.setEffect(new GaussianBlur());

            VBox pauseRoot = new VBox(5);
            pauseRoot.setStyle("-fx-background-color: rgba(81, 84, 104, 0.8);");

            pauseRoot.setAlignment(Pos.CENTER);
            pauseRoot.setPadding(new Insets(20));

            Button resume = new JFXButton("Resume");
            String style = MainGraphic.class.getResource("CSS/GamePlay.css").toString();
            resume.setStyle(style);
            Button exit = new JFXButton("Exit");
            exit.setStyle(style);
            pauseRoot.getChildren().add(resume);
            pauseRoot.getChildren().add(exit);

            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
//            popupStage.initOwner(primaryStage);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(pauseRoot, Color.DARKGREY));

            exit.setOnAction(event -> {
                view.setEffect(null);
                popupStage.hide();
                Medias.GAMEPLAY_BACKGROUND.pause();
                MainGraphic.setRoot("MainMenu");
            });

            resume.setOnAction(event -> {
                view.setEffect(null);
                popupStage.hide();
            });

            popupStage.show();
        });

        initHands();
        initZones();
        initPhases();
        initDuelistInfo();
        initGraveYard();
//        initLifepointsCircles();
    }

    private void initGraveYard() {
        downPlayerGraveYard.getChildren().add(new GraveYard(view, game, true));
        upperPlayerGraveYard.getChildren().add(new GraveYard(view, game, false));
    }

//    private void initLifepointsCircles() {
//
//        RingProgressIndicator ringProgressIndicator = new RingProgressIndicator();
//        ringProgressIndicator.setRingWidth(5);
//        ringProgressIndicator.makeIndeterminate();
//
//        upperLifepointCircle.getChildren().add(ringProgressIndicator);
//        LifePointCircle pt = new LifePointCircle(ringProgressIndicator, );
//        pt.start();
//    }


    private void initDuelistInfo() {
        downPlayerNickname.setText(game.getBoard().getMainPlayer().getNickname());
        downPlayerLifePoint.setText(String.valueOf(game.getBoard().getMainPlayer().getLifePoint()));
        downPlayerPhoto.setStroke(Color.TRANSPARENT);
//        Image image = Database.getProfilePhoto(game.getBoard().getMainPlayer().getUsername());
//        downPlayerPhoto.setFill(new ImagePattern(image));
        upperPlayerNickname.setText(game.getBoard().getRivalPlayer().getNickname());
        upperPlayerLifePoint.setText(String.valueOf(game.getBoard().getRivalPlayer().getLifePoint()));
        upperPlayerPhoto.setStroke(Color.TRANSPARENT);
//        image = Database.getProfilePhoto(game.getBoard().getRivalPlayer().getUsername());
//        upperPlayerPhoto.setFill(new ImagePattern(image));
        upperPlayerProgress.setStyle("-fx-accent: #1F545D");
        double alaki = game.getBoard().getRivalPlayer().getLifePoint();
        upperPlayerProgress.setProgress(game.getBoard().getRivalPlayer().getLifePoint() / 8000.0);
        downPlayerProgress.setStyle("-fx-accent: #1f545d");
        downPlayerProgress.setProgress(game.getBoard().getMainPlayer().getLifePoint() / 8000.0);

//        downPlayer.getChildren().add(new DuelistInfo(game, game.getBoard().getMainPlayer()));
//        upperPlayer.getChildren().add(new DuelistInfo(game, game.getBoard().getRivalPlayer()));

    }

    public void setImage(Image image) {
        selectedCardImage.setImage(image);
    }


    public void setSpecification(CardLoader cardLoader) {

        Card card = Card.getCardByName(cardLoader.getName());
        if (card.getCardType() == CardType.MONSTER) {
            attack.setText(String.valueOf(((Monster) card).getAttack()));
            defense.setText(String.valueOf(((Monster) card).getDefence()));
        } else {
            attack.setText("");
            defense.setText("");
        }
        price.setText(String.valueOf(card.getPrice()));
        description.setText(card.getDescriptionGraphic());
    }


    public void setSpecificationForCard(Card card) {
        if (card.getCardType() == CardType.MONSTER) {
            attack.setText(String.valueOf(((Monster) card).getAttack()));
            defense.setText(String.valueOf(((Monster) card).getDefence()));
        } else {
            attack.setText("");
            defense.setText("");
        }
        price.setText(String.valueOf(card.getPrice()));
        description.setText(card.getDescriptionGraphic());
    }


    private void initZones() {
        downPlayerFieldZone.getChildren().add(new FieldZone(this));
        upperPlayerFieldZone.getChildren().add(new FieldZone(this));
        upperPlayerSpellZone.getChildren().add(new SpellZone(game, false, this));
        downPlayerSpellZone.getChildren().add(new SpellZone(game, true, this));
        downPlayerMonsterZone.getChildren().add(new MonsterZone(game, true, this));
        upperPlayerMonsterZone.getChildren().add(new MonsterZone(game, false, this));
        downPlayerDeckZone.getChildren().add(new DeckZone(game, game.getBoard().getMainPlayer(), true));
        upperPlayerDeckZone.getChildren().add(new DeckZone(game, game.getBoard().getRivalPlayer(), false));
//        initFieldZone();
    }


//    private void initFieldZone() {
//        ZoneCell fieldZone = new ZoneCell();
//        fieldZone.setLayoutX(0);
//        fieldZone.setLayoutY(0);

////        fieldZone.setOnDragExited(e -> {
////                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
////                System.out.println("drag exit");
////                fieldZone.getStyleClass().add("normal");
////        });
////        fieldZone.setOnDragDropped(e -> {
////                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
////                System.out.println("added to container area: " + ((Card)e.getSource()).getName());
////        });
//        view.getChildren().add(fieldZone);
//    }

    private void initHands() {
        upperPlayerHand.getChildren().add(new RivalHand(game));
        downPlayerHand.getChildren().add(new Hand(game, this));
    }

    public AnchorPane getView() {
        return view;
    }

    private void initPhases() {
        phases.getChildren().add(new Phases(game, this));
    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }

    public Game getGame() {
        return game;
    }

    public void update() {
        initDuelistInfo();
        ((MonsterZone) downPlayerMonsterZone.getChildren().get(0)).update(true);
        ((MonsterZone) upperPlayerMonsterZone.getChildren().get(0)).update(false);
        ((SpellZone) downPlayerSpellZone.getChildren().get(0)).update(true);
        ((SpellZone) upperPlayerSpellZone.getChildren().get(0)).update(false);
        ((Hand) downPlayerHand.getChildren().get(0)).update();
        ((RivalHand) upperPlayerHand.getChildren().get(0)).update();
        ((DeckZone) downPlayerDeckZone.getChildren().get(0)).update(true);
        ((DeckZone) upperPlayerDeckZone.getChildren().get(0)).update(false);
        ((GraveYard) upperPlayerGraveYard.getChildren().get(0)).update(false);
        ((GraveYard) downPlayerGraveYard.getChildren().get(0)).update(true);

    }

    public void updateFieldZone(Card card) {
        ((FieldZone) downPlayerFieldZone.getChildren().get(0)).setCard(card);
        background.setImage(new Image(MainGraphic.class.getResource("PNG/background/" + card.getName() + ".png").toString()));
    }
}
