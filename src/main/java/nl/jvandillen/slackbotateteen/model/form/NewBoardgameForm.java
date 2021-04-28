package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.model.view.ViewState;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NewBoardgameForm {

    public final String callbackID = "newBoardgame";
    public final String boardgameInputID = "boardgameInputID";
    public final String boardgameInputActionID = "NA_boardgameInputActionID";


    public int retrieveBggID(ViewSubmissionRequest req) throws NumberFormatException {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        return Integer.parseInt(stateValues.get(boardgameInputID).get(boardgameInputActionID).getValue());
    }
}
