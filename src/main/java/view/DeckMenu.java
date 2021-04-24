package view;

import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeckMenu extends Menu {

    public void run() {


        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.DECK_MENU);

            if (command.matches(Regexes.MENU_ENTER.getLabel()))             // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))      // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.CREATE_DECK.getLabel())) {     // create deck ...

                Request.setCommandTag(CommandTags.CREATE_DECK);
                addDataToRequest(Regexes.CREATE_DECK.getLabel(), command, "deck");
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.DELETE_DECK.getLabel())) {   // delete deck ...

                Request.setCommandTag(CommandTags.DELETE_DECK);
                addDataToRequest(Regexes.DELETE_DECK.getLabel(), command, "deck");
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.ACTIVATE_DECK.getLabel())) {  // activate deck ...

                Request.setCommandTag(CommandTags.ACTIVATE_DECK);
                addDataToRequest(Regexes.ACTIVATE_DECK.getLabel(), command, "deck");
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.ADD_CARD.getLabel())) {       // add card ...

                Request.setCommandTag(CommandTags.ADD_CARD);
                Request.extractData(command);
                Request.checkOption(command);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.REMOVE_CARD.getLabel())) {    // remove card ...

                Request.setCommandTag(CommandTags.ADD_CARD);
                Request.extractData(command);
                Request.checkOption(command);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.SHOW_ALL_DECKS.getLabel())) {  // show all decks ...

                Request.setCommandTag(CommandTags.SHOW_ALL_DECKS);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.SHOW_DECK.getLabel())) {      // show deck ...

                Request.setCommandTag(CommandTags.ADD_CARD);
                Request.extractData(command);
                Request.checkOption(command);
                Request.send();
                Console.print(Request.getResponse());

            } else if (command.matches(Regexes.SHOW_CARDS.getLabel())) {     // show cards ...

                Request.setCommandTag(CommandTags.SHOW_CARDS);
                Request.send();
                Console.print(Request.getResponse());

            } else Console.print(Responses.INVALID_COMMAND.getLabel());      // invalid command

        }
    }

    private void addDataToRequest(String regex, String command, String key) {

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            Request.addData(key, matcher.group(1));
        }
    }
}
