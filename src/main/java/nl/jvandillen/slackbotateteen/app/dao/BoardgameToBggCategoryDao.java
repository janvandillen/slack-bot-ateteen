package nl.jvandillen.slackbotateteen.app.dao;

import nl.jvandillen.slackbotateteen.model.BoardgameToBggCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardgameToBggCategoryDao extends JpaRepository<BoardgameToBggCategory, Integer> {
}
