package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Boardgame {

    @Id
    int id;

    String name;
    String shortName;
    int maxPlayers;
    int minPlayers;
    int[] bestPlayerCounts;
    int playingtime;
    int minPlaytime;
    int maxPlaytime;
    boolean async;
    boolean liveOnline;
    String website;
    @OneToMany(mappedBy = "boardgame", fetch = FetchType.EAGER)
    Set<BoardgameToBggCategory> boardgameCategories;
    @OneToMany(mappedBy = "boardgame", fetch = FetchType.EAGER)
    Set<BoardgameRating> ratings;

    public Boardgame() {
    }

    public Boardgame(String name, int maxPlayers, int minPlayers, boolean async, boolean liveOnline, String website, String category) {
        this.name = name;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.async = async;
        this.liveOnline = liveOnline;
        this.website = website;
        //this.category = category;
    }

    public Boardgame(int id) {
        boardgameCategories = new HashSet<>();
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public void addCategory(BoardgameToBggCategory category) {

        boardgameCategories.add(category);
    }

    public void setPlayingtime(int playingtime) {
        this.playingtime = playingtime;
    }

    public void setMinPlaytime(int minplaytim) {
        this.minPlaytime = minplaytim;
    }

    public void setMaxPlaytime(int maxplaytime) {
        this.maxPlaytime = maxplaytime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<BoardgameRating> getRatings() {
        return ratings;
    }
}
