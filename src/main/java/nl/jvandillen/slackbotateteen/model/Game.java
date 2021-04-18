package nl.jvandillen.slackbotateteen.model;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public Date created;
    public Date closed;
    public String url;

    public Game() {
    }

    public Game(Boardgame boardgame, String name, List<User> playersList, String url) {
        this.boardgame = boardgame;
        this.name = name;
        this.players = new User[playersList.size()];
        this.players = playersList.toArray(players);
        this.running = true;
        this.url = url;
        setCreationDate();
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

    public void setCreationDate(){
        created = Calendar.getInstance().getTime();
    }

    public void setClosingDate(){
        closed = Calendar.getInstance().getTime();
    }

    public String getFormatedCreationDate() {
        return dateToString(created);
    }

    public String getFormatedClosingDate() {
        return dateToString(closed);
    }

    private String dateToString(Date date) {
        DateFormat format = new SimpleDateFormat("dd/MM/yy");
        return format.format(date);
    }

    public String getPlayersName() {
        StringBuilder out = new StringBuilder();
        for (User p: players ) {
            out.append(p.name).append(", ");
        }
        out.deleteCharAt(out.length()-1);
        out.deleteCharAt(out.length()-1);
        return out.toString();
    }

    public String getPlayersNameWithScore() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < players.length; i++) {
            out.append(players[i].name).append(" (").append(scores[i]).append("), ");
        }
        out.deleteCharAt(out.length()-1);
        out.deleteCharAt(out.length()-1);
        return out.toString();
    }

    public String getWinnersName() {
        StringBuilder out = new StringBuilder();
        for (User p: winners ) {
            out.append(p.name).append(", ");
        }
        out.deleteCharAt(out.length()-1);
        out.deleteCharAt(out.length()-1);
        return out.toString();
    }

    public Date getClosed() {
        return closed;
    }

    public String getUrl() {
        if (url == null) {
            return "";
        }
        return url;
    }
}
