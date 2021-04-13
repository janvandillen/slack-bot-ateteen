package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.views.ViewsOpenResponse;
import nl.jvandillen.slackbotateteen.app.views.Modals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@ConfigurationProperties("command")
public class Command {

    private String CREATE_GAME;
    private String CREATE_BOARDGAME;

    @Autowired
    private Modals modals;

    public void addCommands(App app) {
        app.command(CREATE_GAME, this::createGame);
        app.command(CREATE_BOARDGAME, this::createBoardGame);
    }

    private Response createGame(SlashCommandRequest req, SlashCommandContext ctx) throws SlackApiException, IOException {
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.createGameModal())
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }

    private Response createBoardGame(SlashCommandRequest req, SlashCommandContext ctx) throws SlackApiException, IOException {
        ViewsOpenResponse viewsOpenResponse = ctx.client().viewsOpen(r -> r
                .triggerId(ctx.getTriggerId())
                .view(modals.createBoardgameModal())
        );
        if (viewsOpenResponse.isOk()) return ctx.ack();
        else return Response.builder().statusCode(500).body(viewsOpenResponse.getError()).build();
    }

    public void setCREATE_GAME(String CREATE_GAME) {
        this.CREATE_GAME = CREATE_GAME;
    }

    public void setCREATE_BOARDGAME(String CREATE_BOARDGAME) {
        this.CREATE_BOARDGAME = CREATE_BOARDGAME;
    }
}
