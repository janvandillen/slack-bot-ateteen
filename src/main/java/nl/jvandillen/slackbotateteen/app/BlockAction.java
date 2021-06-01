package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.app.views.HomeView;
import nl.jvandillen.slackbotateteen.app.views.Modals;
import nl.jvandillen.slackbotateteen.controller.UserController;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.Setting;
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
    private HomeView homeView;
    @Autowired
    private UserController userController;
    @Autowired
    private Modals modals;

    public void addBlockAction(App app) {
        app.blockAction(homeForm.boardgameInputActionID, this::createBoardGame);
        app.blockAction(homeForm.gameInputActionID, this::createGame);
        app.blockAction(homeForm.closegameInputActionID, this::closeGame);
        app.blockAction(homeForm.gameTabInputActionID, this::openGameTab);
        app.blockAction(homeForm.settingsTabInputActionID, this::openSettingsTab);
        app.blockAction(homeForm.modifySettingInputActionID, this::modifySetting);
        app.blockAction(homeForm.modifyGameRatingInputActionID, this::modifySetting);
        app.blockAction(homeForm.modifyGameMaxGameInputActionID, this::modifySetting);
        app.blockAction(Pattern.compile("NA_.*"), (req, ctx) -> ctx.ack());
    }


    private Response modifySetting(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        Setting setting = homeForm.getSetting(req);
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.simpleModal(setting))
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }

    private Response openSettingsTab(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        View appHomeView = homeView.createSettingsView(userController.getUser(ctx, ctx.getRequestUserId()));
        ctx.client().viewsPublish(r -> r
                .userId(ctx.getRequestUserId())
                .view(appHomeView)
        );
        return ctx.ack();
    }

    private Response openGameTab(BlockActionRequest req, ActionContext ctx) throws SlackApiException, IOException {
        View appHomeView = homeView.createGamesView();
        ctx.client().viewsPublish(r -> r
                .userId(ctx.getRequestUserId())
                .view(appHomeView)
        );
        return ctx.ack();
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
