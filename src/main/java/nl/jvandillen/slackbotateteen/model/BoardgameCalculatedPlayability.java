package nl.jvandillen.slackbotateteen.model;

import org.javatuples.Pair;

import java.util.List;

public class BoardgameCalculatedPlayability {

    Boardgame boardgame;
    List<Pair<User, Float>> potentialPlayers;
    List<Pair<User, Float>> extraPlayers;
    float eligibility;

    public BoardgameCalculatedPlayability(Boardgame boardgame, List<Pair<User, Float>> userRating) {
        this.boardgame = boardgame;

        userRating.sort((o1, o2) -> {
            float c = o1.getValue1() - o2.getValue1();
            if (c > 0) return -1;
            if (c < 0) return 1;
            return 0;
        });

        potentialPlayers = userRating.subList(0, boardgame.getMinPlayers());
        extraPlayers = userRating.subList(boardgame.getMinPlayers(), Math.min(boardgame.getMaxPlayers(),userRating.size()));

        eligibility = 0;
        for (Pair<User,Float> ur: userRating) {
            eligibility += ur.getValue1();
        }
        eligibility = eligibility/userRating.size();

    }

    public Boardgame getBoardgame() {
        return boardgame;
    }

    public List<Pair<User, Float>> getPotentialPlayers() {
        return potentialPlayers;
    }

    public List<Pair<User, Float>> getExtraPlayers() {
        return extraPlayers;
    }

    public float getEligibility() {
        return eligibility;
    }
}
