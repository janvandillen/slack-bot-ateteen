package nl.jvandillen.slackbotateteen.model;

public enum SettingValue {

    integer("This value has to be an integer"),
    string("Something went wrong"),
    rating("This value has to be an integer between 0 and 7");

    String errorTxt;

    SettingValue(String errorTxt) {
        this.errorTxt = errorTxt;
    }
}
