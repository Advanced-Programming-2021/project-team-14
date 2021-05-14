package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class DuelMenu extends Menu{

    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.DUEL_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.START_DUEL.getLabel())){
                Request.setCommandTag(CommandTags.START_DUEL);
                Request.extractData(command);
                Request.setOption(command, Strings.NEW_OPTION.getLabel());
                Request.send();
                if (Request.isSuccessful())
                     new GamePlayMenu().run();
            }

            Console.print(Request.getResponse());
        }

    }

}
