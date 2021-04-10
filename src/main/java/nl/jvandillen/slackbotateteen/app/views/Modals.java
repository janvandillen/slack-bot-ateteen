package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.model.NewGameForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.Blocks.input;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.multiUsersSelect;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;
import static com.slack.api.model.view.Views.*;
import static com.slack.api.model.view.Views.viewClose;

@Component
public class Modals {

    @Autowired
    private NewGameForm newGameForm;

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
}
