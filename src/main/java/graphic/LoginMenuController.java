package graphic;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import graphic.ComponentsText;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class LoginMenuController{
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
    public void initialize(){
        isLoginMenu = true;
        loadAppropriateState();
    }
    private void loadAppropriateState() {
        if (isLoginMenu){
            title.setText(ComponentsText.LOGIN_TITLE.getContent());
            changeState.setText(ComponentsText.SIGNUP_BUTTON.getContent());
            mainButton.setText(ComponentsText.LOGIN_TITLE.getContent());
            textGuide.setText(ComponentsText.DONT_HAVE_ACCOUNT.getContent());
        }else{
            title.setText(ComponentsText.SIGNUP_TITLE.getContent());
            changeState.setText(ComponentsText.LOGIN_BUTTON.getContent());
            mainButton.setText(ComponentsText.SIGNUP_TITLE.getContent());
            textGuide.setText(ComponentsText.DO_HAVE_ACCOUNT.getContent());
        }
        nicknameField.setVisible(!isLoginMenu);
    }

    public void register() {
        Label response = new Label();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nicknameField.getText().isEmpty()) {
            response.setText("please enter your username, password and nickname");
            response.getStyleClass().add("error-snackbar");
        } else {
            Request.addData("username", usernameField.getText());
            Request.addData("password", passwordField.getText());
            Request.addData("nickname", nicknameField.getText());
            Request.setCommandTag(CommandTags.REGISTER);
            Request.addData("view", Menus.REGISTER_MENU.getLabel());
            Request.send();

            if (!Request.isSuccessful()) {
                response.setText(Request.getMessage());
                response.getStyleClass().add("error-snackbar");
            } else {
                response.setText(Request.getMessage());
                response.getStyleClass().add("successful-snackbar");
            }
        }
        new JFXSnackbar(root).enqueue(new JFXSnackbar.SnackbarEvent(response, Duration.seconds(2), null));
    }
    public void login() {
        Label response = new Label();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            response.setText("please enter your username, password and nickname");
            response.getStyleClass().add("error-snackbar");
        } else {
            Request.addData("username", usernameField.getText());
            Request.addData("password", passwordField.getText());
            Request.setCommandTag(CommandTags.LOGIN);
            Request.addData("view", Menus.REGISTER_MENU.getLabel());
            Request.send();

            if (!Request.isSuccessful()) {
                response.setText(Request.getMessage());
                response.getStyleClass().add("error-snackbar");
            } else {
                response.setText(Request.getMessage());
                Request.getToken();
                response.getStyleClass().add("successful-snackbar");
            }
        }
        new JFXSnackbar(root).enqueue(new JFXSnackbar.SnackbarEvent(response, Duration.seconds(2), null));
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
