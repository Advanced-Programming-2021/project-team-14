package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class DuelMenu extends Menu {

    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.DUEL_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.START_DUEL.getLabel())) {
                Request.setCommandTag(CommandTags.START_DUEL);
                if (command.contains("--rou ") && command.contains("--sec-p "))
                    Request.addShortData(command);
                else
                    Request.extractData(command);

                Request.setOption(command, Strings.NEW_OPTION.getLabel());
                Request.send();
                if (Request.isSuccessful()) {
                    Console.print(Request.getMessage());
                    new GamePlayMenu().run();
                } else
                    Console.print(Request.getMessage());
            } else if (command.matches(Regexes.START_DUEL_AI.getLabel())) {
                Request.setCommandTag(CommandTags.START_DUEL_AI);
                if (command.contains("--rou "))
                    Request.addShortData(command);
                else
                    Request.extractData(command);

                Request.setOption(command, Strings.NEW_OPTION.getLabel());
                Request.setOption(command, Strings.AI.getLabel());
                Request.send();
                if (Request.isSuccessful()) {
                    Console.print(Request.getMessage());
                    new GamePlayMenu().run();
                } else
                    Console.print(Request.getMessage());
            }
        }
    }

}
