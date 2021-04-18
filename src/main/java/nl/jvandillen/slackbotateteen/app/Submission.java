package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.app.views.Modals;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.form.ChoseGameForm;
import nl.jvandillen.slackbotateteen.model.form.CloseGameForm;
import nl.jvandillen.slackbotateteen.model.form.NewBoardgameForm;
import nl.jvandillen.slackbotateteen.model.form.NewGameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class Submission {

    @Autowired
    private NewGameForm newGameForm;
    @Autowired
    private NewBoardgameForm newBoardgameForm;
    @Autowired
    private CloseGameForm closeGameForm;
    @Autowired
    private ChoseGameForm choseGameForm;
    @Autowired
    private Modals modals;
    @Autowired
    private BoardgameDao boardgameDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    SlackActions slackActions;

    public void addSubmissions(App app) {
        app.viewSubmission(newGameForm.callbackID, this::createGameSubmission);
        app.viewSubmission(newBoardgameForm.callbackID, this::createBoardgameSubmission);
        app.viewSubmission(closeGameForm.callbackID, this::closeGameSubmission);
        app.viewSubmission(choseGameForm.callbackID, this::choseGameSubmission);
    }

    private Response choseGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        Game game = choseGameForm.retrieveGame(req);
        return ctx.ack(r -> r.responseAction("update").view(modals.closeGameModal(game)));
    }

    private Response createBoardgameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        Boardgame boardgame = newBoardgameForm.retrieveGame(req);
        boardgameDao.save(boardgame);
        return ctx.ack();
    }

    private Response createGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Game game = newGameForm.retrieveGame(ctx, req);
        gameDao.save(game);
        Conversation channel = slackActions.CreateChannel(ctx, game.getFullname());
        game.setChannelID(channel.getId());
        slackActions.InviteToChannel(ctx, channel, game.getPlayers());
        gameDao.save(game);
        return ctx.ack();
    }

    private Response closeGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Map<String,String> errors = closeGameForm.validate(req);
        if (!errors.isEmpty()) { return ctx.ack(r -> r.responseAction("errors").errors(errors));}
        Game game = closeGameForm.retrieveGame(req);
        gameDao.save(game);
        slackActions.CloseChannel(ctx,game.getChannelID());
        return ctx.ack();
    }

}
