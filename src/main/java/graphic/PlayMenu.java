package graphic;

import com.jfoenix.controls.JFXButton;
import graphic.component.CardLoader;
import graphic.component.DuelistInfo;
import graphic.component.Phases;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;
import view.GamePlayMenu;

public class PlayMenu extends Menu{

    public AnchorPane view;
    private Game game;
    @FXML
    private Text attack;
    @FXML
    private Text defense;
    @FXML
    private Text price;
    @FXML
    private ImageView selectedCardImage;
    @FXML
    private Text description;
    @FXML
    private JFXButton pauseButton;


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

//        initZones();
        initPhases();
        initHands();
        initDuelistInfo();
    }

    private void initDuelistInfo() {
        view.getChildren().add(new DuelistInfo(User.getUserByUsername(game.getBoard().getMainPlayer().getUsername()), false));
        view.getChildren().add(new DuelistInfo(User.getUserByUsername(game.getBoard().getRivalPlayer().getUsername()), true));
    }

    private void initHands() {
//        view.getChildren().add(new RivalHand(game));
//        view.getChildren().add(new Hand(game, this));
    }

    private void initPhases() {
        view.getChildren().add(new Phases(game));
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

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}
