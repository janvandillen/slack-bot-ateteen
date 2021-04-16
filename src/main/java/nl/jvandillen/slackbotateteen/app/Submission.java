package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.NewBoardgameForm;
import nl.jvandillen.slackbotateteen.model.NewGameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Submission {

    @Autowired
    private NewGameForm newGameForm;

    @Autowired
    private NewBoardgameForm newBoardgameForm;

    @Autowired
    private BoardgameDao boardgameDao;

    @Autowired
    private GameDao gameDao;

    @Autowired
    SlackActions slackActions;

    public void addSubmissions(App app) {
        app.viewSubmission(newGameForm.callbackID, this::createGameSubmission);
        app.viewSubmission(newBoardgameForm.callbackID, this::createBoardgameSubmission);
    }

    private Response createBoardgameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        Boardgame boardgame = newBoardgameForm.retrieveGame(req);
        boardgameDao.save(boardgame);
        return ctx.ack();
    }

    private Response createGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Game game = newGameForm.retrieveGame(ctx, req);
        gameDao.save(game);
        Conversation channel = slackActions.CreateChannel(ctx, game.getName());
        slackActions.InviteToChannel(ctx, channel, game.getPlayers());
        return ctx.ack();
    }
}
