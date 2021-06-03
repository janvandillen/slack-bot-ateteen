package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.element.BlockElement;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.algo.SortBoardgames;
import nl.jvandillen.slackbotateteen.controller.DatabaseController;
import nl.jvandillen.slackbotateteen.model.*;
import nl.jvandillen.slackbotateteen.model.form.HomeForm;
import org.javatuples.Pair;
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
    @Autowired
    private SortBoardgames sortBoardgames;


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

        blocks.add(header(header -> header.text(plainText("General settings"))));

        blocks.add(section(sct -> sct
                .text(markdownText("Default rating for new games: " + user.getDefaultRating()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.modifySettingInputActionID)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultScore(user))
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games do you want to play at least: " + user.getMinGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.modifySettingInputActionID)
                        .text(plainText("modify"))
                        .value(homeForm.userMinGames(user))
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games do you want to play at maximum: " + user.getMaxGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.modifySettingInputActionID)
                        .text(plainText("modify"))
                        .value(homeForm.userMaxGames(user))
                ))
        ));

        blocks.add(section(sct -> sct
                .text(markdownText("How many games of the same board game do you want to play by default (0 is infinite): " + user.getDefaultMaxSimilarGames()))
                .accessory(button(btn -> btn
                        .actionId(homeForm.modifySettingInputActionID)
                        .text(plainText("modify"))
                        .value(homeForm.userDefaultMaxSimilarGames(user))
                ))
        ));

        blocks.add(divider());
        blocks.add(header(header -> header.text(plainText("specific game settings"))));
        blocks.add(section(sct -> sct
                .text(markdownText("- Value in `( )` are calculated values \n- Ratings go from `0: I never want to play this game` to `7: even if I am at my max games I still want to play it`. \n- if max games is `0` for specific games it means that there is no limit in how often you want to play this game"))
        ));

        for (Boardgame boardgame : databaseController.getAllBoardGame(true)) {

            String rating = "(" + user.getDefaultRating() + ")";
            String maxGames = "(" + user.getDefaultMaxSimilarGames() + ")";

            for (BoardgameRating r : boardgame.getRatings()) {
                if (r.getPlayer().equals(user)) {
                    if (r.isRatingFixed()) rating = String.valueOf(r.getRating());
                    if (r.isMaxGamesFixed()) maxGames = String.valueOf(r.getMaxGames());
                    break;
                }
            }
            String finalRating = rating;
            String finalMaxGames = maxGames;
            blocks.add(section(sct -> sct
                    .text(markdownText("*" + boardgame.getName() + "*\nRating: " + finalRating + "\nmax games: " + finalMaxGames))
            ));

            List<BlockElement> gameSettings = new ArrayList<>();
            gameSettings.add(button(btn -> btn.text(plainText("modify rating")).actionId(homeForm.modifyGameRatingInputActionID).value(homeForm.modifyGameRating(user, boardgame))));
            gameSettings.add(button(btn -> btn.text(plainText("modify max games")).actionId(homeForm.modifyGameMaxGameInputActionID).value(homeForm.modifymaxGames(user, boardgame))));
            blocks.add(actions(act -> act.elements(gameSettings)));

        }

        return view(view -> view
                .type("home")
                .blocks(blocks)
        );
    }

    public View createGameOrganiserView() {

        List<LayoutBlock> blocks = getInitialBlocks();

        blocks.add(header(header -> header.text(plainText("Game organiser"))));

        for (BoardgameCalculatedPlayability bcp : sortBoardgames.sort(databaseController.getAllBoardGame(), databaseController.getAllUsers())) {
            StringBuilder txt = new StringBuilder();
            txt.append("*" + bcp.getBoardgame().getName() + "*: " + bcp.getRating() + "\n");
            for (Pair<User, Float> p : bcp.getPotentialPlayers()) {
                txt.append(p.getValue0().getName() + ", ");
            }

            for (Pair<User, Float> p : bcp.getExtraPlayers()) {
                txt.append("(" + p.getValue0().getName() + "), ");
            }

            txt.deleteCharAt(txt.length() - 1);
            txt.deleteCharAt(txt.length() - 1);
            blocks.add(section(sct -> sct
                    .text(markdownText(txt.toString()))
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
        possibleTabs.add(button(btn -> btn.text(plainText("Game Organiser")).actionId(homeForm.gameOrganiserTabInputActionID)));
        possibleTabs.add(button(btn -> btn.text(plainText("Settings")).actionId(homeForm.settingsTabInputActionID)));

        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(header(header -> header.text(plainText("Tabs"))));
        blocks.add(actions(act -> act.elements(possibleTabs)));
        blocks.add(divider());

        return blocks;
    }
}
