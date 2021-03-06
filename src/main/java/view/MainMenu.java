package view;

import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class MainMenu extends Menu {

    public void run() {
        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {

            setCurrentMenu(Menus.MAIN_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))            // menu enter
                changeMenu(command.substring(11));
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))     // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.LOGOUT.getLabel()))
                new RegistrationMenu().run();
            else Console.print(Responses.INVALID_COMMAND.getLabel());
        }
    }

    private void changeMenu(String destinationMenu) {

        if (destinationMenu.equals(Menus.SCOREBOARD_MENU.getLabel())) {
            new ScoreboardMenu().run();
        } else if (destinationMenu.equals(Menus.PROFILE_MENU.getLabel())) {
            new ProfileMenu().run();
        } else if (destinationMenu.equals(Menus.DECK_MENU.getLabel())) {
            new DeckMenu().run();
        } else if (destinationMenu.equals(Menus.SHOP_MENU.getLabel())) {
            new ShopMenu().run();
        }else if (destinationMenu.equals(Menus.DUEL_MENU.getLabel())) {
            new DuelMenu().run();
        } else if (destinationMenu.equals(Menus.IMPORT_EXPORT_MENU.getLabel())) {
            new ImportExportMenu().run();
        }
    }
}
