package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.BlockActionRequest;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeForm {

    @Autowired
    private GameDao gameDao;

    public String boardgameInputActionID = "boardgameInputActionID";
    public String gameInputActionID = "gameInputActionID";
    public String closegameInputActionID = "closeGameInputActionID";

    public Game retrieveGame(BlockActionRequest req) {
        String gameID = req.getPayload().getActions().get(0).getValue();
        return gameDao.findById(Integer.parseInt(gameID)).orElseThrow();
    }
}
