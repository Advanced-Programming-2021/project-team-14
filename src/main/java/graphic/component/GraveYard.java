package graphic.component;


import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.card.Card;
import model.game.Game;
import model.game.Player;
import model.game.PlayingDeck;
import sample.MainGraphic;

public class GraveYard extends AnchorPane implements ComponentLoader {

    @FXML
    private ImageView image;

    private PlayingDeck deck;

    private Game game;

    private Player player;

    private HBox root;

    public GraveYard(AnchorPane view, Game game, boolean isMain) {
        this.game = game;
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        load("GraveYard");
        image.setImage(new Image(MainGraphic.class.getResource("PNG/deckCards.png").toString()));

        image.setOnMouseClicked(e -> {

//            view.setEffect(new GaussianBlur());


            root = new HBox(20);
            root.setStyle("-fx-background-color: rgba(81, 84, 104, 0.8);");

            root.setAlignment(Pos.CENTER);
            root.setPadding(new Insets(20));

            addAllCards();

            Button back = new JFXButton("Back");
            String style = MainGraphic.class.getResource("CSS/GamePlay.css").toString();
            back.setStyle(style);
            root.getChildren().add(back);


            Stage popupStage = new Stage(StageStyle.TRANSPARENT);
//            popupStage.initOwner(primaryStage);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root, Color.DARKGREY));

            back.setOnAction(event -> {
                view.setEffect(null);
                popupStage.hide();
            });
            popupStage.show();
        });
    }

    private void addAllCards() {
        player.getGraveYard().getCards().forEach(this::addCard);
    }


    public void addCard(Card card) {
        graphic.component.CardLoader cardLoader = new graphic.component.CardLoader(card, CardSize.MEDIUM.getLabel(), "Hand");

        root.getChildren().add(cardLoader);
    }

    public void update(boolean isMain) {
        this.player = isMain ? game.getBoard().getMainPlayer() : game.getBoard().getRivalPlayer();
        addAllCards();
    }
}