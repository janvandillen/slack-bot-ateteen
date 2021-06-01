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
    boolean ratingFixed;
    int maxGames;
    boolean maxGamesFixed;

    public BoardgameRating() {
    }

    public BoardgameRating(Boardgame boardgame, User player) {
        this.boardgame = boardgame;
        this.player = player;
    }

    public Boardgame getBoardgame() {
        return boardgame;
    }

    public User getPlayer() {
        return player;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
        ratingFixed = true;
    }

    public boolean isRatingFixed() {
        return ratingFixed;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(int maxGames) {
        this.maxGames = maxGames;
        maxGamesFixed = true;
    }

    public boolean isMaxGamesFixed() {
        return maxGamesFixed;
    }
}
