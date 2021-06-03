package nl.jvandillen.slackbotateteen.model;

public enum SettingType {
    defaultRating("default rating", SettingValue.rating),
    minTotalGame("minimum total games", SettingValue.integer),
    maxTotalGame("maximum total games", SettingValue.integer),
    maxSimilarGame("maximum similar games", SettingValue.integer),
    defaultMaxSimilarGame("default maximum similar games", SettingValue.integer),
    boardgameRating("boardgame rating", SettingValue.rating);


    SettingValue valueType;
    String name;

    SettingType(String name, SettingValue valueType) {
        this.valueType = valueType;
        this.name = name;
    }
}
