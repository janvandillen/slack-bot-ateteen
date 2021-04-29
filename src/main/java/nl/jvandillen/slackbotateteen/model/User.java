package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class User {

    @Id
    public String userID;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<GameRegistration> registrations;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<BoardgameRating> boardgameRatings;

    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER)
    Set<CategoryRating> categoryRatings;

    String name;
    int defaultRating;
    int maxGames;
    int minGames;
    int DefaultMaxSimilarGames;

    public User() {
    }

    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GameRegistration> getRegistrations() {
        return registrations;
    }

    public Set<BoardgameRating> getBoardgameRatings() {
        return boardgameRatings;
    }

    public Set<CategoryRating> getCategoryRatings() {
        return categoryRatings;
    }

    public int getDefaultRating() {
        return defaultRating;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public int getMinGames() {
        return minGames;
    }

    public int getDefaultMaxSimilarGames() {
        return DefaultMaxSimilarGames;
    }
}
