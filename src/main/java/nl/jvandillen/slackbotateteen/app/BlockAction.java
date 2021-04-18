package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import nl.jvandillen.slackbotateteen.app.views.Modals;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.form.HomeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
public class BlockAction {

    @Autowired
    private HomeForm homeForm;
    @Autowired
    private Modals modals;

    public void addBlockAction(App app) {
        app.blockAction(homeForm.boardgameInputActionID,this::createBoardGame);
        app.blockAction(homeForm.gameInputActionID,this::createGame);
        app.blockAction(homeForm.closegameInputActionID,this::closeGame);
        app.blockAction(Pattern.compile("NA_.*"),(req,ctx) -> ctx.ack());
    }

    public Response closeGame(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        Game game = homeForm.retrieveGame(req);
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.closeGameModal(game))
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }

    public Response createGame(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.createGameModal())
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }

    public Response createBoardGame(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.createBoardgameModal())
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }
}
