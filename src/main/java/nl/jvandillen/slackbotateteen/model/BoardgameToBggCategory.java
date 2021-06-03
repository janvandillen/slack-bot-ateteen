package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class BoardgameToBggCategory {

    @GeneratedValue
    @Id
    public int id;

    @ManyToOne
    Boardgame boardgame;

    @ManyToOne
    BggCategory category;

    public BoardgameToBggCategory() {
    }

    public BoardgameToBggCategory(Boardgame boardgame, BggCategory category) {
        this.boardgame = boardgame;
        this.category = category;
    }
}
