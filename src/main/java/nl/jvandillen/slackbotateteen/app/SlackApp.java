package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
@ConfigurationProperties("slackapp")
public class SlackApp {
    private String SLACK_BOT_TOKEN;
    private String SLACK_SIGNING_SECRET;

    @Bean
    public App initSlackApp() {
        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken(SLACK_BOT_TOKEN);
        config.setSigningSecret(SLACK_SIGNING_SECRET);
        App app = new App(config);
        app.command("/dev-creategame", (req, ctx) -> {
            return ctx.ack("What's up?");
        });
        return app;
    }

    public void setSLACK_BOT_TOKEN(String SLACK_BOT_TOKEN) {
        this.SLACK_BOT_TOKEN = SLACK_BOT_TOKEN;
    }

    public void setSLACK_SIGNING_SECRET(String SLACK_SIGNING_SECRET) {
        this.SLACK_SIGNING_SECRET = SLACK_SIGNING_SECRET;
    }
}