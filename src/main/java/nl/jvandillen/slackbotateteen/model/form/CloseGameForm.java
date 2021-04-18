package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CloseGameForm {

    @Autowired
    private GameDao gameDao;

    public final String callbackID = "closeGame";
    private final String scorePrefix = "score";

    public String scoreInputID(User player) {
        return scorePrefix + "_" + player.userID;
    }

    public String scoreInputActionID(User player) {
        return "NA_" + scorePrefix + "_val_" + player.userID;
    }

    public String scoreInputActionID(String playerID) {
        return "NA_" + scorePrefix + "_val_" + playerID;
    }

    public String winInputID(User player) {
        return "win_" + player.userID;
    }

    public String winInputActionID(User player) {
        return "NA_win_val_" + player.userID;
    }

    public String winInputActionCheckID(User player) {
        return "win_check_" + player.userID;
    }

    public Map<String, String> validate(ViewSubmissionRequest req) {
        Map<String,String> errors = new HashMap<>();
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        for (Map.Entry<String, Map<String, ViewState.Value>> entry: stateValues.entrySet()) {
            if (entry.getKey().contains(scorePrefix)) {
                String[] arr =entry.getKey().split("_");
                String userID = arr[arr.length-1];
                try {
                    Integer.parseInt(entry.getValue().get(scoreInputActionID(userID)).getValue());
                } catch (NumberFormatException nfe) {
                    errors.put(entry.getKey(),"needs to be an integer");
                }
            }
        }
        return errors;
    }

    public Game retrieveGame(ViewSubmissionRequest req) {
        int gameID = Integer.parseInt(req.getPayload().getView().getPrivateMetadata());
        Game game = gameDao.findById(gameID).orElseThrow();
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        int[] scores = new int[game.players.length];
        List<User> winners = new ArrayList<>();
        for (int i = 0; i < scores.length; i++) {
            scores[i] = Integer.parseInt(stateValues.get(scoreInputID(game.players[i])).get(scoreInputActionID(game.players[i])).getValue());
            if (stateValues.get(winInputID(game.players[i])).get(winInputActionID(game.players[i])).getSelectedOptions().size() == 1) {
                winners.add(game.players[i]);
            }
        }
        game.scores = scores;
        User[] winnerArr = new User[winners.size()];
        game.winners = winners.toArray(winnerArr);
        game.running = false;
        game.setClosingDate();
        return game;
    }
}
