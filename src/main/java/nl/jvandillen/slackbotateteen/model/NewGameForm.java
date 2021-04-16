package nl.jvandillen.slackbotateteen.model;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class NewGameForm {

    @Autowired
    BoardgameDao boardgameDao;

    @Autowired
    UserController userController;


    public final String callbackID = "newGame";
    public final String gameInputID = "game";
    public final String gameInputActionID = "game_val";
    public final String gameNameInputID = "game_name";
    public final String gameNameInputActionID = "game_name_val";
    public final String playersInputID = "players";
    public final String playersInputActionID = "players_val";
    
    public Game retrieveGame(Context ctx, ViewSubmissionRequest req) throws SlackApiException, IOException {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        Boardgame boardgame = boardgameDao.findById(Integer.parseInt(stateValues.get(gameInputID).get(gameInputActionID).getSelectedOption().getValue())).orElseThrow();
        String name = stateValues.get(gameNameInputID).get(gameNameInputActionID).getValue().toLowerCase(Locale.ROOT).replace(" ","_");
        List<String> playerIDs = stateValues.get(playersInputID).get(playersInputActionID).getSelectedUsers();
        List<User> players = new ArrayList<>();
        for (String u: playerIDs) {
            players.add(userController.getUser(ctx,u));
        }
        return new Game(boardgame, name, players);
    }
}
