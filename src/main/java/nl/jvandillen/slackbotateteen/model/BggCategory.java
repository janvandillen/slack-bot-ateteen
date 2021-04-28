package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BggCategory {

    @Id
    int id;

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
