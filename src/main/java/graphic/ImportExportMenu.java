package graphic;

import Controller.enums.DatabaseResponses;
import com.jfoenix.controls.JFXComboBox;
import com.sun.java.swing.plaf.windows.WindowsDesktopIconUI;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.Colors;
import model.Database;
import model.ImageCreator;
import model.card.Card;
import model.card.Monster;
import model.card.SpellTrap;
import model.card.enums.CardType;
import model.card.enums.Status;
import sample.MainGraphic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ImportExportMenu extends MainMenu {


    public AnchorPane mainPane;
    public AnchorPane dragPane;
    public Label statusLabel;
    public javafx.scene.image.ImageView cardImageView;

    private static ArrayList<Image> cardImages = new ArrayList<>();
    private static ArrayList<String> canExportedCardsName = new ArrayList<>();
    private static String currentCardName;

    @FXML
    public void initialize() {
        setDragPane();
        showCanExportedCards();
    }


    private void setDragPane() {
        dragPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != mainPane
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        dragPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {

                    DatabaseResponses responses = Database.openDraggedCardFile(db.getUrl().replace("file:/", ""));
                    StringBuilder cardNames = new StringBuilder();
                    ArrayList<String> draggedCardsName = Database.getDraggedCardNames();
                    for (String name : draggedCardsName) {
                        cardNames.append(name).append(", ");
                    }
                    cardNames.replace(cardNames.lastIndexOf(","), cardNames.lastIndexOf(",") + 1, " ");

                    if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
                        createImportedCardImage(draggedCardsName);
                        new SnackBarComponent(cardNames.toString() + "imported successfully!", ResultState.SUCCESS);
                    } else
                        new SnackBarComponent(responses.getLabel(), ResultState.ERROR);

                    success = true;
                }


                event.setDropCompleted(success);
                event.consume();
            }


        });
    }


    private void createImportedCardImage(ArrayList<String> draggedCardsName) {
        cardImages = new ArrayList<>();
        for (String name : draggedCardsName) {
            Database.createUserDirectoryInSavedCardsDirectory(currentUser.getUsername());
            String path = "Resources\\SavedCards\\" + currentUser.getUsername() + "\\" + name + ".png";
            ImageCreator.createCardImage(Card.getCardByName(name), path);
            cardImages.add(Database.getCardImage(path));
        }
        currentCardName = draggedCardsName.get(0);
        statusLabel.setText("Imported Cards");
        showImage(0);
    }


    private void showImage(int index) {
        Image image = cardImages.get(index);
        cardImageView.setFitHeight(image.getHeight() / 3.5);
        cardImageView.setFitWidth(image.getWidth() / 3.5);
        cardImageView.setImage(image);

    }


    private JFXComboBox<Label> showCanExportedCards() {
        JFXComboBox<Label> canExport = new JFXComboBox<>();
        canExport.setLayoutX(100);
        canExport.setLayoutY(150);
        canExport.setLabelFloat(true);
        canExport.setPromptText("to export");
        mainPane.getChildren().add(canExport);

        canExport.setOnMouseClicked(event -> {
            canExportedCardsName = new ArrayList<>();
            canExportedCardsName = Database.loadCanExportedCards(currentUser.getUsername());


            if (canExportedCardsName.size() == 0) {
                new SnackBarComponent("there is no card to export", ResultState.ERROR);
                return;
            }

            for (String name : canExportedCardsName) {
                canExport.getItems().add(new Label(name)); String path = "Resources\\SavedCards\\" + currentUser.getUsername() + "\\" + name + ".png";
                ImageCreator.createCardImage(Card.getCardByName(name), path);
                cardImages.add(Database.getCardImage(path));
            }

        });


        canExport.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            statusLabel.setText("exported cards");
            int index = canExportedCardsName.indexOf(newValue.getText());

            showImage(index);
            currentCardName = newValue.getText();
        });

       return canExport;
    }


    public void exportCard() {
        try {
            DatabaseResponses responses = Database.exportCard(currentUser.getUsername(), Card.getCardByName(currentCardName));

            if (responses.equals(DatabaseResponses.SUCCESSFUL))
                new SnackBarComponent(currentCardName + " exported successfully!", ResultState.SUCCESS);
            else
                new SnackBarComponent(responses.getLabel(), ResultState.ERROR);
        } catch (Exception e) {
            new SnackBarComponent("There is no card to export", ResultState.ERROR);
        }

    }

    public void createCard(ActionEvent event) {
        if (this.view.getChildren().size() > 0)
            this.view.getChildren().remove(0);
        this.view.getChildren().add(MainGraphic.loadFXML("CreateCard"));

    }
}
