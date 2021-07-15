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
import sample.MainGraphic;
import view.Console;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


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
        if (!currentUser.hasProfilePhoto()) {
            saveCircle(Color.color(Math.random(), Math.random(), Math.random()));
        }

        setProfilePhoto();
        if (currentUser.hasProfilePhoto())
            setProfileButton("-");
        else
            setProfileButton("+");
        currentProfileButton.toFront();
    }

    private void setProfileButton(String sign) {
        JFXButton profileButton = new JFXButton();
        profileButton.getStyleClass().add("profile_button");
        profileButton.setLayoutX(515);
        profileButton.setLayoutY(125);
        profileButton.setText(sign);
        mainPane.getChildren().add(profileButton);
        this.currentProfileButton = profileButton;
        profileButton.setOnAction(event -> sendChangeProfilePhotoRequest());
    }


    private void changeProfilePhotoView(String path) {
        if (!Request.isSuccessful()) {
            new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
        } else {
            new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());
            mainPane.getChildren().remove(currentProfilePhoto);

            if (!currentUser.hasProfilePhoto()) {
                Database.saveProfilePhoto(path, currentUser.getUsername());
                currentUser.setHasProfilePhoto(true);
                currentProfileButton.setText("-");
            } else {
                Database.removeProfilePhoto(currentUser.getUsername());
                currentUser.setHasProfilePhoto(false);
                currentProfileButton.setText("+");
                if (!currentUser.hasProfileCircle())
                    saveCircle(Color.color(Math.random(), Math.random(), Math.random()));
            }
            setProfilePhoto();
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
                BufferedImage rawImage = null;
                rawImage = Database.getBufferedImage(rawImage, f.getPath());
                String imageString = Database.convertImageToString(rawImage);
                Request.setCommandTag(CommandTags.SET_PROFILE_PHOTO);
                Request.addData("view", Menus.PROFILE_MENU.getLabel());
                Request.addData("image", imageString);
                Request.send();
                changeProfilePhotoView(f.getPath());

            }
        } else {
            Request.setCommandTag(CommandTags.REMOVE_PROFILE_PHOTO);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.send();
            changeProfilePhotoView(null);
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

    private void saveCircle(Color color) {
        BufferedImage rawImage = null;
        rawImage = Database.getBufferedImage(rawImage, "Resources\\ProfileImages\\aiPlayer.png");
        java.awt.Color awtColor = new java.awt.Color((float) color.getRed(),
                (float) color.getGreen(),
                (float) color.getBlue(),
                (float) color.getOpacity());

        Graphics2D graphics = rawImage.createGraphics();
        graphics.setColor(awtColor);
        graphics.fillRect(0, 0, rawImage.getWidth(), rawImage.getHeight());

        graphics.setFont(new Font("Arial", Font.TRUETYPE_FONT, 80));
        graphics.setColor(java.awt.Color.WHITE);
        graphics.drawString(currentUser.getUsername().substring(0, 1), rawImage.getWidth() / 2 - 20, rawImage.getHeight() / 2 + 20);

        graphics.dispose();

        String imageString = Database.convertImageToString(rawImage);
        Request.setCommandTag(CommandTags.SET_PROFILE_CIRCLE);
        Request.addData("view", Menus.PROFILE_MENU.getLabel());
        Request.addData("image", imageString);
        Request.send();

        if (Request.isSuccessful()) {
            Database.saveProfileCircle(rawImage, currentUser.getUsername());
            currentUser.setHasProfileCircle(true);
        } else
            new SnackBarComponent(Request.getMessage(), ResultState.ERROR);

    }

    private void setProfilePhoto() {
        ImageView profileImage = new ImageView();
        Image image = null;

        try {
            if (currentUser.hasProfilePhoto()) {
                image = Database.getProfilePhoto(currentUser.getUsername());
            } else
                image = Database.getProfilePhoto(currentUser.getUsername() + "_circle");
        } catch (Exception e) {
            Request.setCommandTag(CommandTags.GET_PROFILE_PHOTO);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.send();

            if (Request.isSuccessful())
                image = Database.convertStringToImage(Request.getMessage());
            else
                saveCircle(Color.color(Math.random(), Math.random(), Math.random()));
        }

        if (image == null)
            saveCircle(Color.color(Math.random(), Math.random(), Math.random()));

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
            String newPass = newPassField.getText();
            Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.addData("current", currentPassField.getText());
            Request.addData("new", newPass);
            Request.send();

            if (!Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
            } else {
                currentUser.changePassword(newPass);
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());
            }
            Console.print(Request.getMessage());
        }
    }

    public void changeNickname() {

        if (nicknameField.getText().isEmpty()) {
            new SnackBarComponent("please enter new nickname", ResultState.ERROR, getRoot());
        } else {
            String newNickname = nicknameField.getText();
            Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
            Request.addData("view", Menus.PROFILE_MENU.getLabel());
            Request.addData("new", newNickname);
            Request.send();

            if (!Request.isSuccessful()) {
                new SnackBarComponent(Request.getMessage(), ResultState.ERROR, getRoot());
                new Shake(nicknameField);
            } else {
                new SnackBarComponent(Request.getMessage(), ResultState.SUCCESS, getRoot());
                currentUser.changeNickname(newNickname);
            }
            Console.print(Request.getMessage());
        }
    }
}
