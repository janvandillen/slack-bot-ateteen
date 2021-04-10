package nl.jvandillen.slackbotateteen.model;

import java.util.List;

public class Game {
    private final String boardgame;
    private String name;
    private List<User> players;

    public Game(String boardgame, String name, List<User> players) {
        this.boardgame = boardgame;
        this.name = name;
        this.players = players;
    }

    public String getName() {
        return name;
    }

    public List<User> getPlayers() {
        return players;
    }
}
