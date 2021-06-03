package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UpdateBoardgameForm {

    public final String callbackID = "updateBoardgame";
    public final String websiteInputID = "websiteInputID";
    public final String websiteInputActionID = "NA_websiteInputActionID";
    public final String playableInputID = "playableInputID";
    public final String playableInputActionID = "NA_playableInputActionID";
    public final String liveID = "liveID";
    public final String asynchID = "asynchID";
    public final String boardgameShortNameInputID = "shortName";
    public final String boardgameShortNameInputActionID = "NA_shortName";

    @Autowired
    BoardgameDao boardgameDao;

    public Boardgame retrieveBoardgame(ViewSubmissionRequest req) {
        int boardgameID = Integer.parseInt(req.getPayload().getView().getPrivateMetadata());
        Boardgame boardgame = boardgameDao.findById(boardgameID).orElseThrow();

        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        boardgame.setShortName(stateValues.get(boardgameShortNameInputID).get(boardgameShortNameInputActionID).getValue());
        boardgame.setWebsite(stateValues.get(websiteInputID).get(websiteInputActionID).getValue());
        List<ViewState.SelectedOption> playable = stateValues.get(playableInputID).get(playableInputActionID).getSelectedOptions();
        boolean async = false;
        boolean liveOnline = false;
        for (ViewState.SelectedOption opt : playable) {
            switch (opt.getValue()) {
                case liveID -> async = true;
                case asynchID -> liveOnline = true;
            }
        }
        boardgame.setAsync(async);
        boardgame.setLiveOnline(liveOnline);

        return boardgame;
    }
}
