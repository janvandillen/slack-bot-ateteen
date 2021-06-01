package nl.jvandillen.slackbotateteen.model;

public class Setting {

    SettingType type;
    String userID;
    int boardgameID;

    public Setting() {
    }

    public Setting(SettingType type, User user) {
        this();
        this.type = type;
        this.userID = user.userID;
    }

    public Setting(SettingType type, User user, Boardgame boardgame) {
        this(type, user);
        this.boardgameID = boardgame.id;
    }

    public String getName() {
        return type.name;
    }

    public boolean validate(String value) {

        if (type.valueType == SettingValue.rating | type.valueType == SettingValue.integer) {
            try {
                int valueAsInt = Integer.parseInt(value);
                if (type.valueType == SettingValue.rating & (valueAsInt > 7 | valueAsInt < 0)) return false;
            } catch (NumberFormatException e) {
                return false;
            }
        }

        return true;
    }

    public String getErrorCode() {
        return type.valueType.errorTxt;
    }

    public SettingType getType() {
        return type;
    }

    public String getUserID() {
        return userID;
    }

    public int getBoardgameID() {
        return boardgameID;
    }
}

