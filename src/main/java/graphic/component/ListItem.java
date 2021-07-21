package graphic.component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.Auction;
import model.Deck;
import model.User;

public class ListItem extends AnchorPane implements ComponentLoader {

    @FXML
    Circle validity;
    @FXML
    Text cardsNum, deckName, nickName, rank, score, timer, auctionName;
    @FXML
    JFXButton delete, edit, enter;
    @FXML
    AnchorPane container;
    @FXML
    Circle onlineCircle;
    @FXML
    private JFXProgressBar progressBar;

    private User user;
    private Auction auction;

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

    public ListItem(Auction auction) {
        this.auction = auction;
        load("AuctionList");
//        timer.setText(String.valueOf(auction.get));
        auctionName.setText(String.valueOf(auction.getCard().getName()));
        progressBar.setProgress(auction.getTimer() / 30.0);
        enter.setGraphic(generateIcon(FontAwesomeIcon.EDIT));
    }

    public String getAuctionName() {
        return auctionName.getText();
    }

    public String getTimer() {
        return timer.getText();
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

    public JFXButton getEnter() {
        return enter;
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