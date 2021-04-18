package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChoseGameForm {

    @Autowired
    private GameDao gameDao;

    public final String callbackID = "choseGame";
    public String gameInputID = "game";
    public String gameInputActionID = "NA_game_val";

    public Game retrieveGame(ViewSubmissionRequest req) {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        int id = Integer.parseInt(stateValues.get(gameInputID).get(gameInputActionID).getSelectedOption().getValue());
        return gameDao.findById(id).orElseThrow();
    }

}
