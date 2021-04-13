package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.List;

@Entity
public class Game {

    @GeneratedValue
    @Id
    private int id;

    private final String boardgame;
    private String name;
    private User[] players;

    public Game(String boardgame, String name, List<User> players) {
        this.boardgame = boardgame;
        this.name = name;
        this.players = (User[]) players.toArray();
    }

    public String getName() {
        return name;
    }

    public List<User> getPlayers() {
        return Arrays.stream(players).toList();
    }
}
