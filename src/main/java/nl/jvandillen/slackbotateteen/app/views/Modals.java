package nl.jvandillen.slackbotateteen.app.views;

import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.OptionObject;
import com.slack.api.model.view.View;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.GameDao;
import nl.jvandillen.slackbotateteen.controller.SettingController;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.Setting;
import nl.jvandillen.slackbotateteen.model.User;
import nl.jvandillen.slackbotateteen.model.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.option;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.model.view.Views.*;

@Component
public class Modals {

    @Autowired
    private NewGameForm newGameForm;
    @Autowired
    private NewBoardgameForm newBoardgameForm;
    @Autowired
    private CloseGameForm closeGameForm;
    @Autowired
    private ChoseGameForm choseGameForm;
    @Autowired
    private UpdateBoardgameForm updateBoardgameForm;
    @Autowired
    private SimpleModalForm simpleModalForm;
    @Autowired
    private BoardgameDao boardgameDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private SettingController settingController;

    public View createGameModal() {

        List<OptionObject> options = new ArrayList<>();
        for (Boardgame b : boardgameDao.findAll()) {
            options.add(option(plainText(b.getName()), String.valueOf(b.getId())));
        }

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
                                .label(plainText("Boardgame"))
                                .element(staticSelect(ss -> ss
                                        .actionId(newGameForm.gameInputActionID)
                                        .options(options)
                                ))
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
                        ),
                        input(input -> input
                                .blockId(newGameForm.gameURLInputID)
                                .element(plainTextInput(pti -> pti
                                        .actionId(newGameForm.gameURLInputActionID)
                                        .placeholder(plainText("https://18xx.games"))
                                ))
                                .label(plainText("Link"))
                        ),
                        section(input -> input
                                .blockId(newGameForm.noChannelInputID)
                                .text(plainText(" "))
                                .accessory(checkboxes(check -> check
                                        .actionId(newGameForm.noChannelInputActionID)
                                        .options(Collections.singletonList(option(plainText("Skip channel creation"), newGameForm.noChannelInputActionCheckID)))
                                ))
                        )
                ))
        );
    }

    public View createBoardgameModal() {

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
                                .label(plainText((pt -> pt.text("Boardgame geek ID"))))
                        )
                ))
        );
    }

    public View updateBoardgameModal(Boardgame boardgame) {

        List<OptionObject> playable = new ArrayList<>();
        playable.add(option(plainText("Playable live online"), updateBoardgameForm.liveID));
        playable.add(option(plainText("Playable Asynch"), updateBoardgameForm.asynchID));

        String titleTxt = boardgame.getName();
        if (titleTxt.length() > 25) {
            titleTxt = titleTxt.substring(0, 21) + "...";
        }
        String finalTitleTxt = titleTxt;

        return view(view -> view
                .callbackId(updateBoardgameForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text(finalTitleTxt).emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(asBlocks(
                        divider(),
                        input(input -> input
                                .blockId(updateBoardgameForm.boardgameShortNameInputID)
                                .element(plainTextInput(pti -> pti.actionId(updateBoardgameForm.boardgameShortNameInputActionID)))
                                .label(plainText((pt -> pt.text("Abriviation"))))
                        ),
                        input(input -> input
                                .blockId(updateBoardgameForm.websiteInputID)
                                .element(plainTextInput(pti -> pti.actionId(updateBoardgameForm.websiteInputActionID)))
                                .label(plainText((pt -> pt.text("Website"))))
                        ),
                        section(input -> input
                                .blockId(updateBoardgameForm.playableInputID)
                                .text(plainText(" "))
                                .accessory(checkboxes(check -> check
                                        .actionId(updateBoardgameForm.playableInputActionID)
                                        .options(playable)
                                ))
                        )
                ))
                .privateMetadata(String.valueOf(boardgame.getId()))
        );

    }

    public View choseGameModal() {

        List<OptionObject> options = new ArrayList<>();
        for (Game g : gameDao.findByRunningTrue()) {
            options.add(option(plainText(g.getFullname()), String.valueOf(g.id)));
        }

        return view(view -> view
                .callbackId(choseGameForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("Chose Game").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(asBlocks(
                        divider(),
                        input(input -> input
                                .blockId(choseGameForm.gameInputID)
                                .label(plainText("game"))
                                .element(staticSelect(ss -> ss
                                        .actionId(choseGameForm.gameInputActionID)
                                        .options(options)
                                ))
                        )
                ))
        );
    }

    public View closeGameModal(Game game) {

        List<LayoutBlock> blocks = new ArrayList<>();
        for (User player : game.getPlayers()) {
            blocks.add(divider());
            blocks.add(input(input -> input
                    .blockId(closeGameForm.scoreInputID(player))
                    .label(plainText(player.getName()))
                    .element(plainTextInput(pti -> pti
                            .actionId(closeGameForm.scoreInputActionID(player))
                            .placeholder(plainText("0"))
                    ))
            ));
            blocks.add(section(input -> input
                    .blockId(closeGameForm.winInputID(player))
                    .text(plainText(" "))
                    .accessory(checkboxes(check -> check
                            .actionId(closeGameForm.winInputActionID(player))
                            .options(Collections.singletonList(option(plainText("winner"), closeGameForm.winInputActionCheckID(player))))
                    ))
            ));
        }

        String titleTxt = "Close " + game.getFullname();
        if (titleTxt.length() > 25) {
            titleTxt = titleTxt.substring(0, 21) + "...";
        }
        String finalTitleTxt = titleTxt;

        return view(view -> view
                .callbackId(closeGameForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text(finalTitleTxt).emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(blocks)
                .privateMetadata(String.valueOf(game.id))
        );
    }

    public View simpleModal(Setting setting ){
        return view(view -> view
                .callbackId(simpleModalForm.callbackID)
                .type("modal")
                .notifyOnClose(true)
                .title(viewTitle(title -> title.type("plain_text").text("modify setting").emoji(true)))
                .submit(viewSubmit(submit -> submit.type("plain_text").text("Submit").emoji(true)))
                .close(viewClose(close -> close.type("plain_text").text("Cancel").emoji(true)))
                .blocks(asBlocks())
                .blocks(asBlocks(
                        divider(),
                        input(input -> input
                                .blockId(simpleModalForm.IDInputID)
                                .element(plainTextInput(pti -> pti.actionId(simpleModalForm.IDInputActionID)))
                                .label(plainText((pt -> pt.text(setting.getName()))))
                        )
                ))
                .privateMetadata(settingController.encode(setting))
        );
    }
}
