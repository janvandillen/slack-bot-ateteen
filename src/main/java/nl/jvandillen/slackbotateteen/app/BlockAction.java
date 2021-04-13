package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class BlockAction {
    public void addBlockAction(App app) {
        app.blockAction(Pattern.compile(".*"),(req,ctx) -> ctx.ack());
    }
}
