package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class GamePlayMenu extends Menu {


    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            setCurrentMenu(Menus.GAMEPLAY_MENU);
            if (command.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
                Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
            else if (command.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
                Console.print(currentMenu);
            else if (command.matches(Regexes.SHOW_SELECTED_CARD.getLabel())) {             //show selected card
                Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
                Request.send();
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.SHOW_GRAVEYARD.getLabel())) {
                while (!command.equals(CommandTags.BACK.getLabel())) {
                    Request.setCommandTag(CommandTags.SHOW_GRAVEYARD);
                    Request.send();
                    Console.printBoard(Request.getResponse());
                    Console.print(Request.getMessage());
                    command = Console.scan();
                }
            } else if (command.matches(Regexes.SELECT.getLabel()) ||
                    command.matches(Regexes.SELECT_FIELD.getLabel())) {
                select();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.NEXT_PHASE.getLabel())) {
                Request.setCommandTag(CommandTags.NEXT_PHASE);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.SUMMON.getLabel())) {            // summon cards

                Request.setCommandTag(CommandTags.SUMMON);
                Request.addData("tributeCardAddress1", "");
                Request.addData("tributeCardAddress2", "");
                Request.addData("needTribute", "false");
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
                while (Request.getResponse().getString("needTribute").equals("true")) {
                    Request.getToken();
                    for (int i = 1; i <= Integer.parseInt(Request.getResponse().getString("tributeNumber")); i++) {
                        Request.addData("tributeCardAddress" + i, Console.scan());
                    }
                    setCurrentMenu(Menus.GAMEPLAY_MENU);
                    Request.setCommandTag(CommandTags.SUMMON);
                    Request.send();
                    Console.printBoard(Request.getResponse());
                    Console.print(Request.getMessage());
                }


            } else if (command.matches(Regexes.FLIP_SUMMON.getLabel())) {            // summon cards

                Request.setCommandTag(CommandTags.FLIP_SUMMON);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.ACTIVATE_EFFECT.getLabel())) {            // active effect
                Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.SET.getLabel())) {            // set
                Request.setCommandTag(CommandTags.SET);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.SET_POSITION.getLabel())) {            // set position
                Request.setCommandTag(CommandTags.SET_POSITION);
                Request.extractData(command);
                Request.send();
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.DESELECT.getLabel())) {            // deselect
                Request.setCommandTag(CommandTags.DESELECT);
                Request.send();
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.ATTACK_DIRECT.getLabel())) {            // direct attack
                Request.setCommandTag(CommandTags.ATTACK_DIRECT);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.ATTACK_TO.getLabel())) {            // attack to
                Request.setCommandTag(CommandTags.ATTACK);
                Request.addDataToRequest(Regexes.ATTACK_TO.getLabel(), command, Strings.TO.getLabel());
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else if (command.matches(Regexes.INCREASE_LIFE_POINT.getLabel())) {            // attack to
                Request.setCommandTag(CommandTags.INCREASE_LIFE_POINT);
                Request.addDataToRequest(Regexes.INCREASE_LIFE_POINT.getLabel(), command, "increase life point");
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            } else Console.print(Responses.INVALID_COMMAND.getLabel()); // invalid command


        }

    }

    private void select() {

        Request.setCommandTag(CommandTags.SELECT);
        Request.setOption(command, Strings.OPPONENT_OPTION.getLabel());

        if (command.matches(Regexes.SELECT_FIELD.getLabel())) {
            Request.addData("area", "field");
        } else {
            Request.addDataToRequest(Regexes.SELECT_AREA.getLabel(), command, "area");
            Request.addDataToRequest(Regexes.SELECT_POSITION.getLabel(), command, "position");
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
