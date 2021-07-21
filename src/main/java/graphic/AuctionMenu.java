package graphic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXListView;
import graphic.component.ListItem;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import model.Auction;
import model.Strings;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class AuctionMenu extends Menu {


    @FXML
    private AnchorPane view;
    @FXML
    private JFXListView listView;


    private ArrayList<Auction> auctions;
    private boolean exit;

    @FXML
    public void initialize() {
        exit = false;
        new Thread(() -> {
            System.out.println(Menu.getData());
            while (!exit) {
                update();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void update() {
        getAuctions();

        Platform.runLater(() -> {
            listView.getItems().clear();
            listView.setBackground(Background.EMPTY);
            auctions.forEach(this::addItem);
            listView.setVerticalGap(10.0);
        });
    }

    private void getAuctions() {
        setView(Menus.AUCTION);
        Request.setCommandTag(CommandTags.GET_AUCTIONS);
        Request.send();

        auctions = new Gson().fromJson(Request.getMessage(), new TypeToken<ArrayList<Auction>>() {
        }.getType());
    }


    private void addItem(Auction auction) {
        ListItem auctionListItem = new ListItem(auction);

        auctionListItem.getEnter().setOnMouseClicked(e -> {
            addData(auctionListItem.getAuctionName());
            MainGraphic.setRoot("AuctionInside");
        });

        listView.getItems().add(auctionListItem);
    }


    public void addAuction(ActionEvent actionEvent) {
        TextInputDialog textInputDialog = new TextInputDialog("Enter the card name");
        textInputDialog.show();
        textInputDialog.getDialogPane().lookupButton(ButtonType.OK).addEventFilter(ActionEvent.ACTION, action -> {
            setView(Menus.AUCTION);
            Request.addData(Strings.CARD.getLabel(), textInputDialog.getEditor().getText());
            Request.setCommandTag(CommandTags.CREATE_AUCTION);
            Request.send();
            if (Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS);
                update();
            } else
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR);

        });
    }
}