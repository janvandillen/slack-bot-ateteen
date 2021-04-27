package nl.jvandillen.slackbotateteen.controller;

import nl.jvandillen.slackbotateteen.app.dao.BoardgameCategoryDao;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameDao;
import nl.jvandillen.slackbotateteen.app.dao.BoardgameToBggCategoryDao;
import nl.jvandillen.slackbotateteen.model.BggCategory;
import nl.jvandillen.slackbotateteen.model.Boardgame;
import nl.jvandillen.slackbotateteen.model.BoardgameToBggCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Optional;

@Component
public class BggApiController {

    @Autowired
    BoardgameCategoryDao boardgameCategoryDao;
    @Autowired
    BoardgameToBggCategoryDao boardgameToBggCategoryDao;
    @Autowired
    BoardgameDao boardgameDao;


    public Boardgame getBoardgameFromBgg(int id) throws ParserConfigurationException, IOException, SAXException {

        Boardgame boardgame = new Boardgame(id);
        boardgameDao.save(boardgame);

        // request url
        String url = "https://api.geekdo.com/xmlapi2/thing?id=" + id;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(url);
        document.getDocumentElement().normalize();
        Node items = document.getFirstChild();
        Node item = items.getFirstChild();
        NodeList itemChildNodes = item.getChildNodes();
        for (int i = 0; i < itemChildNodes.getLength(); i++) {
            Node node = itemChildNodes.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                switch (node.getNodeName()) {
                    case "name":
                        if (node.getAttributes().getNamedItem("type").getNodeValue().equals("primary"))
                            boardgame.setName(node.getAttributes().getNamedItem("value").getNodeValue());
                        break;
                    case "minplayers":
                        boardgame.setMinPlayers(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
                        break;
                    case "maxplayers":
                        boardgame.setMaxPlayers(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
                        break;
                    case "playingtime":
                        boardgame.setPlayingtime(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
                        break;
                    case "minplaytime":
                        boardgame.setMinplaytim(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
                        break;
                    case "maxplaytime":
                        boardgame.setMaxplaytime(Integer.parseInt(node.getAttributes().getNamedItem("value").getNodeValue()));
                        break;
                    case "link":
                        boardgame.addCategory(getCategoryID(boardgame, node));
                        break;
                }
            }
        }
        return boardgame;
    }

    private BoardgameToBggCategory getCategoryID(Boardgame boardgame, Node node) {
        int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
        BggCategory category;
        Optional<BggCategory> opt = boardgameCategoryDao.findById(id);
        if (opt.isPresent()) {
            category = opt.get();
        } else {
            String type = node.getAttributes().getNamedItem("type").getNodeValue();
            String name = node.getAttributes().getNamedItem("value").getNodeValue();
            category = new BggCategory(id, type, name);
            boardgameCategoryDao.save(category);
        }

        BoardgameToBggCategory boardgameToBggCategory = new BoardgameToBggCategory(boardgame, category);
        boardgameToBggCategoryDao.save(boardgameToBggCategory);

        return boardgameToBggCategory;
    }

}
