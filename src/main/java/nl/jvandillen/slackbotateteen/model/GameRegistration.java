package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GameRegistration {

    @GeneratedValue
    @Id
    public int id;

    @ManyToOne
    Game game;

    @ManyToOne
    User player;

    int score;
    boolean win;

    public GameRegistration() {
    }

    public GameRegistration(Game game, User player) {
        this.game = game;
        this.player = player;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public int getScore() {
        return score;
    }
}
