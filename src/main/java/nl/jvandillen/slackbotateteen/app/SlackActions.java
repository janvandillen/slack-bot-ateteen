package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.context.builtin.ViewSubmissionContext;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.conversations.ConversationsCreateResponse;
import com.slack.api.model.Conversation;
import nl.jvandillen.slackbotateteen.model.Secrets;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SlackActions {

    @Autowired
    private Secrets secrets;

    public Conversation CreateChannel(Context ctx, String name) throws SlackApiException, IOException {
        ConversationsCreateResponse result = ctx.client().conversationsCreate(r -> r.name(name).token(secrets.getSLACK_BOT_TOKEN()));
        return result.getChannel();
    }

    public void InviteToChannel(ViewSubmissionContext ctx, Conversation channel, List<User> users) throws SlackApiException, IOException {

        List<String> userIDs = new ArrayList<>();

        for (User user :
                users) {
            userIDs.add(user.userID);
        }

        ctx.client().conversationsInvite(r -> r
                .token(secrets.getSLACK_BOT_TOKEN())
                .channel(channel.getId())
                .users(userIDs)
        );
    }
}
