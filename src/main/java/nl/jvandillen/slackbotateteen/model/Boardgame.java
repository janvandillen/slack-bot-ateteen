package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Boardgame {

    @GeneratedValue
    @Id
    private int id;

    private String name;
    private int maxPlayers;
    private int minPlayers;
    private boolean async;
    private boolean liveOnline;
    private String website;
    private String category;

    public Boardgame(String name, int maxPlayers, int minPlayers, boolean async, boolean liveOnline, String website, String category) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.async = async;
        this.liveOnline = liveOnline;
        this.website = website;
        this.category = category;
    }
}
