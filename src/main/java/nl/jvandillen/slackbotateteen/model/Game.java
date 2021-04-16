package nl.jvandillen.slackbotateteen.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
public class Game {

    @GeneratedValue
    @Id
    private int id;

    @ManyToOne
    private Boardgame boardgame;
    private String name;
    private User[] players;
    private int[] scores;
    private User[] winners;
    private Boolean running;

    public Game() {
    }

    public Game(Boardgame boardgame, String name, List<User> playersList) {
        this.boardgame = boardgame;
        this.name = name;
        this.players = new User[playersList.size()];
        this.players = playersList.toArray(players);
        this.running = true;
    }

    public String getName() {
        return name;
    }

    public List<User> getPlayers() {
        return Arrays.stream(players).toList();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayers(User[] players) {
        this.players = players;
    }

    public void setBoardgame(Boardgame boardgame) {
        this.boardgame = boardgame;
    }

    public void setScores(int[] scores) {
        //this.scores = scores;
    }

    public void setWinners(User[] winners) {
        //this.winners = winners;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }
}
