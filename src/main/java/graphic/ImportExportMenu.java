package graphic;

import Controller.enums.DatabaseResponses;
import com.jfoenix.controls.JFXComboBox;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import model.Database;
import model.ImageCreator;
import model.card.Card;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class ImportExportMenu extends MainMenu {


    public AnchorPane mainPane;
    public AnchorPane dragPane;

    public javafx.scene.image.ImageView cardImageView;

    private static ArrayList<String> importedCards = new ArrayList<>();
    private static ArrayList<Image> cardImages = new ArrayList<>();
    private static String currentCardName;

    @FXML
    public void initialize() {
        setDragPane();
        showImportedCards();
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
                    importedCards = Database.getDraggedCardNames();
                    for (String name : importedCards) {
                        cardNames.append(name).append(", ");
                    }
                    cardNames.replace(cardNames.lastIndexOf(","), cardNames.lastIndexOf(",") + 1, " ");

                    if (responses.equals(DatabaseResponses.SUCCESSFUL)) {
                        createImportedCardImage(0);
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


    private void createImportedCardImage(int index) {
        cardImages = new ArrayList<>();
        for (String name : importedCards) {
            Database.createUserDirectoryInSavedCardsDirectory(currentUser.getUsername());
            String path = "Resources\\SavedCards\\" + currentUser.getUsername() + "\\" + name + ".png";
            ImageCreator.createCardImage(Card.getCardByName(name), path);
            cardImages.add(Database.getCardImage(path));
        }
        currentCardName = importedCards.get(index);
        showImage(index);
    }


    private void showImage(int index) {
        Image image = cardImages.get(index);
        cardImageView.setFitHeight(image.getHeight() / 3.5);
        cardImageView.setFitWidth(image.getWidth() / 3.5);
        cardImageView.setImage(null);
        cardImageView.setImage(image);

    }


    private void showImportedCards() {
        JFXComboBox<Label> importedCardsList = new JFXComboBox<>();
        importedCardsList.setLayoutX(650);
        importedCardsList.setLayoutY(280);
        importedCardsList.setLabelFloat(true);
        importedCardsList.setPromptText("imported cards");
        mainPane.getChildren().add(importedCardsList);

        importedCardsList.setOnMouseClicked(event -> {
            if (importedCards.size() == 0) {
                new SnackBarComponent("there is no card to show!", ResultState.ERROR);
                return;
            }

            for (String name : importedCards) {
                importedCardsList.setItems(setBoxItems(importedCards));
                String path = "Resources\\SavedCards\\" + currentUser.getUsername() + "\\" + name + ".png";
                ImageCreator.createCardImage(Card.getCardByName(name), path);
                cardImages.add(Database.getCardImage(path));
            }

        });


        importedCardsList.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (importedCards != null) {
                if (newValue != null) {
                    int index = importedCards.indexOf(newValue.getText());
                    showImage(index);
                    currentCardName = newValue.getText();
                }

            }
        });

    }

    private ObservableList<Label> setBoxItems(ArrayList<String> names) {
        ArrayList<Label> labels = new ArrayList<>();
        for (String name : names) {
            labels.add(new Label(name));
        }
        return FXCollections.observableArrayList(labels);
    }


    public void exportCard() {
        try {
            Request.setCommandTag(CommandTags.EXPORT);
            Request.addData("cardName", currentCardName);
            Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
            Request.send();

            if (Request.isSuccessful())
                new SnackBarComponent(currentCardName + " exported successfully!", ResultState.SUCCESS);
            else
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            new SnackBarComponent("There is no card to export", ResultState.ERROR);
        }

    }

    public void createCard(ActionEvent event) {
        new CreateCardMenu().run();
    }
}
