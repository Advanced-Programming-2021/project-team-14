package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class ProfileMenu extends Menu {

    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.PROFILE_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());

            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);

            else if (command.matches(Regexes.CHANGE_PROFILE_NICKNAME.getLabel())) {       // change profile nickname

                Request.setCommandTag(CommandTags.CHANGE_NICKNAME);
                Request.extractData(command);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.CHANGE_PROFILE_PASSWORD.getLabel())) {     // change profile password

                Request.setCommandTag(CommandTags.CHANGE_PASSWORD);
                Request.extractData(command);
                Request.send();
                Console.print(Request.getResponse());
            } else Console.print(Responses.INVALID_COMMAND.getLabel());
        }

    }
}
