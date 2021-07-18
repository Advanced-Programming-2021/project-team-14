package graphic.component;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Deck;
import model.User;

public class ListItem extends AnchorPane implements ComponentLoader {

    @FXML
    Circle validity;
    @FXML
    Text cardsNum, deckName, nickName, rank, score;
    @FXML
    JFXButton delete, edit;
    @FXML
    AnchorPane container;
    @FXML
    Circle onlineCircle;

    private User user;

    public ListItem(Deck deck, boolean isActiveDeck) {
        load("DeckList");
        cardsNum.setText(String.valueOf(deck.getSize()));
        deckName.setText(deck.getName());
        if (deck.getSize() > 40) {
            validity.setFill(Colors.SUCCESS.getColor());
        } else {
            validity.setFill(Colors.WARNING.getColor());
        }
        if (isActiveDeck)
            container.setStyle("-fx-border-color: GOLD; -fx-border-width: 1;");
        else
            container.setStyle("-fx-border-color: #46464f; -fx-border-width: 1;");

        delete.setGraphic(generateIcon(FontAwesomeIcon.TRASH));
        edit.setGraphic(generateIcon(FontAwesomeIcon.EDIT));

    }


    public ListItem(User user) {
        this.user = user;
        load("ScoreboardItem");
        rank.setText(String.valueOf(user.getRank()));
        score.setText(String.valueOf(user.getScore()));
        nickName.setText(user.getNickname());
        onlineCircle.setVisible(false);
    }

    public User getUser() {
        return this.user;
    }

    public void setOnlineCircle(boolean bool) {
        onlineCircle.setVisible(bool);
    }

    public AnchorPane getContainer() {
        return container;
    }

    public JFXButton getDelete() {
        return delete;
    }

    public JFXButton getEdit() {
        return edit;
    }

    public Text getCardsNum() {
        return cardsNum;
    }

    public String getDeckName() {
        return deckName.getText();
    }

    private FontAwesomeIconView generateIcon(FontAwesomeIcon fontAwesomeIcon) {
        FontAwesomeIconView icon = new FontAwesomeIconView(fontAwesomeIcon);
        icon.setFill(Colors.DARK_GRAY.getColor());
        icon.setSize("1.25em");
        return icon;
    }
}
