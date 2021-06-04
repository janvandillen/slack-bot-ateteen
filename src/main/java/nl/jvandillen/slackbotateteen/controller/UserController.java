package nl.jvandillen.slackbotateteen.controller;

import com.slack.api.bolt.context.Context;
import com.slack.api.methods.SlackApiException;
import nl.jvandillen.slackbotateteen.app.SlackActions;
import nl.jvandillen.slackbotateteen.app.dao.UserDao;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class UserController {
    @Autowired
    private UserDao userDao;

    @Autowired
    private SlackActions slackActions;

    public User getUser(Context ctx, String id) throws SlackApiException, IOException {
        Optional<User> optionalUser = userDao.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            User user = slackActions.getUserInfoSlack(ctx,id);
            userDao.save(user);
            return user;
        }
    }

    public User getUser(Context ctx) throws SlackApiException, IOException {
        return getUser(ctx,ctx.getRequestUserId());
    }
}
