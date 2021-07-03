package view;

import model.Strings;
import view.enums.CommandTags;
import view.enums.Menus;
import view.enums.Regexes;
import view.enums.Responses;

public class GamePlayMenu extends Menu {


    public void run() {

        while (!(command = Console.scan()).equals(Regexes.MENU_EXIT.getLabel())) {
            commandCheckers(command);
        }

    }


    public void commandCheckers(String inputCommand) {


        setCurrentMenu(Menus.GAMEPLAY_MENU);
        if (inputCommand.matches(Regexes.MENU_ENTER.getLabel()))                           // enter other menus
            Console.print(Responses.IMPOSSIBLE_MENU_NAVIGATION.getLabel());
        else if (inputCommand.matches(Regexes.MENU_CURRENT.getLabel()))                    // show current menu
            Console.print(currentMenu);
        else if (inputCommand.matches(Regexes.SHOW_SELECTED_CARD.getLabel())) {             //show selected card
            Request.setCommandTag(CommandTags.SHOW_SELECTED_CARD);
            Request.send();
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SHOW_CARD.getLabel())) {             //show selected card
            Request.setCommandTag(CommandTags.SHOW_CARD);
            Request.addDataToRequest(Regexes.SHOW_CARD.getLabel(), command, "card");
            Request.send();
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SHOW_GRAVEYARD.getLabel())) {
            while (!inputCommand.equals(CommandTags.BACK.getLabel())) {
                setCurrentMenu(Menus.GAMEPLAY_MENU);
                Request.setCommandTag(CommandTags.SHOW_GRAVEYARD);
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
                inputCommand = Console.scan();
            }
        } else if (inputCommand.matches(Regexes.SELECT.getLabel()) ||
                inputCommand.matches(Regexes.SELECT_FIELD.getLabel())) {
            select(inputCommand);
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SELECT_FORCE.getLabel())) {
            Request.setCommandTag(CommandTags.SELECT_FORCE);
            Request.addDataToRequest(Regexes.SELECT_FORCE.getLabel(), inputCommand, "card");
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.WIN_GAME.getLabel())) {
            Request.setCommandTag(CommandTags.WIN_GAME);
            Request.send();
            endDuelChecker();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.NEXT_PHASE.getLabel())) {
            Request.setCommandTag(CommandTags.NEXT_PHASE);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SUMMON.getLabel())) {            // summon cardLoaders

            Request.setCommandTag(CommandTags.SUMMON);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
            while (Request.isChoice()) {
                setCurrentMenu(Menus.GAMEPLAY_MENU);
                Request.setCommandTag(CommandTags.SUMMON);
                Request.addData("data", Console.scan());
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            }
        } else if (inputCommand.matches(Regexes.FLIP_SUMMON.getLabel())) {            // summon cardLoaders

            Request.setCommandTag(CommandTags.FLIP_SUMMON);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.RITUAL_SUMMON.getLabel())) {            // summon cardLoaders

            Request.setCommandTag(CommandTags.RITUAL_SUMMON);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SPECIAL_SUMMON.getLabel())) {            // summon cardLoaders

            Request.setCommandTag(CommandTags.SPECIAL_SUMMON);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.ACTIVATE_EFFECT.getLabel())) {            // active effect
            Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
            Request.send();
            if (Request.isChoice()) {
                Request.setCommandTag(CommandTags.ACTIVATE_EFFECT);
                setCurrentMenu(Menus.GAMEPLAY_MENU);
                Console.print(Request.getMessage());
                Request.addData("data", Console.scan());
                Request.send();
            }
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SET.getLabel())) {            // set
            Request.setCommandTag(CommandTags.SET);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
            while (Request.isChoice()) {
                setCurrentMenu(Menus.GAMEPLAY_MENU);
                Request.setCommandTag(CommandTags.SUMMON);
                Request.addData("data", Console.scan());
                Request.send();
                Console.printBoard(Request.getResponse());
                Console.print(Request.getMessage());
            }
        } else if (inputCommand.matches(Regexes.SET_POSITION.getLabel())) {            // set position
            Request.setCommandTag(CommandTags.SET_POSITION);
            if (inputCommand.contains("--pos ") && ((inputCommand + " ").contains("att ") || (inputCommand + " ").contains("def ")))
                Request.addShortData(inputCommand);
            else
                Request.extractData(inputCommand);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.DESELECT.getLabel())) {            // deselect
            Request.setCommandTag(CommandTags.DESELECT);
            Request.send();
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.ATTACK_DIRECT.getLabel())) {            // direct attack
            Request.setCommandTag(CommandTags.DIRECT_ATTACK);
            Request.send();
            endDuelChecker();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.ATTACK_TO.getLabel())) {            // attack to
            Request.setCommandTag(CommandTags.ATTACK);
            Request.addDataToRequest(Regexes.ATTACK_TO.getLabel(), inputCommand, Strings.TO.getLabel());
            Request.send();
            endDuelChecker();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.INCREASE_LIFE_POINT.getLabel())) {            // increase life point
            Request.setCommandTag(CommandTags.INCREASE_LIFE_POINT);
            Request.addDataToRequest(Regexes.INCREASE_LIFE_POINT.getLabel(), inputCommand, "amount");
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.SURRENDER.getLabel())) {            // surrender

            Request.setCommandTag(CommandTags.SURRENDER);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else if (inputCommand.matches(Regexes.CANCEL.getLabel())) {            // surrender
            Request.setCommandTag(CommandTags.CANCEL_ACTIVATION);
            Request.send();
            Console.printBoard(Request.getResponse());
            Console.print(Request.getMessage());
        } else Console.print(Responses.INVALID_COMMAND.getLabel()); // invalid command
    }

    private void endDuelChecker() {

        if (Request.getResponse().getString("isDuelEnded").equals("true")) {
            Console.print(Request.getMessage());
            new MainMenu().run();
        }
    }


    private void select(String inputCommand) {

        Request.setCommandTag(CommandTags.SELECT);
        Request.setOption(inputCommand, Strings.OPPONENT_OPTION.getLabel());

        if (inputCommand.matches(Regexes.SELECT_FIELD.getLabel())) {
            Request.addData("area", "field");
        } else {
            if (inputCommand.matches(Regexes.SHORT_SELECT_AREA.getLabel()))
                Request.addShortData(inputCommand);
            else
                Request.addDataToRequest(Regexes.SELECT_AREA.getLabel(), inputCommand, "area");

            Request.addDataToRequest(Regexes.SELECT_POSITION.getLabel(), inputCommand, "position");
        }

        Request.send();


        // select --field
        // select --field --opponent
        // select --monster n
        // select --monster n --opponent
        // select --spell n
        // select --spell n --opponent
        // select --hand n

    }

}
