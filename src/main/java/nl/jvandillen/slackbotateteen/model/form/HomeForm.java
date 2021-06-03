package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.BlockActionRequest;
import nl.jvandillen.slackbotateteen.controller.DatabaseController;
import nl.jvandillen.slackbotateteen.controller.SettingController;
import nl.jvandillen.slackbotateteen.model.*;
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
    public final String modifyGameRatingInputActionID = "modifyGameRatingInputActionID";
    public final String modifyGameMaxGameInputActionID = "modifyGameMaxGameInputActionID";
    public final String gameOrganiserTabInputActionID = "gameOrganiserTabInputActionID";

    @Autowired
    private DatabaseController databaseController;
    @Autowired
    private SettingController settingController;

    public Game retrieveGame(BlockActionRequest req) {
        String gameID = req.getPayload().getActions().get(0).getValue();
        return databaseController.findGame(Integer.parseInt(gameID));
    }

    public Setting getSetting(BlockActionRequest req) {
        return settingController.decode(req.getPayload().getActions().get(0).getValue());
    }

    public String userDefaultScore(User user) {
        return settingController.encode(new Setting(SettingType.defaultRating, user));
    }

    public String userDefaultMaxSimilarGames(User user) {
        return settingController.encode(new Setting(SettingType.defaultMaxSimilarGame, user));
    }

    public String userMaxGames(User user) {
        return settingController.encode(new Setting(SettingType.maxTotalGame, user));
    }

    public String userMinGames(User user) {
        return settingController.encode(new Setting(SettingType.minTotalGame, user));
    }

    public String modifyGameRating(User user, Boardgame boardgame) {
        return settingController.encode(new Setting(SettingType.boardgameRating, user, boardgame));
    }

    public String modifymaxGames(User user, Boardgame boardgame) {
        return settingController.encode(new Setting(SettingType.maxSimilarGame, user, boardgame));
    }
}
