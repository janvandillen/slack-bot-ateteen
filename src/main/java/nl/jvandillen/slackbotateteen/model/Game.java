package nl.jvandillen.slackbotateteen.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Entity
public class Game {

    @GeneratedValue
    @Id
    public int id;

    @ManyToOne
    private Boardgame boardgame;
    public String name;
    public User[] players;
    public int[] scores;
    public User[] winners;
    public Boolean running;
    private String channelID;

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

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }

    public String getFullname() {
        return boardgame.name.toLowerCase(Locale.ROOT) + "-" + name.toLowerCase(Locale.ROOT) + "-" + id;
    }
}
