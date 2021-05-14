package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class GamePlayMenu extends Menu{

    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.GAMEPLAY_MENU);
            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);
            else if (command.startsWith(Regexes.SELECT.getLabel()))
                select();


            Console.print(Request.getResponse());
        }

    }

    private void select() {

        Request.setCommandTag(CommandTags.SELECT);

        if (command.matches(Regexes.SELECT_FIELD.getLabel())){
            Request.addData("area", "field");
        }else{
            Request.addDataToRequest(Regexes.SELECT_AREA.getLabel(), command, "area");
            Request.addDataToRequest(Regexes.SELECT_POSITION.getLabel(), command, "position");
        }

        if (!command.matches(Regexes.SELECT_HAND.getLabel())){
            Request.setOption(command, Strings.OPPONENT_OPTION.getLabel());
        }

        Request.send();


            // select --filed
            // select --filed --opponent
            // select --monster n
            // select --monster n --opponent
            // select --spell n
            // select --spell n --opponent
            // select --hand n

    }
}
