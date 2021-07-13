package server.controller;

import Controller.enums.CommandTags;
import com.google.gson.Gson;
import model.Message;
import model.User;
import org.json.JSONObject;
import server.ServerResponse;

import java.util.Objects;

public class ServerChatController {

    public static void processCommand(JSONObject request, ServerResponse response, User user) {

        switch (Objects.requireNonNull(CommandTags.fromValue(request.getString("command")))) {
            case UPDATE_CHAT:
                response.add("allMessages", new Gson().toJson(Message.getMessages()));
                break;
            case SEND_MESSAGE:
                response.success();
                response.addMessage("send successfully");
                new Message(user, request.getString("message"), request.has("repliedTo") ? request.getInt("repliedTo") : -1);
                break;
            case EDIT_MESSAGE:
                Message.getMessageById(request.getInt("messageId")).edit(request.getString("editedMessage"));
                break;
            case REMOVE_MESSAGE:
                Message.removeMessage(request.getInt("messageId"));
                break;

        }
    }
}
