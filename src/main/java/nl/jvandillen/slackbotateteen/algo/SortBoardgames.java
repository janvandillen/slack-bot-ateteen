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
            List<Pair<User, Float>> userEligibility = new ArrayList<>();
            for (User u : users) {
                float el = u.getBoardgameEligibility(b);
                if (el > 0) userEligibility.add(new Pair<>(u,el));
            }
            if (userEligibility.size() >= b.getMinPlayers()) out.add(new BoardgameCalculatedPlayability(b, userEligibility));
        }
        out.sort(((o1, o2) -> {
            float c = o1.getEligibility() - o2.getEligibility();
            if (c > 0) return -1;
            if (c < 0) return 1;
            return 0;
        }));
        return out;
    }
}
