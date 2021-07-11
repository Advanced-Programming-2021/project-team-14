package graphic;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import graphic.animation.Shake;
import graphic.component.ResultState;
import graphic.component.SnackBarComponent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import model.Database;
import model.card.enums.CardType;
import sample.MainGraphic;
import view.Console;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ProfileMenu extends Menu {


    public Label usernameLabel;
    public Label nicknameLabel;
    public JFXTextField nicknameField;
    public JFXTextField currentPassField;
    public JFXTextField newPassField;
    public HBox userDataHBox;
    public Label scoreValue;
    public Label cashValue;
    public Label gamesPlayedValue;
    public VBox userDataVBox;
    public AnchorPane mainPane;

    private JFXButton currentProfileButton;
    private Circle currentCircle;
    private Label currentUsername;
    private ImageView currentProfilePhoto;

    @FXML
    public void initialize() {
        setProfile();
    }

    private void setProfile() {
        setProfilePic();
        usernameLabel.setText(currentUser.getUsername());
        nicknameLabel.setText(currentUser.getNickname());
        scoreValue.setText(String.valueOf(currentUser.getScore()));
        cashValue.setText(String.valueOf(currentUser.getWallet().getCash()));
        gamesPlayedValue.setText(String.valueOf(currentUser.getGamesPlayed()));
    }

    private void setProfilePic() {
        if (currentUser.hasProfilePhoto()) {
            try {
                setProfilePhoto();
                setProfileButton("-");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                setProfileCircle();
                setProfileButton("+");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setProfileButton(String sign) {
        JFXButton profileButton = new JFXButton();
        profileButton.getStyleClass().add("profile_button");
        profileButton.setLayoutX(515);
        profileButton.setLayoutY(125);
        profileButton.setText(sign);
        mainPane.getChildren().add(profileButton);
        this.currentProfileButton = profileButton;

        profileButton.setOnAction(event -> profileButtonOnAction());
    }

    private void profileButtonOnAction() {
        sendChangeProfilePhotoRequest();

    }

    private void changeProfilePhotoView() {
        if (!Request.isSuccessful()) {
            new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
        } else {
            new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());
            if (currentUser.hasProfilePhoto()) {
                mainPane.getChildren().remove(currentCircle);
                mainPane.getChildren().remove(currentUsername);
                setProfilePhoto();
                currentProfileButton.setText("-");
            } else {
                mainPane.getChildren().remove(currentProfilePhoto);
                setProfileCircle();
                currentProfileButton.setText("+");
            }
            currentProfileButton.toFront();
        }
    }

    private void sendChangeProfilePhotoRequest() {
        if (!currentUser.hasProfilePhoto()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Image");
            setFilter(fileChooser);
            File f = fileChooser.showOpenDialog(MainGraphic.getStage());
            if (f != null) {
                Request.setCommandTag(CommandTags.SET_PROFILE_PHOTO);
                Request.addData("view", Menus.PROFILE_MENU.getLabel());
                Request.addData("path", f.getPath());
                Request.send();
                changeProfilePhotoView();
            }
        } else {
            Request.setCommandTag(CommandTags.REMOVE_PROFILE_PHOTO);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.send();
            changeProfilePhotoView();
        }

    }

    private void setFilter(FileChooser fileChooser) {
        //Set extension filter
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
    }

    private void setProfileCircle() {
        Circle circle = new Circle(490, 110, 50);
        circle.getStyleClass().add("circle");
        Color color = Color.color(Math.random(), Math.random(), Math.random());
        circle.setFill(color);
        Label username = new Label(currentUser.getUsername().substring(0, 1));
        username.setLayoutY(60);
        username.setLayoutX(440);
        username.getStyleClass().add("profile_username");
        this.currentCircle = circle;
        this.currentUsername = username;
        mainPane.getChildren().add(circle);
        mainPane.getChildren().add(username);
        saveCircle(color);

    }


    private void saveCircle(Color color) {
        BufferedImage rawImage = null;

        rawImage = Database.getMainProfileBufferedImage(rawImage);

        java.awt.Color awtColor = new java.awt.Color((float) color.getRed(),
                (float) color.getGreen(),
                (float) color.getBlue(),
                (float) color.getOpacity());

        Graphics2D graphics = rawImage.createGraphics();
        graphics.setColor(awtColor);
        graphics.fillRect(0, 0, rawImage.getWidth(), rawImage.getHeight());

        graphics.setFont(new Font("Arial", Font.TRUETYPE_FONT, 80));
        graphics.setColor(java.awt.Color.WHITE);
        graphics.drawString(currentUser.getUsername().substring(0, 1), rawImage.getWidth()/2-20,rawImage.getHeight()/2+20);

        graphics.dispose();

        Database.saveProfileCircle(rawImage, currentUser.getUsername());
    }



    private void setProfilePhoto() {
        ImageView profileImage = new ImageView();
        Image image = Database.getProfilePhoto(currentUser.getUsername());
        profileImage.setImage(image);
        profileImage.setFitHeight(100);
        profileImage.setFitWidth(100);
        profileImage.setX(440);
        profileImage.setY(60);
        profileImage.setClip(new Circle(490, 110, 50));
        this.currentProfilePhoto = profileImage;
        mainPane.getChildren().add(profileImage);
    }

    public void changePassword() {
        if (currentPassField.getText().isEmpty() || newPassField.getText().isEmpty()) {
            new SnackBarComponent("please enter current and new password", ResultState.ERROR, getRoot());
        } else {
            Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.addData("current", currentPassField.getText());
            Request.addData("new", newPassField.getText());
            Request.send();

            if (!Request.isSuccessful()){
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
            }
            else
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());

            Console.print(Request.getMessage());
        }
    }

    public void changeNickname() {

        if (nicknameField.getText().isEmpty()) {
            new SnackBarComponent("please enter new nickname", ResultState.ERROR, getRoot());
        } else {
            Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.addData("new", nicknameField.getText());
            Request.send();

            if (!Request.isSuccessful()){
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
                new Shake(nicknameField);
            }
            else
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());

            Console.print(Request.getMessage());
        }
    }
}
