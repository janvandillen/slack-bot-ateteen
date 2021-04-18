package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import nl.jvandillen.slackbotateteen.model.Secrets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SlackApp {


    @Autowired
    private Command command;
    @Autowired
    private Submission submission;
    @Autowired
    private BlockAction blockAction;
    @Autowired
    private Home home;
    @Autowired
    private Secrets secrets;

    @Bean
    public App initSlackApp() {
        AppConfig config = new AppConfig();
        config.setSingleTeamBotToken(secrets.getSLACK_BOT_TOKEN());
        config.setSigningSecret(secrets.getSLACK_SIGNING_SECRET());
        App app = new App(config);
        command.addCommands(app);
        submission.addSubmissions(app);
        blockAction.addBlockAction(app);
        home.addHome(app);
        return app;
    }
}