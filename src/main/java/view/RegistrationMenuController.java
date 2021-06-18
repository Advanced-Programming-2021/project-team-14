package view;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.enums.CommandTags;
import view.enums.Menus;

public class RegistrationMenuController extends Menu {


    @FXML
    private JFXTextField nicknameField;
    @FXML
    private JFXSnackbar snackbar;
    @FXML
    private JFXTextField usernameField;
    @FXML
    private JFXPasswordField passwordField;

    private static Stage stage;

    public void run(Stage stage) throws Exception {
        RegistrationMenuController.stage = stage;
        this.start(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/fxml/RegistrationMenu.fxml"));
        primaryStage.setTitle("Yu-Gi-Oh!");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public void loginButtonOnAction() throws Exception {
       new LoginMenuController().run(stage);
    }

    public void register() {
        Label response = new Label();
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || nicknameField.getText().isEmpty()) {
            response.setText("please enter your username, password and nickname");
            response.getStyleClass().add("error-snackbar");
        } else {
            String command = "--username " + usernameField.getText() + " --password " + passwordField.getText() + " --nickname " + nicknameField.getText();
            Request.setCommandTag(CommandTags.REGISTER);
            Request.extractData(command);
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
        snackbar.enqueue(new JFXSnackbar.SnackbarEvent(response, Duration.seconds(2), null));
    }


}
