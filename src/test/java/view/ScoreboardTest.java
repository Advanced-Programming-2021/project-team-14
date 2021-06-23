package view;

import Controller.ScoreboardController;
import model.User;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.enums.CommandTags;
import view.enums.Menus;

import java.util.ArrayList;

public class ScoreboardTest {
    @Test
    public void sortUsersAlphabetically(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        ScoreboardController.sortUsers(users);
        Assertions.assertEquals(first, users.get(0));
        Assertions.assertEquals(second, users.get(1));
    }
    @Test
    public void sortUsersBaseOnScore(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        first.increaseScore(1000);
        second.increaseScore(2000);
        ScoreboardController.sortUsers(users);
        Assertions.assertEquals(first, users.get(1));
        Assertions.assertEquals(second, users.get(0));
    }
    @Test
    public void rankUsersWithSameScores(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        ScoreboardController.rankUsers(users);
        Assertions.assertEquals(1, first.getRank());
        Assertions.assertEquals(1, second.getRank());
    }
    @Test
    public void rankUsersWithDifferentScores(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        first.increaseScore(1000);
        second.increaseScore(2000);
        ScoreboardController.sortUsers(users);
        ScoreboardController.rankUsers(users);
        Assertions.assertEquals(2, first.getRank());
        Assertions.assertEquals(1, second.getRank());
    }
    @Test
    public void stringifyUsers(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        String scoreboard = ScoreboardController.stringify(users);
        Assertions.assertEquals(first.toString() + "\n" + second.toString(), scoreboard);
    }

    @Test
    public void getScoreboardByCommand(){
        ArrayList<User> users = new ArrayList<>();
        User first = new User("name1", "pass", "nick1");
        User second = new User("name2", "pass", "nick2");
        users.add(first);
        users.add(second);
        first.increaseScore(1000);
        second.increaseScore(500);
        Request.addData("view", Menus.SCOREBOARD_MENU.getLabel());
        Request.setCommandTag(CommandTags.SHOW_SCOREBOARD);
        Request.send();
        String actual = "1   | nick1                : 1000\n" +
                        "2   | nick2                : 500\n" +
                        "3   | nickname             : 0\n" +
                        "3   | nicknameCreate       : 0\n" +
                        "3   | profileNickname      : 0";
        Assertions.assertEquals(actual, Request.getMessage());
    }
}
