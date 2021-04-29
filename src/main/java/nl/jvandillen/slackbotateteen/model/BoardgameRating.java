package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BoardgameRating {

    @GeneratedValue
    @Id
    public int id;

    @ManyToOne
    Boardgame boardgame;

    @ManyToOne
    User player;

    float rating;
    boolean fixed;
    int maxGames;

    public Boardgame getBoardgame() {
        return boardgame;
    }

    public User getPlayer() {
        return player;
    }

    public float getRating() {
        return rating;
    }

    public boolean isFixed() {
        return fixed;
    }

    public int getMaxGames() {
        return maxGames;
    }
}
