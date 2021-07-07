package graphic;

import com.jfoenix.controls.JFXButton;
import graphic.component.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

    public AnchorPane downPlayerFieldZone;
    public ImageView background;
    @FXML
    private AnchorPane view;
    @FXML
    private VBox sideCard;
    @FXML
    private Circle upperPlayerPhoto;
    @FXML
    private Text upperPlayerNickname;
    @FXML
    private Text upperPlayerLifePoint;
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
    private Circle downPlayerPhoto;
    @FXML
    private Text downPlayerNickname;
    @FXML
    private Text downPlayerLifePoint;
    @FXML
    private HBox phases;
    @FXML
    private HBox upperPlayerHand;
    @FXML
    private HBox upperPlayerSpellZone;
    @FXML
    private HBox upperPlayerMonsterZone;
    @FXML
    private HBox downPlayerHand;
    @FXML
    private HBox downPlayerSpellZone;
    @FXML
    private HBox downPlayerMonsterZone;
    @FXML
    private JFXButton pauseButton;

    public Pane root;
    public AnchorPane addCardArea;
    private Game game;

    @FXML
    public void initialize() {

        KeyCombination cheat = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);
        game = Duel.getCurrentDuel().getGame();

        view.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (cheat.match(e)) {

                view.setEffect(new GaussianBlur());

                VBox pauseRoot = new VBox(5);
                pauseRoot.setStyle("-fx-background-color: rgba(81, 84, 104, 0.8);");

                pauseRoot.setAlignment(Pos.CENTER);
                pauseRoot.setPadding(new Insets(20));

                TextField textField = new TextField();
                pauseRoot.getChildren().add(textField);

                Button resume = new JFXButton("Resume");
                String style = MainGraphic.class.getResource("CSS/GamePlay.css").toString();
                resume.setStyle(style);
                Button apply = new JFXButton("Apply");
                apply.setStyle(style);

                pauseRoot.getChildren().add(apply);
                pauseRoot.getChildren().add(resume);


                Stage cheatStage = new Stage(StageStyle.TRANSPARENT);
//            popupStage.initOwner(primaryStage);
                cheatStage.initModality(Modality.APPLICATION_MODAL);
                cheatStage.setScene(new Scene(pauseRoot, Color.DARKGREY));

                apply.setOnAction(event -> {
                    new GamePlayMenu().commandCheckers(textField.getText());
                    view.setEffect(null);
                    cheatStage.hide();
                });

                resume.setOnAction(event -> {
                    view.setEffect(null);
                    cheatStage.hide();
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
    }


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

    private void initZones() {
        downPlayerMonsterZone.getChildren().add(new MonsterZone(game, true));
        upperPlayerMonsterZone.getChildren().add(new MonsterZone(game, false));
        downPlayerFieldZone.getChildren().add(new FieldZone());
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

    public void update() {
        ((MonsterZone) downPlayerMonsterZone.getChildren().get(0)).update();
        ((MonsterZone) upperPlayerMonsterZone.getChildren().get(0)).update();
        ((Hand) downPlayerHand.getChildren().get(0)).update();
        ((RivalHand) upperPlayerHand.getChildren().get(0)).update();
    }

    public void updateFieldZone(Card card) {
        ((FieldZone) downPlayerFieldZone.getChildren().get(0)).setCard(card);
        background.setImage(new Image(MainGraphic.class.getResource("PNG/background/" + card.getName() + ".png").toString()));
    }
}
