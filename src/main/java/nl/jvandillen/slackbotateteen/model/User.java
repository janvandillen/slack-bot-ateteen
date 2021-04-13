package nl.jvandillen.slackbotateteen.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    public final String userID;

    public User(String u) {
        this.userID = u;
    }
}
