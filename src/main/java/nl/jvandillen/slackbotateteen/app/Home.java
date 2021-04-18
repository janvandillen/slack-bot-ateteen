package nl.jvandillen.slackbotateteen.app;

import com.slack.api.bolt.App;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import com.slack.api.model.event.AppHomeOpenedEvent;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.app.views.HomeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Home {

    @Autowired
    private HomeView homeView;

    public void addHome(App app){
        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            View appHomeView = homeView.createView();
            ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                    .userId(payload.getEvent().getUser())
                    .view(appHomeView)
            );
            return ctx.ack();
        });
    }
}
