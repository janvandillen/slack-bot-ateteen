package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.model.NewBoardgameForm;
import nl.jvandillen.slackbotateteen.model.NewGameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.option;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;
import static com.slack.api.model.view.Views.viewClose;

@Component
public class Modals {

    @Autowired
    private NewGameForm newGameForm;
    @Autowired
    private NewBoardgameForm newBoardgameForm;

    public View createGameModal() {

        return view(view -> view
                .callbackId(newGameForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("Create new game").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(asBlocks(
                        divider(),
                        input(input -> input
                                .blockId(newGameForm.gameInputID)
                                .element(plainTextInput(pti -> pti.actionId(newGameForm.gameInputActionID)))
                                .label(plainText((pt -> pt.text("Game"))))
                        ),
                        input(input -> input
                                .blockId(newGameForm.gameNameInputID)
                                .element(plainTextInput(pti -> pti
                                        .actionId(newGameForm.gameNameInputActionID)
                                        .placeholder(plainText("Euro atetee club"))
                                ))
                                .label(plainText("Game name"))
                        ),
                        input(input -> input
                                .blockId(newGameForm.playersInputID)
                                .element(multiUsersSelect(mult -> mult
                                        .placeholder(plainText("Select players"))
                                        .actionId(newGameForm.playersInputActionID)
                                ))
                                .label(plainText((pt -> pt.text("Players"))))
                        )
                ))
        );
    }

    public View createBoardgameModal() {

        List<OptionObject> amounts = new ArrayList<>();
        for (int i = 2; i < 21; i++) {
            amounts.add(option(plainText(String.valueOf(i)),String.valueOf(i)));
        }

        List<OptionObject> categories = new ArrayList<>();
        categories.add(option(plainText("18XX"),"18XX"));
        categories.add(option(plainText("Heavy"),"Heavy"));
        categories.add(option(plainText("Medium"),"Medium"));
        categories.add(option(plainText("Low"),"Low"));
        categories.add(option(plainText("Other"),"Other"));

        List<OptionObject> playable = new ArrayList<>();
        playable.add(option(plainText("Playable live online"),newBoardgameForm.liveID));
        playable.add(option(plainText("Playable Asynch"),newBoardgameForm.asynchID));

        return view(view -> view
                .callbackId(newBoardgameForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("Create new boardgame").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(asBlocks(
                        divider(),
                        input(input -> input
                                .blockId(newBoardgameForm.boardgameInputID)
                                .element(plainTextInput(pti -> pti.actionId(newBoardgameForm.boardgameInputActionID)))
                                .label(plainText((pt -> pt.text("Game"))))
                        ),
                        section(section -> section
                                .blockId(newBoardgameForm.minPlayerInputID)
                                .text(plainText("minimum players"))
                                .accessory(staticSelect(ss -> ss
                                        .actionId(newBoardgameForm.minPlayerInputActionID)
                                        .options(amounts)
                                ))
                        ),
                        section(section -> section
                                .blockId(newBoardgameForm.maxPlayerInputID)
                                .text(plainText("maximum players"))
                                .accessory(staticSelect(ss -> ss
                                        .actionId(newBoardgameForm.maxPlayerInputActionID)
                                        .options(amounts)
                                ))
                        ),
                        section(section -> section
                                .blockId(newBoardgameForm.categoryInputID)
                                .text(plainText("category"))
                                .accessory(staticSelect(ss -> ss
                                        .actionId(newBoardgameForm.categoryInputActionID)
                                        .options(categories)
                                ))
                        ),
                        input(input -> input
                                .blockId(newBoardgameForm.websiteInputID)
                                .element(plainTextInput(pti -> pti.actionId(newBoardgameForm.websiteInputActionID)))
                                .label(plainText((pt -> pt.text("Website"))))
                        ),
                        section(input -> input
                                .blockId(newBoardgameForm.playableInputID)
                                .text(plainText(" "))
                                .accessory(checkboxes(check -> check
                                       .actionId(newBoardgameForm.playableInputActionID)
                                       .options(playable)
                                ))
                        )
                ))
        );
    }
}
