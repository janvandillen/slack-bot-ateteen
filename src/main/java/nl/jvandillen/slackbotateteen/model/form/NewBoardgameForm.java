package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class NewBoardgameForm {

    public final String callbackID = "newBoardgame";
    public final String nameInputID = "name";
    public final String nameInputActionID = "NA_name_val";
    public final String boardgameInputID = "boardgameInputID";
    public final String boardgameInputActionID = "NA_boardgameInputActionID";
    public final String minPlayerInputID = "minPlayerInputID";
    public final String minPlayerInputActionID = "NA_minPlayerInputActionID";
    public final String maxPlayerInputID = "maxPlayerInputID";
    public final String maxPlayerInputActionID = "NA_maxPlayerInputActionID";
    public final String categoryInputID = "categoryInputID";
    public final String categoryInputActionID = "NA_categoryInputActionID";
    public final String websiteInputID = "websiteInputID";
    public final String websiteInputActionID = "NA_websiteInputActionID";
    public final String playableInputID = "playableInputID";
    public final String playableInputActionID = "NA_playableInputActionID";
    public final String liveID = "liveID";
    public final String asynchID = "asynchID";

    public Boardgame retrieveGame(ViewSubmissionRequest req) {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        String name = stateValues.get(boardgameInputID).get(boardgameInputActionID).getValue();
        int maxPlayers = Integer.parseInt(stateValues.get(maxPlayerInputID).get(maxPlayerInputActionID).getSelectedOption().getValue());
        int minPlayers = Integer.parseInt(stateValues.get(minPlayerInputID).get(minPlayerInputActionID).getSelectedOption().getValue());
        String website = stateValues.get(websiteInputID).get(websiteInputActionID).getValue();
        String category = stateValues.get(categoryInputID).get(categoryInputActionID).getSelectedOption().getValue();

        List<ViewState.SelectedOption> playable =  stateValues.get(playableInputID).get(playableInputActionID).getSelectedOptions();
        boolean async = false;
        boolean liveOnline = false;
        for (ViewState.SelectedOption opt: playable ) {
            switch (opt.getValue()) {
                case liveID -> async = true;
                case asynchID -> liveOnline = true;
            }
        }
        return new Boardgame(name, maxPlayers, minPlayers, async, liveOnline, website, category);
    }
}
