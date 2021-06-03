package nl.jvandillen.slackbotateteen.app.dao;

import nl.jvandillen.slackbotateteen.model.BggCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardgameCategoryDao extends JpaRepository<BggCategory, Integer> {
}
