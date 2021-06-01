package nl.jvandillen.slackbotateteen.app.dao;

import nl.jvandillen.slackbotateteen.model.BoardgameRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardgameRatingDao extends JpaRepository<BoardgameRating, Integer> {
}
