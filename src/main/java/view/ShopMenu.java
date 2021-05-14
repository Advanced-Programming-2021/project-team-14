package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class ShopMenu extends Menu {
    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.SHOP_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());

            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);

            else if (command.matches(Regexes.SHOP_BUY.getLabel())) {       // buy card
                Request.setCommandTag(CommandTags.BUY_CARD);
                Request.addDataToRequest(Regexes.SHOP_BUY.getLabel(), command, "cardName");
                Request.send();
                Console.print(Request.getMessage());

            } else if (command.matches(Regexes.SHOP_SHOW_ALL.getLabel())) {     // shop show all
                Request.setCommandTag(CommandTags.SHOP_SHOW_ALL);
                Request.send();
                Console.print(Request.getMessage());
            } else Console.print(Responses.INVALID_COMMAND.getLabel());
        }

    }


}
