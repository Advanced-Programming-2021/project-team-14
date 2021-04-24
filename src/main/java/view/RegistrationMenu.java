package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class RegistrationMenu extends Menu {
    public void run() {


        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.REGISTER_MENU);
            if (command.matches(Regexes.MENU_ENTER.getLabel()))     // enter other menus
                Console.print(Responses.MENU_ENTER_NOT_ALLOWED.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))  // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.CREATE_USER.getLabel())) {  // register ...

                Request.setCommandTag(CommandTags.REGISTER);
                Request.extractData(command);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.LOGIN_USER.getLabel())) {  // log in ...

                Request.setCommandTag(CommandTags.LOGIN);
                Request.extractData(command);
                Request.send();
                Console.print(Request.getResponse());
                if (Request.isSuccessful()) {
                    Request.getToken();
                    new MainMenu().run();
                }

            } else Console.print(Responses.INVALID_COMMAND.getLabel()); // invalid command

        }
    }
}