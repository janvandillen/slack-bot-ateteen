package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Objects;
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

    public void setRegistrations(Set<GameRegistration> registrations) {
        this.registrations = registrations;
    }

    public Set<BoardgameRating> getBoardgameRatings() {
        return boardgameRatings;
    }

    public void setBoardgameRatings(Set<BoardgameRating> boardgameRatings) {
        this.boardgameRatings = boardgameRatings;
    }

    public Set<CategoryRating> getCategoryRatings() {
        return categoryRatings;
    }

    public void setCategoryRatings(Set<CategoryRating> categoryRatings) {
        this.categoryRatings = categoryRatings;
    }

    public int getDefaultRating() {
        return defaultRating;
    }

    public void setDefaultRating(int defaultRating) {
        this.defaultRating = defaultRating;
    }

    public int getMaxGames() {
        return maxGames;
    }

    public void setMaxGames(int maxGames) {
        this.maxGames = maxGames;
    }

    public int getMinGames() {
        return minGames;
    }

    public void setMinGames(int minGames) {
        this.minGames = minGames;
    }

    public int getDefaultMaxSimilarGames() {
        return DefaultMaxSimilarGames;
    }

    public void setDefaultMaxSimilarGames(int defaultMaxSimilarGames) {
        DefaultMaxSimilarGames = defaultMaxSimilarGames;
    }

    public void setBoardgameRating(Boardgame boardgame, int rating) {
        for (BoardgameRating br :
                boardgameRatings) {
            if (br.boardgame.id == boardgame.id) {
                br.setRating(rating);
                return;
            }
        }

        BoardgameRating boardgameRating = new BoardgameRating(boardgame, this);
        boardgameRating.setRating(rating);
        boardgameRatings.add(boardgameRating);
    }

    public void setBoardgameMaxGames(Boardgame boardgame, int maxGames) {
        for (BoardgameRating br :
                boardgameRatings) {
            if (br.boardgame.id == boardgame.id) {
                br.setMaxGames(maxGames);
                return;
            }
        }

        BoardgameRating boardgameRating = new BoardgameRating(boardgame, this);
        boardgameRating.setMaxGames(maxGames);
        boardgameRatings.add(boardgameRating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID);
    }
}
