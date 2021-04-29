package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.controller.DatabaseController;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.BoardgameRating;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.User;
import nl.jvandillen.slackbotateteen.model.form.HomeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.button;
import static com.slack.api.model.view.Views.view;

@Component
public class HomeView {

    @Autowired
    private DatabaseController databaseController;
    @Autowired
    private HomeForm homeForm;


    public View createGamesView() {
        List<BlockElement> possibleActions = new ArrayList<>();
        possibleActions.add(button(btn -> btn.text(plainText("new boardgame")).actionId(homeForm.boardgameInputActionID)));
        possibleActions.add(button(btn -> btn.text(plainText("new game")).actionId(homeForm.gameInputActionID)));

        List<LayoutBlock> blocks = getInitialBlocks();
        blocks.add(header(header -> header.text(plainText("Here are the actions you can take"))));
        blocks.add(actions(act -> act.elements(possibleActions)));
        blocks.add(section(sct -> sct.text(markdownText("*Ongoing games*"))));
        blocks.add(divider());

        for (Game game : databaseController.getRunningGames()) {
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

        List<Game> games = databaseController.getFinishedGames();
        games.sort(Comparator.comparing(Game::getClosed).reversed());
        for (Game game : games) {
            String text = "*" + game.getFullname() + "*\n" +
                    "Created: " + game.getFormatedCreationDate() + " : " +
                    "Closed: " + game.getFormatedClosingDate() + "\n" +
                    "Winner: " + game.getWinnersName() + "\n" +
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

    public View createSettingsView(User user) {

        List<LayoutBlock> blocks = getInitialBlocks();

        blocks.add(section(sct -> sct
                .text(markdownText("Default rating for new games: " + user.getDefaultRating()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.modifySettingInputActionID)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultScore)
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games do you want to play at least: " + user.getMinGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.userDefaultMaxSimilarGames)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultScore)
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games do you want to play at maximum: " + user.getMaxGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.userMaxGames)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultScore)
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games of the same board game doo you want to play by default (0 is infinite): " + user.getDefaultMaxSimilarGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.userMinGames)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultScore)
                ))
        ));

        blocks.add(divider());
        for (Boardgame boardgame : databaseController.getAllBoardGame()) {

            String rating = "(" + user.getDefaultRating() + ")";
            String maxGames = "(" + user.getDefaultMaxSimilarGames() + ")";

            for (BoardgameRating r : boardgame.getRatings()) {
                if (r.getPlayer() == user) {
                    if (r.isFixed()) {
                        rating = String.valueOf(r.getRating());
                        maxGames = String.valueOf(r.getMaxGames());
                    }
                    break;
                }
            }

            blocks.add(section(sct -> sct
                    .text(markdownText("*" + boardgame.getName() + "*"))
            ));
            String finalRating = rating;

            blocks.add(section(sct -> sct
                    .text(markdownText("Rating: " + finalRating))
                    .accessory(button(btn -> btn
                            .actionId(homeForm.modifyGameRatingInputActionID)
                            .text(plainText("modify rating"))
                            .value(String.valueOf(boardgame.getId()))
                    ))
            ));

            String finalMaxGames = maxGames;
            blocks.add(section(sct -> sct
                    .text(markdownText("How many games of this boardgame do you want to max play at the same time: " + finalMaxGames))
                    .accessory(button(btn -> btn
                            .actionId(homeForm.modifyGameMaxGameInputActionID)
                            .text(plainText("modify"))
                            .value(String.valueOf(boardgame.getId()))
                    ))
            ));

        }

        return view(view -> view
                .type("home")
                .blocks(blocks)

        );
    }

    private List<LayoutBlock> getInitialBlocks() {

        List<BlockElement> possibleTabs = new ArrayList<>();
        possibleTabs.add(button(btn -> btn.text(plainText("Games")).actionId(homeForm.gameTabInputActionID)));
        possibleTabs.add(button(btn -> btn.text(plainText("Settings")).actionId(homeForm.settingsTabInputActionID)));

        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(header(header -> header.text(plainText("Tabs"))));
        blocks.add(actions(act -> act.elements(possibleTabs)));
        blocks.add(divider());

        return blocks;
    }
}
