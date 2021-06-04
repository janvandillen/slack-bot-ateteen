package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.context.Context;
import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.view.ViewState;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameRegistrationDao;
import nl.jvandillen.slackbotateteen.controller.UserController;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.GameRegistration;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class NewGameForm {

    public final String callbackID = "newGame";
    public final String gameInputID = "game";
    public final String gameInputActionID = "NA_game_val";
    public final String gameNameInputID = "game_name";
    public final String gameNameInputActionID = "NA_game_name_val";
    public final String playersInputID = "players";
    public final String playersInputActionID = "NA_players_val";
    public final String gameURLInputID = "URL";
    public final String gameURLInputActionID = "NA_URL_val";
    public final String noChannelInputID = "noChannel";
    public final String noChannelInputActionID = "NA_noChannel_val";
    public final String noChannelInputActionCheckID = "NA_noChannel_chk";
    @Autowired
    BoardgameDao boardgameDao;
    @Autowired
    GameDao gameDao;
    @Autowired
    GameRegistrationDao registrationDao;
    @Autowired
    UserController userController;

    public Game retrieveGame(Context ctx, ViewSubmissionRequest req) throws SlackApiException, IOException {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        Boardgame boardgame = boardgameDao.findById(Integer.parseInt(stateValues.get(gameInputID).get(gameInputActionID).getSelectedOption().getValue())).orElseThrow();
        String name = stateValues.get(gameNameInputID).get(gameNameInputActionID).getValue().toLowerCase(Locale.ROOT).replace(" ", "_");
        String url = stateValues.get(gameURLInputID).get(gameURLInputActionID).getValue();
        User owner = userController.getUser(ctx);

        Game game = new Game(boardgame, name, url, owner);
        gameDao.save(game);

        List<String> playerIDs = stateValues.get(playersInputID).get(playersInputActionID).getSelectedUsers();
        Set<GameRegistration> registrations = new HashSet<>();
        for (String u : playerIDs) {
            GameRegistration registration = new GameRegistration(game, userController.getUser(ctx, u));
            registrations.add(registration);
        }
        registrationDao.saveAll(registrations);
        game.setRegistrations(registrations);
        return game;
    }

    public boolean noChannel(ViewSubmissionRequest req) {
        Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
        return stateValues.get(noChannelInputID).get(noChannelInputActionID).getSelectedOptions().size() == 1;
    }
}
