package graphic;

import graphic.component.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.User;
import model.card.Card;
import model.card.Monster;
import model.card.enums.CardType;
import model.game.Duel;
import model.game.Game;
import sample.MainGraphic;

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
    public void initialize() {
        game = Duel.getCurrentDuel().getGame();
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
        view.getChildren().add(new RivalHand(game));
        view.getChildren().add(new Hand(game, this));
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
