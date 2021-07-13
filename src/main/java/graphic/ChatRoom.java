package graphic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import graphic.component.Bubble;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.util.Duration;
import model.Message;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class ChatRoom {

    public JFXTextArea textField;
    public JFXListView<Bubble> chatList;
    public JFXButton doneButton, cancelButton;
    public JFXButton sendMessageButton;
    private ArrayList<Message> messages;
    private int clickedCellId;


    @FXML
    public void initialize() {
        setVisibility(true);
        updateRequest();
        messages = new Gson().fromJson(Request.getResponse().getString("allMessages"), new TypeToken<ArrayList<Message>>() {
        }.getType());
        messages.forEach(this::addCell);
        new Thread(() -> {
            System.out.println(Menu.getData());
            while (true) {
                update();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @FXML
    private void update() {
        Platform.runLater(() -> {
            updateRequest();
            ArrayList<Message> newMessages = new Gson().fromJson(Request.getResponse().getString("allMessages"), new TypeToken<ArrayList<Message>>() {
            }.getType());

            for (int i = 0; i < newMessages.size(); i++) {
                if (i > messages.size() - 1) {
                    addCell(newMessages.get(i));
                } else {
                        Message history = messages.get(i);
                    if (newMessages.get(i).getId() != (messages.get(i).getId())) {
                        System.out.println("there must be a deleting message, not? : " + history.getMessage() + " " + history.getUsername() + " " + history.getUsername());
                        removeCellByMessage(messages.get(i));
                        messages.remove(messages.get(i));
                    }
                    history = messages.get(i);
                    if (!newMessages.get(i).getMessage().equals(history.getMessage())) {

                        System.out.println("updating the message:" + history.getMessage() + " -> " + newMessages.get(i).getMessage());
                        updateCell(newMessages.get(i), i);
                    }
                }
            }
            messages = newMessages;

        });

    }

    private void removeCellByMessage(Message message) {
        System.out.println("removing");
        for (int i = 0; i < chatList.getItems().size(); i++) {

            if (chatList.getItems().get(i).getText().equals(message.getMessage())) {
                chatList.getItems().remove(chatList.getItems().get(i));
                break;
            }
        }
    }

    private void updateCell(Message message, int index) {
        chatList.getItems().get(index).update(message.getMessage());
    }

    private void editMessageRequest(String editedText, int id) {
        Request.setCommandTag(CommandTags.EDIT_MESSAGE);
        Request.addData("view", Menus.CHAT.getLabel());
        Request.addData("messageId", String.valueOf(id));
        Request.addData("editedMessage", editedText);
        Request.send();
        setVisibility(true);
    }

    private void updateRequest() {
        Request.setCommandTag(CommandTags.UPDATE_CHAT);
        Request.addData("view", Menus.CHAT.getLabel());
        Request.send();
    }

    public void sendMessageRequest(ActionEvent actionEvent) {
        Request.setCommandTag(CommandTags.SEND_MESSAGE);
        Request.addData("view", Menus.CHAT.getLabel());
        Request.addData("message", textField.getText());
        Request.send();
        textField.clear();
        update();

    }

    private void addCell(Message message) {
        Bubble bubble = new Bubble(message.getMessage(), message.getUsername().equals(Menu.currentUser.getUsername()), message.isEdited(), message.getTime());

        ContextMenu contextMenu = new ContextMenu();

        MenuItem edit = new MenuItem("Edit");
        MenuItem remove = new MenuItem("Remove");

        contextMenu.getItems().addAll(edit, remove);
        edit.setOnAction(e -> editState(message.getMessage(), message.getId()));
        remove.setOnAction(e -> removeMessage(message.getId()));

        bubble.setOnContextMenuRequested(event -> contextMenu.show(bubble, event.getScreenX(), event.getScreenY()));
        setAnimations(bubble);
        chatList.getItems().add(bubble);
        chatList.scrollTo(bubble);
    }

    private void setAnimations(Bubble bubble) {
        FadeTransition ft = new FadeTransition();
        ft.setNode(bubble);
        ft.setDuration(new Duration(250));
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(true);


        final ScaleTransition stSmall = new ScaleTransition();
        stSmall.setNode(bubble);
        stSmall.setFromX(0.5);
        stSmall.setFromY(0.5);
        stSmall.setToX(1.0);
        stSmall.setToY(1.0);
        stSmall.setDuration(new Duration(250));
        stSmall.play();
        ft.play();

        Medias.MESSAGE_NOTIFICATION.play(1);
    }

    private void removeMessage(int id) {
        Request.setCommandTag(CommandTags.REMOVE_MESSAGE);
        Request.addData("view", Menus.CHAT.getLabel());
        Request.addData("messageId", String.valueOf(id));
        Request.send();
    }

    private void editState(String message, int id) {
        textField.setText(message);
        setVisibility(false);
        clickedCellId = id;
    }

    @FXML
    public void doneEditing() {
        editMessageRequest(textField.getText(), clickedCellId);
        textField.clear();
    }

    @FXML
    public void cancelEditing() {
        setVisibility(true);
        textField.clear();
    }

    private void setVisibility(boolean isNormalState) {
        cancelButton.setVisible(!isNormalState);
        doneButton.setVisible(!isNormalState);
        sendMessageButton.setVisible(isNormalState);
    }

}
