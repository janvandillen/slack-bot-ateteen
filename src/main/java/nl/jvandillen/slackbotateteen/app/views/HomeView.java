package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.form.HomeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

@Component
public class HomeView {

    @Autowired
    private GameDao gameDao;
    @Autowired
    private HomeForm homeForm;



    public View createView() {
        List<BlockElement> possibleActions = new ArrayList<>();
        possibleActions.add(button(btn -> btn.text(plainText("new boardgame")).actionId(homeForm.boardgameInputActionID)));
        possibleActions.add(button(btn -> btn.text(plainText("new game")).actionId(homeForm.gameInputActionID)));

        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(header(header -> header.text(plainText("Here are the actions you can take"))));
        blocks.add(actions(act -> act.elements( possibleActions)));
        blocks.add(section(sct -> sct.text(markdownText("*Ongoing games*"))));
        blocks.add(divider());

        for (Game game: gameDao.findByRunningTrue() ) {
            String text = "*#" + game.getFullname() + "*\n" +
                    "created: " + game.getFormatedCreationDate() + "\n" +
                    "players: " + game.getPlayersName() + "\n" +
                    game.getUrl();
            blocks.add(section(sct -> sct
                    .text(markdownText(text))
                    .accessory(button(btn -> btn
                            .actionId(homeForm.closegameInputActionID)
                            .text(plainText("close"))
                            .value(String.valueOf(game.id))
                    ))
            ));
        }

        blocks.add(section(sct -> sct.text(markdownText("*Finished games*"))));
        blocks.add(divider());

        List<Game> games = gameDao.findByRunningFalse();
        games.sort(Comparator.comparing(Game::getClosed).reversed());
        for (Game game: games ) {
            String text = "*" + game.getFullname() + "*\n" +
                    "Created: " + game.getFormatedCreationDate() + "\n" +
                    "Closed: " + game.getFormatedClosingDate() + "\n" +
                    "Winners: " + game.getWinnersName() + "\n" +
                    "Players: " + game.getPlayersNameWithScore() + "\n" +
                    game.getUrl();
            blocks.add(section(sct -> sct
                    .text(markdownText(text))
            ));
        }

        return view(view -> view
                .type("home")
                .blocks(blocks)

        );
    }
}
