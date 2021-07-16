package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class ScoreboardMenu extends Menu {
    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.SCOREBOARD_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))    // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))  // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.SHOW_SCOREBOARD.getLabel())) { // show scoreboard
                Request.setCommandTag(CommandTags.SHOW_SCOREBOARD);
                Request.send();
                Console.print(Request.getMessage());
            } else Console.print(Responses.INVALID_COMMAND.getLabel()); // invalid command

        }
    }
}