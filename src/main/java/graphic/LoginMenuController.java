package graphic;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sample.MainGraphic;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class LoginMenuController extends Menu {
    @FXML
    public JFXButton changeState, mainButton;
    @FXML
    public Label title, textGuide;
    public AnchorPane root;

    @FXML
    private JFXTextField usernameField, nicknameField;
    @FXML
    private JFXPasswordField passwordField;

    private boolean isLoginMenu;

    @FXML
    public void initialize() {
        isLoginMenu = true;
        loadAppropriateState();
    }

    private void loadAppropriateState() {
        if (isLoginMenu) {
            title.setText(ComponentsText.LOGIN_TITLE.getContent());
            changeState.setText(ComponentsText.SIGNUP_BUTTON.getContent());
            mainButton.setText(ComponentsText.LOGIN_TITLE.getContent());
            textGuide.setText(ComponentsText.DONT_HAVE_ACCOUNT.getContent());
        } else {
            title.setText(ComponentsText.SIGNUP_TITLE.getContent());
            changeState.setText(ComponentsText.LOGIN_BUTTON.getContent());
            mainButton.setText(ComponentsText.SIGNUP_TITLE.getContent());
            textGuide.setText(ComponentsText.DO_HAVE_ACCOUNT.getContent());
        }
        nicknameField.setVisible(!isLoginMenu);
    }

    public void register() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nicknameField.getText().isEmpty()) {
            new SnackBarComponent("please enter your username, password and nickname", ResultState.SUCCESS, root);
        } else {
            Request.addData("username", usernameField.getText());
            Request.addData("password", passwordField.getText());
            Request.addData("nickname", nicknameField.getText());
            Request.setCommandTag(CommandTags.REGISTER);
            Request.addData("view", Menus.REGISTER_MENU.getLabel());
            Request.send();

            if (!Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, root);
            } else {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, root);
            }
        }
    }

    public void login() {
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            new SnackBarComponent("please enter your username, password and nickname", ResultState.SUCCESS, root);
        } else {
            Request.addData("username", usernameField.getText());
            Request.addData("password", passwordField.getText());
            Request.setCommandTag(CommandTags.LOGIN);
            Request.addData("view", Menus.REGISTER_MENU.getLabel());
            Request.send();

            if (!Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, root);
            } else {
                Request.getToken();
                setCurrentUser(usernameField.getText());
                MainGraphic.setRoot("MainMenu");
            }
        }
    }

    public void changeState(MouseEvent mouseEvent) {
        isLoginMenu = !isLoginMenu;
        loadAppropriateState();
    }

    public void perform(ActionEvent actionEvent) {
        if (isLoginMenu)
            login();
        else
            register();
    }
}
