package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CategoryRating {

    @GeneratedValue
    @Id
    public int id;

    @ManyToOne
    BggCategory category;

    @ManyToOne
    User player;

    int score;
}
