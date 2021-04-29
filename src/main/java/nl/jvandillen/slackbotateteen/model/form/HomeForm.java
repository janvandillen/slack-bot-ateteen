package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.BlockActionRequest;
import nl.jvandillen.slackbotateteen.controller.DatabaseController;
import nl.jvandillen.slackbotateteen.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeForm {

    public final String boardgameInputActionID = "boardgameInputActionID";
    public final String gameInputActionID = "gameInputActionID";
    public final String closegameInputActionID = "closeGameInputActionID";
    public final String gameTabInputActionID = "gameTabInputActionID";
    public final String settingsTabInputActionID = "SettingsTabInputActionID";
    public final String modifySettingInputActionID = "modifySettingInputActionID";
    public final String userDefaultScore = "userDefaultScore";
    public final String userDefaultMaxSimilarGames = "userDefaultMaxSimilarGames";
    public final String userMaxGames = "userMaxGames";
    public final String userMinGames = "userMinGames";
    public final String modifyGameRatingInputActionID = "modifyGameRatingInputActionID";
    public final String modifyGameMaxGameInputActionID = "modifyGameMaxGameInputActionID";
    @Autowired
    private DatabaseController databaseController;

    public Game retrieveGame(BlockActionRequest req) {
        String gameID = req.getPayload().getActions().get(0).getValue();
        return databaseController.findGame(Integer.parseInt(gameID));
    }
}
