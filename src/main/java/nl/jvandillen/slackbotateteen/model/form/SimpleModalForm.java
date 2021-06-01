package nl.jvandillen.slackbotateteen.model.form;

import com.slack.api.bolt.request.builtin.ViewSubmissionRequest;
import nl.jvandillen.slackbotateteen.controller.SettingController;
import nl.jvandillen.slackbotateteen.model.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SimpleModalForm {
    public final String callbackID = "simpleModal";
    public final String IDInputID = "callbackIDInputID";
    public final String IDInputActionID = "NA_callbackIDInputActionID";

    @Autowired
    private SettingController settingController;

    public Setting getSetting(ViewSubmissionRequest req) {
        return settingController.decode(req.getPayload().getView().getPrivateMetadata());
    }

    public String getValue(ViewSubmissionRequest req) {
        return req.getPayload().getView().getState().getValues().get(IDInputID).get(IDInputActionID).getValue();
    }
}
