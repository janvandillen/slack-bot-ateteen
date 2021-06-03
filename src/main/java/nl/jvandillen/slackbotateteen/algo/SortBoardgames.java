package nl.jvandillen.slackbotateteen.algo;

import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.BoardgameCalculatedPlayability;
import nl.jvandillen.slackbotateteen.model.User;
import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;



@Component
public class SortBoardgames {

    public List<BoardgameCalculatedPlayability> sort(List<Boardgame> boardgames, List<User> users) {
        List<BoardgameCalculatedPlayability> out = new ArrayList<>();

        for (Boardgame b : boardgames) {
            List<Pair<User, Float>> userRating = new ArrayList<>();
            for (User u : users) {
                float rating = u.getBoardgameRating(b);
                if (u.getMaxGames() <= u.gameAmount() & rating < 6.5 ) continue; //check if player is at max game without having a rating of 7
                int maxSimilarGames = u.getMaxSimilarGames(b);
                if (maxSimilarGames != 0 & maxSimilarGames <= u.gameAmount(b)) continue; //check if the player has similar games
                if (rating > 0.5) userRating.add(new Pair<>(u,rating)); //check if rating is not 0
            }
            if (userRating.size() < b.getMinPlayers()) continue;
            out.add(new BoardgameCalculatedPlayability(b, userRating));
        }
        out.sort(((o1, o2) -> {
            float c = o1.getRating() - o2.getRating();
            if (c > 0) return -1;
            if (c < 0) return 1;
            return 0;
        }));
        return out;
    }
}
