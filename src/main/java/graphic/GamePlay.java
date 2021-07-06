package graphic;

import graphic.component.Hand;
import graphic.component.Phases;
import graphic.component.RivalHand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;


public class GamePlay extends Menu {

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

    public Pane root;
    public AnchorPane addCardArea;
    private Game game;

    @FXML
    public void initialize() {
        game = Duel.getCurrentDuel().getGame();
//        initZones();
        initPhases();
        initHands();
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

    private void initZones() {
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
////                System.out.println("added to container area: " + ((Card)e.getSource()).getName()); //TODO: do the task
////        });
//        view.getChildren().add(fieldZone);
//    }

    private void initHands() {
        upperPlayerHand.getChildren().add(new RivalHand(game));
        downPlayerHand.getChildren().add(new Hand(game, view));
    }

    private void initPhases() {
        phases.getChildren().add(new Phases(game));
    }

    public void back(ActionEvent actionEvent) {
        MainGraphic.setRoot("MainMenu");
    }
}