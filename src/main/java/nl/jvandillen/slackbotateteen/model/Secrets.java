package nl.jvandillen.slackbotateteen.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("secrets")
public class Secrets {

    private String SLACK_BOT_TOKEN;
    private String SLACK_SIGNING_SECRET;

    public String getSLACK_BOT_TOKEN() {
        return SLACK_BOT_TOKEN;
    }

    public void setSLACK_BOT_TOKEN(String SLACK_BOT_TOKEN) {
        this.SLACK_BOT_TOKEN = SLACK_BOT_TOKEN;
    }

    public String getSLACK_SIGNING_SECRET() {
        return SLACK_SIGNING_SECRET;
    }

    public void setSLACK_SIGNING_SECRET(String SLACK_SIGNING_SECRET) {
        this.SLACK_SIGNING_SECRET = SLACK_SIGNING_SECRET;
    }
}
