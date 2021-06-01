package nl.jvandillen.slackbotateteen.controller;

import com.google.gson.Gson;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Setting;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SettingController {

    @Autowired
    DatabaseController databaseController;

    public Setting decode(String rawEncodedSetting) {
        Gson gson = new Gson();
        return gson.fromJson(rawEncodedSetting, Setting.class);
    }

    public String encode(Setting setting) {
        Gson gson = new Gson();
        return gson.toJson(setting);
    }


    public boolean set(Setting setting, String value) {
        if (!setting.validate(value)) return false;

        User user = databaseController.findUser(setting.getUserID());
        Boardgame boardgame = null;
        if (setting.getBoardgameID() != 0) boardgame = databaseController.findBoardgame(setting.getBoardgameID());

        switch (setting.getType()) {
            case maxTotalGame -> user.setMaxGames(Integer.parseInt(value));
            case minTotalGame -> user.setMinGames(Integer.parseInt(value));
            case defaultRating -> user.setDefaultRating(Integer.parseInt(value));
            case defaultMaxSimilarGame -> user.setDefaultMaxSimilarGames(Integer.parseInt(value));
            case boardgameRating -> user.setBoardgameRating(boardgame, Integer.parseInt(value));
            case maxSimilarGame -> user.setBoardgameMaxGames(boardgame, Integer.parseInt(value));
        }
        databaseController.save(user);
        return true;
    }
}
