package nl.jvandillen.slackbotateteen.controller;

import nl.jvandillen.slackbotateteen.app.dao.*;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.Game;
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

    public List<Boardgame> getAllBoardGame() {
        return boardgameDao.findAll();
    }

    public List<Game> getRunningGames() {
        return gameDao.findByRunningTrue();
    }

    public List<Game> getFinishedGames() {
        return gameDao.findByRunningFalse();
    }

    public Game findGame(int id) {
        return gameDao.findById(id).orElseThrow();
    }
}
