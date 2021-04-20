package view;

import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class MainMenu extends Menu {

    public void run() {
        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.MAIN_MENU);
            response = "invalid command";

            if (command.matches(Regexes.MENU_ENTER.getLabel())) // menu enter
                changeMenu(command.substring(11));
            else if (command.matches(Regexes.MENU_CURRENT.getLabel())) // show current menu
                Console.print(currentMenu);
            else Console.print(Responses.INVALID_COMMAND.getLabel());
        }
    }

    private void changeMenu(String destinationMenu) {
        System.out.println(destinationMenu);
        if (destinationMenu.equals(Menus.SCOREBOARD_MENU.getLabel()))
            new ScoreboardMenu().run();
        else Console.print(Responses.INVALID_COMMAND.getLabel());
    }
}
