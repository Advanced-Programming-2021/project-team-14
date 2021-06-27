package Controller;

import Controller.enums.Responses;
import model.Database;
import model.Strings;
import model.User;
import model.card.Card;
import model.game.Board;
import model.game.Phase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.Request;
import view.enums.CommandTags;
import view.enums.Menus;

import java.net.UnknownServiceException;


public class GamePlayControllerTest {


    private String option;
    private String area;


    @Test
    public void createDuelWrongRivalPlayer() {
        login("main");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rivaaaaal");
        Request.addData("rounds", "1");
        Request.send();
        Assertions.assertEquals(Strings.PLAYER_NOT_EXIST.getLabel(), Request.getMessage());
    }

    @Test
    public void createDuelWrongDuelRounds() {
        login("main");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rival");
        Request.addData("rounds", "6");
        Request.send();
        Assertions.assertEquals(Strings.NUMBER_OF_ROUNDS_NOT_SUPPORTED.getLabel(), Request.getMessage());
    }

    @Test
    public void setNotSelectedCard() {

        login("main");
        startDuel("rival");
        changePhase();
        setCard();
        Assertions.assertEquals("no card is selected yet", Request.getMessage());
    }

    @Test
    public void setMonsterSelectedCard() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        setCard();
        Assertions.assertEquals("set successfully", Request.getMessage());
    }

    @Test
    public void setMonsterError() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        summonAMonster();
        selectCard("--monster 1", "monster");
        setCard();
        Assertions.assertEquals("you can’t set this card", Request.getMessage());
    }

    @Test
    public void createDuelSuccessful() {
        login("main");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", "rival");
        Request.addData("rounds", "1");
        Request.send();
        Assertions.assertEquals(String.format(Strings.START_DUEL.getLabel(), "main", "rival"), Request.getMessage());
    }

    @Test
    public void selectHandCard() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand1", "hand");
        Assertions.assertEquals(Strings.CARD_SELECTED.getLabel(), Request.getMessage());
    }

    @Test
    public void deselectCard() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--field 1", "field");
        selectCard("--spell 1", "spell");
        selectCard("--hand 1", "hand");
        Request.setCommandTag(CommandTags.DESELECT);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals(Strings.CARD_DESELECTED.getLabel(), Request.getMessage());
    }

    @Test
    public void winGameCheat() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        Request.setCommandTag(CommandTags.WIN_GAME);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("Unexpectedly user AdminTest1 won!", Request.getMessage());
    }

    @Test
    public void playAI() {
        login("main");
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL_AI.getLabel());
        Request.addData("rounds", "1");
        Request.send();
        changePhase();
        selectCard("--hand 1", "hand");
        summonAMonster();
        changeSomePhases(5);
        Assertions.assertEquals("phase: STANDBY_PHASE", Request.getMessage());
    }

    @Test
    public void forceSelect() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        Request.setCommandTag(CommandTags.SELECT_FORCE);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.addData("card", "Spell Absorption");
        Request.send();
        Assertions.assertEquals("card added successfully", Request.getMessage());
    }

    @Test
    public void summon() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        summonAMonster();
        Assertions.assertEquals(Strings.SUMMON_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void ritualSummon() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        Request.setCommandTag(CommandTags.RITUAL_SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("there is no way you could ritual summon a monster", Request.getMessage());
    }

    @Test
    public void specialSummon() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        Request.setCommandTag(CommandTags.SPECIAL_SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("there is no way you could special summon a monster", Request.getMessage());
    }

    @Test
    public void summonError1() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        summonAMonster();
        Assertions.assertEquals("no card is selected yet", Request.getMessage());
    }

    @Test
    public void summonError2() {
        login("SpellTest");
        startDuel("TrapTest");
        changePhase();
        selectCard("--hand 1", "hand");
        summonAMonster();
        Assertions.assertEquals("you can’t summon this card", Request.getMessage());
    }

    @Test
    public void summonError3() {
        login("MonsterTest");
        startDuel("rival");
        selectCard("--hand 1", "hand");
        summonAMonster();
        Assertions.assertEquals("action not allowed in this phase", Request.getMessage());
    }

    @Test
    public void summonError4() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        for (int i = 0; i < 6; i++) {
            changeSomePhases(12);
            selectCard("--hand 1", "hand");
            summonAMonster();

        }
        Assertions.assertEquals("monster card zone is full", Request.getMessage());
    }

    @Test
    public void summonError5() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand 1", "hand");
        summonAMonster();
        selectCard("--monster 1", "monster");
        summonAMonster();
        Assertions.assertEquals("you can’t summon this card (should be in hand)", Request.getMessage());
    }

    @Test
    public void flipSummon() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();

        selectCard("--hand 1", "hand");
        setCard();

        changeSomePhases(12);

        selectCard("--monster 1", "monster");
        Request.setCommandTag(CommandTags.FLIP_SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals(Strings.FLIP_SUMMON_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void flipSummonError() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();

        selectCard("--hand 1", "hand");
        summon();

        changeSomePhases(12);

        selectCard("--monster 1", "monster");
        Request.setCommandTag(CommandTags.FLIP_SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals(Strings.CANNOT_FLIP_SUMMON_THIS_CARD.getLabel(), Request.getMessage());
    }

    @Test
    public void setSpellTest() {
        login("SpellTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand1", "hand");
        setCard();
        Assertions.assertEquals(Strings.SET_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    private void setCard() {
        Request.setCommandTag(CommandTags.SET);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
    }

    @Test
    public void increaseLifePoint() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        Request.setCommandTag(CommandTags.INCREASE_LIFE_POINT);
        Request.addData("amount", "1000");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals(Responses.INCREASE_LIFE_POINT.getLabel(), Request.getMessage());
    }

    @Test
    public void attack() {
        login("MonsterTest");
        startDuel("MonsterRival");
        attackAMonster();
        Assertions.assertEquals("both you and your opponent monster cards are destroyed and one receives damage", Request.getMessage());
    }

    @Test
    public void attackError() {
        login("MonsterTest");
        startDuel("MonsterRival");

        changePhase();

        selectCard("--hand1", "hand");
        summonAMonster();

        changeSomePhases(6);

        selectCard("--hand1", "hand");
        summonAMonster();

        changePhase();

        selectCard("--monster 1", "monster");

        Request.setCommandTag(CommandTags.ATTACK);
        Request.addData("to", "3");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("there is no card to attack here", Request.getMessage());
    }

    @Test
    public void effectTest() {
        login("MonsterTest");
        startDuel("SpellTestRival");
        changePhase();

        selectCard("--hand 1", "hand");
        summonAMonster();

        changeSomePhases(6);

        selectCard("--hand 1", "hand");
        setCard();

        changeSomePhases(12);
        selectCard("--spell 1", "spell");
        Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
        Request.addData("to", "1");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        Assertions.assertEquals(Strings.ACTIVATE_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void effectTest2() {
        login("MonsterTest");
        startDuel("TrapTest");
        changePhase();

        selectCard("--hand 1", "hand");
        summonAMonster();

        changeSomePhases(6);

        selectCard("--hand 1", "hand");
        setCard();

        changeSomePhases(12);

        selectCard("--hand 1", "hand");
        setCard();
        selectCard("--hand 1", "hand");
        setCard();
        selectCard("--hand 1", "hand");
        setCard();
        selectCard("--spell 1", "spell");
        Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
        Request.addData("to", "1");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        Assertions.assertEquals(Strings.ACTIVATE_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void tributeMonster() {
        login("TributeTest");
        startDuel("MonsterTest");
        changePhase();

        selectCard("--hand 1", "hand");
        summonAMonster();

        changeSomePhases(12);

        selectCard("--hand 1", "hand");
        Request.addData("data", "1");
        summonAMonster();

        Assertions.assertEquals(Strings.SUMMON_SUCCESSFULLY.getLabel(), Request.getMessage());
    }

    @Test
    public void directAttack() {
        login("MonsterTest");
        startDuel("MonsterRival");
        changeSomePhases(7);
        selectCard("--hand 1", "hand");
        summonAMonster();
        changePhase();
        selectCard("--monster 1", "monster");
        Request.setCommandTag(CommandTags.DIRECT_ATTACK);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("you opponent receives 300 battle damage", Request.getMessage());
    }

    @Test
    public void winTheGame() {
        login("MonsterTest");
        startDuel("MonsterRival");
        changePhase();
        directAttackForWin();
        for (int i=0; i<26;i++) {
            changeSomePhases(11);
            directAttackForWin();
        }
        Assertions.assertEquals("you opponent receives 300 battle damage\n" +
                "user AdminTest1 won Duel with score 1000 and user AdminTest1 lost with score 0!\n", Request.getMessage());
    }

    @Test
    public void showGraveYard() {
        login("MonsterTest");
        startDuel("MonsterRival");
        attackAMonster();
        Request.setCommandTag(CommandTags.SHOW_GRAVEYARD);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
        Assertions.assertEquals("1. Marshmallon : Cannot be destroyed by battle. After damage calculation, if this card was attacked, and was face-down at the start of the Damage Step: The attacking player takes 1000 damage.\n", Request.getMessage());
    }

    @Test
    public void setPositionError() {
        login("MonsterRival");
        startDuel("MonsterTest");
        changePhase();
        selectCard("--hand1", "hand");
        summonAMonster();

        selectCard("--monster 1", "monster");

        Request.setCommandTag(CommandTags.SET_POSITION);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.addData("position", "attack");
        Request.send();
        Assertions.assertEquals("this card is already in the wanted position", Request.getMessage());
    }

    @Test
    public void setPosition() {
        login("MonsterTest");
        startDuel("rival");
        changePhase();
        selectCard("--hand1", "hand");
        summonAMonster();
        selectCard("--monster 1", "monster");
        Request.setCommandTag(CommandTags.SET_POSITION);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.addData("position", "defence");
        Request.send();
        Assertions.assertEquals("monster position changed successfully", Request.getMessage());
    }

    @Test
    public void showSelectedMonsterCard() {
        login("MonsterTest");
        startDuel("rival");
        selectCard("--hand1", "hand");
        changePhase();
        Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        String showCard = Card.getCardByName("Marshmallon").show();
        Assertions.assertEquals(showCard, Request.getMessage());
    }

    @Test
    public void showCard() {
        login("MonsterTest");
        startDuel("rival");
        selectCard("--hand1", "hand");
        changePhase();
        Request.setCommandTag(CommandTags.SHOW_CARD);
        Request.addData("card", "Wattaildragon");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        String showCard = Card.getCardByName("Wattaildragon").show();
        Assertions.assertEquals(showCard, Request.getMessage());
    }

    @Test
    public void showSelectedSpellCard() {
        login("SpellTest");
        startDuel("rival");
        selectCard("--hand1", "hand");
        changePhase();
        Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();

        String showCard = Card.getCardByName("Spell Absorption").show();
        Assertions.assertEquals(showCard, Request.getMessage());
    }

    @Test
    public void notExistDeck() {
        login("NotExistDeckUser");
        startDuel("main");
        Assertions.assertEquals(String.format(Strings.NO_ACTIVE_DECK.getLabel(), "NotExistDeckUser"), Request.getMessage());
    }

    @Test
    public void notExistRivalDeck() {
        login("main");
        startDuel("NotExistDeckUser");
        Assertions.assertEquals(String.format(Strings.NO_ACTIVE_DECK.getLabel(), "NotExistDeckUser"), Request.getMessage());
    }

    @Test
    public void sameSecondPlayer() {
        login("main");
        startDuel("main");
        Assertions.assertEquals(Strings.SAME_SECOND_PLAYER.getLabel(), Request.getMessage());
    }

    private void setMain1Phase() {
        startDuel("rival");
        changePhase();
        Assertions.assertEquals("phase: " + Phase.MAIN_PHASE_1, Request.getMessage());
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

    private void directAttackForWin() {
        selectCard("--hand 1", "hand");
        setCard();
        changePhase();
        selectCard("--monster 1", "monster");
        Request.setCommandTag(CommandTags.DIRECT_ATTACK);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
    }

    private void attackAMonster() {
        changePhase();

        selectCard("--hand1", "hand");
        summonAMonster();

        changeSomePhases(6);

        selectCard("--hand1", "hand");
        summonAMonster();

        changePhase();

        selectCard("--monster 1", "monster");

        Request.setCommandTag(CommandTags.ATTACK);
        Request.addData("to", "1");
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
    }

    private void changeSomePhases(int count) {
        for (int i = 0; i < count; i++) {
            changePhase();
        }
    }

    private void changePhase() {
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.addData("command", CommandTags.NEXT_PHASE.getLabel());
        Request.send();
    }

    private void startDuel(String rival) {
        Request.addData("view", Menus.DUEL_MENU.getLabel());
        Request.addData("command", CommandTags.START_DUEL.getLabel());
        Request.addData("second-player", rival);
        Request.addData("rounds", "1");
        Request.send();
    }

    private String selectCard(String option, String area) {
        Request.setCommandTag(CommandTags.SELECT);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.setOption(option, Strings.OPPONENT_OPTION.getLabel());
        Request.addData("area", area);
        Request.addData("position", "1");
        Request.send();
        String response = " ";
        try {
            response = Request.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private void summonAMonster() {
        Request.setCommandTag(CommandTags.SUMMON);
        Request.addData("view", Menus.GAMEPLAY_MENU.getLabel());
        Request.send();
    }

}
