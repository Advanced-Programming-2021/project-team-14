package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class ImportExportMenu extends Menu {
    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.IMPORT_EXPORT_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());

            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);

            else if (command.matches(Regexes.IMPORT.getLabel())) {              // import card
                Request.setCommandTag(CommandTags.IMPORT);
                Request.addDataToRequest(Regexes.IMPORT.getLabel(), command, "cardName");
                Request.send();
                Console.print(Request.getMessage());

            } else if (command.matches(Regexes.EXPORT.getLabel())) {            // export card
                Request.setCommandTag(CommandTags.EXPORT);
                Request.addDataToRequest(Regexes.EXPORT.getLabel(), command, "cardName");
                Request.send();
                Console.print(Request.getMessage());

            } else Console.print(Responses.INVALID_COMMAND.getLabel());
        }
    }
}
