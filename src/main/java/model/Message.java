package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Message{
    private static HashMap<Integer, Message> messages;
    private int id;
    private static int messagesIdCounter;

    static {
        messages = new HashMap<>();
        messagesIdCounter = 1000;
    }

    private String message;
    private String username;
    private boolean edited;
    private String time;


    public Message(User user, String message){
        this.time = getCurrentTime();
        this.edited = false;
        this.username = user.getUsername();
        this.message = message;
        this.id = messagesIdCounter;
        addToMessages(messagesIdCounter++, this);
    }

    public String getTime() {
        return time;
    }

    private static String getCurrentTime(){
        Date date = new Date();
        return date.getHours() + ":" + date.getMinutes();
    }

    private synchronized static void addToMessages(int id, Message message) {
        messages.put(id, message);
    }

    public static void removeMessage(int messageId) {
        messages.remove(messageId);
    }

    public int getId() {
        return id;
    }

    public synchronized static ArrayList<Message> getMessages() {
        return new ArrayList<>(messages.values());
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }

    public boolean isEqualWith(Message message) {
        return message.getMessage().equals(getMessage());
    }

    public static Message getMessageById(int id){
        return messages.get(id);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void edit(String editedMessage) {
        setMessage(editedMessage);
        edited = true;
    }

    public boolean isEdited() {
        return edited;
    }
}
