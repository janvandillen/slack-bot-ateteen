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
    @OneToMany(mappedBy = "game", fetch = FetchType.EAGER)
    Set<GameRegistration> registrations;
    String name;
    Boolean running;
    String channelID;
    Date created;
    Date closed;
    String url;
    @ManyToOne
    private Boardgame boardgame;

    public Game() {
    }

    public Game(Boardgame boardgame, String name, String url) {
        this.boardgame = boardgame;
        this.name = name;
        this.running = true;
        this.url = url;
        setCreationDate();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getPlayers() {
        List<User> out = new ArrayList<>();
        for (GameRegistration gr : registrations) {
            out.add(gr.player);
        }
        return out;
    }

    public void setId(int id) {
        this.id = id;
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
        return boardgame.name.toLowerCase(Locale.ROOT).replace(" ","_") + "-" + name.toLowerCase(Locale.ROOT).replace(" ","_") + "-" + id;
    }

    public void setCreationDate() {
        created = Calendar.getInstance().getTime();
    }

    public void setClosingDate() {
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
        for (GameRegistration gr : registrations) {
            out.append(gr.player.name).append(", ");
        }
        out.deleteCharAt(out.length() - 1);
        out.deleteCharAt(out.length() - 1);
        return out.toString();
    }

    public String getPlayersNameWithScore() {
        StringBuilder out = new StringBuilder();
        for (GameRegistration gr : registrations) {
            out.append(gr.player.name).append(" (").append(gr.score).append("), ");
        }
        out.deleteCharAt(out.length() - 1);
        out.deleteCharAt(out.length() - 1);
        return out.toString();
    }

    public String getWinnersName() {
        StringBuilder out = new StringBuilder();
        for (GameRegistration gr : registrations) {
            if (gr.win) out.append(gr.player.name).append(", ");
        }
        if (out.length() == 0) return out.toString();
        out.deleteCharAt(out.length() - 1);
        out.deleteCharAt(out.length() - 1);
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

    public Set<GameRegistration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<GameRegistration> registrations) {
        this.registrations = registrations;
    }
}
