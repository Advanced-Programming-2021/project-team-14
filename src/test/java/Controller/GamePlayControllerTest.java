package Controller;

import Controller.enums.Responses;
import model.Database;
import model.Strings;
import model.User;
import model.card.Card;
import model.game.Board;
import model.game.Phase;
import model.game.Player;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.RegistrationMenu;
import Controller.RegistrationTest;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GamePlayControllerTest {


    private void login(String username, String password, String nickname) {
        Database.prepareDatabase();
        Request.addData("view", Menus.REGISTER_MENU.getLabel());
        Request.setCommandTag(CommandTags.LOGIN);
        Request.addData("username", username);
        Request.addData("password", password);
        Request.addData("nickname", nickname);
        Request.send();

        if (Request.isSuccessful())
            Request.getToken();
    }

    @Test
    public void createDuelWrongRivalPlayer() {
        login("main", "123", "admin1");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rivaaaaal");
        Request.addData("rounds", "1");
        Request.send();
        Assertions.assertEquals(Strings.PLAYER_NOT_EXIST.getLabel(), Request.getMessage());
    }

    @Test
    public void createDuelWrongDuelRounds() {
        login("main", "123", "admin1");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rival");
        Request.addData("rounds", "6");
        Request.send();
        Assertions.assertEquals(Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel(), Request.getMessage());
    }

    @Test
    public void setNotSelectedCard() {

        login("main", "123", "admin1");
        startDuel();
        setMain1Phase();
        Request.setCommandTag(CommandTags.SET);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("no card is selected yet", Request.getMessage());
    }

    @Test
    public void setMonsterSelectedCard() {
        login("MonsterTest", "123", "AdminTest");
        startDuel();
        setMain1Phase();
        selectHandCard();
        Request.setCommandTag(CommandTags.SET);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("set successfully", Request.getMessage());
    }

    @Test
    public void createDuelSuccessful() {
        login("main", "123", "admin1");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rival");
        Request.addData("rounds", "1");
        Request.send();
        Assertions.assertEquals(String.format(Strings.START_DUEL.getLabel(), "main", "rival"), Request.getMessage());
    }

    @Test
    public void selectHandCard() {
        login("MonsterTest", "123", "AdminTest");
        startDuel();
        setMain1Phase();
        Request.setCommandTag(CommandTags.SELECT);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.setOption("--hand 1", Strings.OPPONENT_OPTION.getLabel());
        Request.addData("area", "hand");
        Request.addData("position", "1");
        Request.send();
        Assertions.assertEquals(Strings.CARD_SELECTED.getLabel(), Request.getMessage());
    }


    @Test
    public void summon() {
        summonAMonster();
        Assertions.assertEquals(Strings.SUMMON_SUCCESSFULLY.getLabel(), Request.getMessage());
    }



//    @Test
//    public void setSpell() {
//        login("main", "123", "admin1");
//        startDuel();
//        setMain1Phase();
//        selectHandCard();
//        Request.setCommandTag(CommandTags.SET);
//        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
//        Request.send();
//        Assertions.assertEquals(Strings.CARD_SELECTED.getLabel(), Request.getMessage());
//    }

//    @Test
//    public void attack() {
//        login("MonsterTest", "123", "AdminTest");
//        startDuel();
//        setMain1Phase();
//        selectHandCard();
//        summonAMonster();
//        Request.setCommandTag(CommandTags.ATTACK);
//        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
//        Request.send();
//        Assertions.assertEquals(Strings.CARD_SELECTED.getLabel(), Request.getMessage());
//    }


    @Test
    public void showSelectedMonsterCard() {
        login("MonsterTest", "123", "AdminTest");
        setMain1Phase();
        selectHandCard();

        Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        String showCard = Card.getCardByName("Marshmallon").show();
        Assertions.assertEquals(showCard, Request.getMessage());
    }


//    @Test
//    public void showSelectedCard() {
//        login("MonsterTest", "123", "AdminTest");
//        startDuel();
//        setMain1Phase();
//        selectHandCard();
//
//        Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
//        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
//        Request.send();
//
//        StringBuilder response = new StringBuilder(Request.getMessage());
//        String showCard = "";
//        if (response.substring(46, 76).trim().equals("Monster")) {
//            showCard = Card.getCardByName(response.substring(162, 190).trim()).show();
//        } else {
//            showCard = Card.getCardByName(response.substring(124, 154).trim()).show();
//        }
//        Assertions.assertEquals(showCard, response.toString());
//    }


    @Test
    public void setMain1Phase() {
        startDuel();
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.addData("command", CommandTags.NEXT_PHASE.getLabel());
        Request.send();
        Assertions.assertEquals("phase: " + Phase.MAIN_PHASE_1, Request.getMessage());
    }


    private void startDuel() {
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rival");
        Request.addData("rounds", "1");
        Request.send();
    }

    private void summonAMonster() {
        login("MonsterTest", "123", "AdminTest");
        startDuel();
        setMain1Phase();
        selectHandCard();
        Request.setCommandTag(CommandTags.SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
    }


}
