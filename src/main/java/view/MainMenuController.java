package view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainMenuController implements Initializable {


    @FXML
    private AnchorPane sideBar;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }


    public void duelButton(javafx.event.ActionEvent actionEvent) {


        //        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("shop");
//        borderPane.setCenter(view);

    }

    @FXML
    public void deckButton(javafx.event.ActionEvent actionEvent) {


        //        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("shop");
//        borderPane.setCenter(view);
    }

    @FXML
    public void shopButton(javafx.event.ActionEvent actionEvent) {

//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("shop");
//        borderPane.setCenter(view);


//        ((Button) actionEvent.getSource()).getStyleClass().removeAll("addBobOk, focus");
//
//        ((Button) actionEvent.getSource()).getStyleClass().add("addBobOk");
    }

    @FXML
    public void scoreboardButton(javafx.event.ActionEvent actionEvent) {

//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("scoreboard");
//        borderPane.setCenter(view);


    }

    @FXML
    public void profileButton(javafx.event.ActionEvent actionEvent) {


//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("profile");
//        borderPane.setCenter(view);
    }

    @FXML
    public void importExportButton(javafx.event.ActionEvent actionEvent) {

//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("importExport");
//        borderPane.setCenter(view);
    }

    @FXML
    public void logoutButton(javafx.event.ActionEvent actionEvent) {


//        FxmlLoader object = new FxmlLoader();
//        Pane view = object.getPage("logout");
//        borderPane.setCenter(view);
    }
}