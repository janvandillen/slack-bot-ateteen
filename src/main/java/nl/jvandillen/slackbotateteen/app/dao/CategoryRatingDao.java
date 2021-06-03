package nl.jvandillen.slackbotateteen.app.dao;

import nl.jvandillen.slackbotateteen.model.CategoryRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRatingDao extends JpaRepository<CategoryRating, Integer> {
}
