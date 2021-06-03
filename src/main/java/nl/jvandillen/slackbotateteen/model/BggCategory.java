package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class BggCategory {

    @Id
    int id;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    Set<CategoryRating> ratings;

    String Type;
    String name;

    public BggCategory() {
    }

    public BggCategory(int id, String type, String name) {
        this.id = id;
        Type = type;
        this.name = name;
    }
}
