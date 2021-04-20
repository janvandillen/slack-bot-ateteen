package nl.jvandillen.slackbotateteen.app.dao;

import nl.jvandillen.slackbotateteen.model.GameRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRegistrationDao extends JpaRepository<GameRegistration, Integer> {
}
