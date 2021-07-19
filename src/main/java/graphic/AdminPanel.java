package graphic;

import com.jfoenix.controls.JFXButton;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class AdminPanel extends Menu {

    @FXML
    private AnchorPane view;

    @FXML
    private TextField cardNameField;

    @FXML
    private TextField amountField;

    @FXML
    private JFXButton banButton;

    @FXML
    private JFXButton applyChangeButton;

    @FXML
    private Text initialText;


    @FXML
    public void initialize() {

        initialText.setVisible(true);
        cardNameField.setVisible(false);
        amountField.setVisible(false);
        banButton.setVisible(false);
        applyChangeButton.setVisible(false);


        KeyCombination cheat = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN, KeyCombination.SHIFT_DOWN);

        view.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if (cheat.match(e)) {

                initialText.setVisible(false);
                cardNameField.setVisible(true);
                amountField.setVisible(true);
                banButton.setVisible(true);
                applyChangeButton.setVisible(true);
            }
        });
    }


    @FXML
    void applyChange(ActionEvent event) {

        setView(Menus.SHOP_MENU);
        Request.setCommandTag(CommandTags.ADMIN_CHANGE_AMOUNT);
        Request.addData("cardName", cardNameField.getText());
        Request.addData("amount", amountField.getText());
        Request.send();

        if (Request.isSuccessful()) {
            new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, view);
        } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR, view);
    }

    @FXML
    void back(ActionEvent event) {
        MainGraphic.setRoot("ShopMenu");
    }

    @FXML
    void ban(ActionEvent event) {

        setView(Menus.SHOP_MENU);
        Request.setCommandTag(CommandTags.BAN);
        Request.addData("cardName", cardNameField.getText());
        Request.send();

        if (Request.isSuccessful()) {

            new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, view);
        } else new SnackBarComponent(Request.getMessage(), ResultState.ERROR, view);
    }
}