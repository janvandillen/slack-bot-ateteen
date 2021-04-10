package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.NewGameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Submission {

    @Autowired
    private NewGameForm newGameForm;

    @Autowired
    SlackActions slackActions;

    public void addSubmissions(App app) {
        app.viewSubmission(newGameForm.callbackID, this::createGameSubmission);
    }

    private Response createGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Game game = newGameForm.retrieveGame(req);
        Conversation channel = slackActions.CreateChannel(ctx, game.getName());
        slackActions.InviteToChannel(ctx, channel, game.getPlayers());
        return ctx.ack();
    }
}
