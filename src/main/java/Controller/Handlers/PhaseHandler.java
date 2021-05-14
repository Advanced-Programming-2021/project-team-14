package Controller.Handlers;

import model.Strings;
import model.game.Game;
import model.game.Phase;
import org.json.JSONObject;
import view.enums.CommandTags;

public class PhaseHandler extends GameHandler{
    public String handle(JSONObject request, Game game){
        String command = request.getString("command");
        Phase expectedPhase = Phase.MAIN_PHASE;
        if (command.equals(CommandTags.SUMMON.getLabel())){
            response = Strings.ACTION_NOT_ALlOWED_IN_THIS_PHASE.getLabel();
        }else{
            response = Strings.CANNOT_TAKE_ACTION_IN_THIS_PHASE.getLabel();
        }
        if (command.startsWith(CommandTags.ATTACK.getLabel())){
            expectedPhase = Phase.BATTLE_PHASE;
        }

        System.out.println("phase checker");
        if (!game.getPhase().toString().contains(expectedPhase.toString()))
            return response;
        return super.handle(request, game);
    }
}
