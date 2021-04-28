package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.Conversation;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.app.views.Modals;
import nl.jvandillen.slackbotateteen.controller.BggApiController;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Component
public class Submission {

    @Autowired
    SlackActions slackActions;
    @Autowired
    BggApiController bggApiController;
    @Autowired
    private NewGameForm newGameForm;
    @Autowired
    private NewBoardgameForm newBoardgameForm;
    @Autowired
    private CloseGameForm closeGameForm;
    @Autowired
    private ChoseGameForm choseGameForm;
    @Autowired
    private UpdateBoardgameForm updateBoardgameForm;
    @Autowired
    private Modals modals;
    @Autowired
    private BoardgameDao boardgameDao;
    @Autowired
    private GameDao gameDao;

    public void addSubmissions(App app) {
        app.viewSubmission(newGameForm.callbackID, this::createGameSubmission);
        app.viewSubmission(newBoardgameForm.callbackID, this::createBoardgameSubmission);
        app.viewSubmission(closeGameForm.callbackID, this::closeGameSubmission);
        app.viewSubmission(choseGameForm.callbackID, this::choseGameSubmission);
        app.viewSubmission(updateBoardgameForm.callbackID, this::updateBoardgameSubmission);
    }

    private Response updateBoardgameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        Boardgame boardgame = updateBoardgameForm.retrieveBoardgame(req);
        boardgameDao.save(boardgame);
        return ctx.ack();
    }

    private Response choseGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        Game game = choseGameForm.retrieveGame(req);
        return ctx.ack(r -> r.responseAction("update").view(modals.closeGameModal(game)));
    }

    private Response createBoardgameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) {
        int id;
        try {
            id = newBoardgameForm.retrieveBggID(req);
        } catch (NumberFormatException nfe) {
            return ctx.ack(r -> r.responseAction("errors").errors(Collections.singletonMap(newBoardgameForm.boardgameInputActionID, "needs to be an integer")));
        }

        Boardgame boardgame = null;
        try {
            boardgame = bggApiController.getBoardgameFromBgg(id);
            boardgameDao.save(boardgame);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        View view = modals.updateBoardgameModal(boardgame);
        return ctx.ack(r -> r.responseAction("update").view(view));
    }

    private Response createGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Game game = newGameForm.retrieveGame(ctx, req);
        gameDao.save(game);
        if (!newGameForm.noChannel(req)) {
            Conversation channel = slackActions.CreateChannel(ctx, game.getFullname());
            game.setChannelID(channel.getId());
            slackActions.InviteToChannel(ctx, channel, game.getPlayers());
            gameDao.save(game);
        }
        return ctx.ack();
    }

    private Response closeGameSubmission(ViewSubmissionRequest req, ViewSubmissionContext ctx) throws SlackApiException, IOException {
        Map<String, String> errors = closeGameForm.validate(req);
        if (!errors.isEmpty()) {
            return ctx.ack(r -> r.responseAction("errors").errors(errors));
        }
        Game game = closeGameForm.retrieveGame(req);
        gameDao.save(game);
        slackActions.CloseChannel(ctx, game.getChannelID());
        return ctx.ack();
    }

}
