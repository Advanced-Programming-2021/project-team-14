package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class RegistrationMenu extends Menu {
    public void run() {
        setCurrentMenu(Menus.REGISTER_MENU);

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())){
            String response = "invalid command";

            if (command.matches(Regexes.MENU_ENTER.getLabel()))
                response = Responses.MENU_ENTER_NOT_ALLOWED.getLabel();
            if (command.matches(Regexes.MENU_CURRENT.getLabel()))
                response = currentMenu;

            if (command.matches(Regexes.CREATE_USER.getLabel())){
                Request.setCommandTag(CommandTags.REGISTER);
                Request.extractData(command);
                response = Request.send();
            }
            if (command.matches(Regexes.LOGIN_USER.getLabel())){
                Request.setCommandTag(CommandTags.LOGIN);
                Request.extractData(command);
                response = Request.send();
            }

            Console.print(response);
        }
    }
}