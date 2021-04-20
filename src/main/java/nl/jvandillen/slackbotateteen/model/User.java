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
    @OneToMany(mappedBy = "player",fetch = FetchType.EAGER)
    Set<GameRegistration> registrations;

    public String name;

    public User() {
    }

    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
