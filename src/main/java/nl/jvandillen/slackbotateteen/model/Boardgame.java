package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Boardgame {

    @GeneratedValue
    @Id
    public int id;

    public String name;
    private int maxPlayers;
    private int minPlayers;
    private boolean async;
    private boolean liveOnline;
    private String website;
    private String category;

    public Boardgame(){}

    public Boardgame(String name, int maxPlayers, int minPlayers, boolean async, boolean liveOnline, String website, String category) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.async = async;
        this.liveOnline = liveOnline;
        this.website = website;
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public void setLiveOnline(boolean liveOnline) {
        this.liveOnline = liveOnline;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
