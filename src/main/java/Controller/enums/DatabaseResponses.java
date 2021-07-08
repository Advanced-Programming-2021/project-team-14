package Controller.enums;

import com.sun.net.httpserver.Authenticator;

public enum DatabaseResponses {
    NOT_EXIST_ERROR("NotExistError"),
    BAD_FORMAT_ERROR("BadFormatError"),
    CARD_ALREADY_EXIST("card already exist"),
    SAVE_ERROR("SaveError"),
    SUCCESSFUL("Successful"),
    SORRY("Sorry, there was a problem with your request. Please try later."),
    BAD_FORMAT_RESPONSE("Sorry, there was a problem with file format."),
    IMPORT("%s imports successfully!"),
    EXPORT("%s exports successfully!");

    public final String label;

    DatabaseResponses(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
