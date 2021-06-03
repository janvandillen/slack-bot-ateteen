package nl.jvandillen.slackbotateteen.controller;

import nl.jvandillen.slackbotateteen.app.dao.*;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
import nl.jvandillen.slackbotateteen.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseController {

    @Autowired
    private BoardgameCategoryDao boardgameCategoryDao;
    @Autowired
    private BoardgameDao boardgameDao;
    @Autowired
    private BoardgameToBggCategoryDao boardgameToBggCategoryDao;
    @Autowired
    private GameDao gameDao;
    @Autowired
    private GameRegistrationDao gameRegistrationDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BoardgameRatingDao boardgameRatingDao;
    @Autowired
    private CategoryRatingDao categoryRatingDao;

    public List<Boardgame> getAllBoardGame() {
        return boardgameDao.findAll();
    }

    public List<Boardgame> getAllBoardGame(boolean sorted) {
        if (sorted) return boardgameDao.findByOrderByName();
        return getAllBoardGame();
    }

    public List<Game> getRunningGames() {
        return gameDao.findByRunningTrue();
    }

    public List<Game> getFinishedGames() {
        return gameDao.findTop20ByRunningFalseOrderByClosedDesc();
    }

    public Game findGame(int id) {
        return gameDao.findById(id).orElseThrow();
    }

    public User findUser(String id) {
        return userDao.findById(id).orElseThrow();
    }

    public Boardgame findBoardgame(int id) {
        return boardgameDao.findById(id).orElseThrow();
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void save(User user) {
        gameRegistrationDao.saveAll(user.getRegistrations());
        boardgameRatingDao.saveAll(user.getBoardgameRatings());
        categoryRatingDao.saveAll(user.getCategoryRatings());
        userDao.save(user);
    }

    public void save(Boardgame boardgame) {
        boardgameDao.save(boardgame);
    }

    public void save(Game game) {
        gameDao.save(game);
    }

    public boolean boardgameExists(int id) {
        return boardgameDao.existsById(id);
    }

    public void deleteBoardgame(int id) {
        boardgameDao.deleteById(id);
    }
}
