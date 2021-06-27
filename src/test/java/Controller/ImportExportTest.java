package Controller;

import Controller.enums.DatabaseResponses;
import Controller.enums.Responses;
import model.Database;
import model.card.Monster;
import model.card.enums.Attribute;
import model.card.enums.MonsterType;
import model.card.enums.Property;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

public class ImportExportTest {



    @BeforeEach
    @Test
    public void exportCard(){
        login("rival");
        Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
        Request.setCommandTag(CommandTags.EXPORT);
        Monster newCard1 = new Monster("newCard1", 1, Attribute.WIND, MonsterType.FAIRY, Property.NORMAL,1000, 1000, "new card!",1000, null);
        Request.addData("token", "rival");
        Request.addData("cardName", "newCard1");
        Request.send();
        Assertions.assertEquals(String.format(DatabaseResponses.EXPORT.getLabel(), "newCard1"), Request.getMessage());
    }

   @Test
    public void cardNoExistExport(){
        login("rival");
        Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
        Request.setCommandTag(CommandTags.EXPORT);
          Request.addData("token", "rival");
        Request.addData("cardName", "newCard2");
        Request.send();
        Assertions.assertEquals(String.format(Responses.CARD_NOT_EXIST.getLabel(), "newCard2"), Request.getMessage());
    }

   @Test
    public void cardNoExistImport(){
        login("rival");
        Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
        Request.setCommandTag(CommandTags.IMPORT);
          Request.addData("token", "rival");
        Request.addData("cardName", "newCard2");
        Request.send();
        Assertions.assertEquals(String.format(Responses.CARD_NOT_EXIST.getLabel(), "newCard2"), Request.getMessage());
    }
   @Test
    public void importCard(){
        login("rival");
        Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
        Request.setCommandTag(CommandTags.IMPORT);
          Request.addData("token", "rival");
        Request.addData("cardName", "newCard1");
        Request.send();
        Assertions.assertEquals(String.format(DatabaseResponses.IMPORT.getLabel(), "newCard1"), Request.getMessage());
    }

   @Test
    public void badFormatImportCard(){
        login("rival");
        Request.addData("view", Menus.IMPORT_EXPORT_MENU.getLabel());
        Request.setCommandTag(CommandTags.IMPORT);
          Request.addData("token", "rival");
        Request.addData("cardName", "badFormat");
        Request.send();
        Assertions.assertEquals(DatabaseResponses.BAD_FORMAT_RESPONSE.getLabel(), Request.getMessage());
    }



    private void login(String username) {
        Database.prepareDatabase();
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.addData("username", username);
        Request.addData("password", "123");
        Request.send();

        if (Request.isSuccessful())
            Request.getToken();
    }
}
