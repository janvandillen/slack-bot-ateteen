package nl.jvandillen.slackbotateteen.model;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class NewGameForm {
    public final String callbackID = "newGame";
    public final String gameInputID = "game";
    public final String gameInputActionID = "game_val";
    public final String gameNameInputID = "game_name";
    public final String gameNameInputActionID = "game_name_val";
    public final String playersInputID = "players";
    public final String playersInputActionID = "players_val";
    
    public Game retrieveGame(ViewSubmissionRequest req) {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        String boardgame = stateValues.get(gameInputID).get(gameInputActionID).getValue().toLowerCase(Locale.ROOT).replace(" ","_");
        String name = stateValues.get(gameNameInputID).get(gameNameInputActionID).getValue().toLowerCase(Locale.ROOT).replace(" ","_");
        List<String> playerIDs = stateValues.get(playersInputID).get(playersInputActionID).getSelectedUsers();
        List<User> players = new ArrayList<>();
        for (String u: playerIDs) {
            players.add(new User(u));
        }
        return new Game(boardgame, name, players);
    }
}
