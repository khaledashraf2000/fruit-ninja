package controller;

import controller.entities.Player;
import controller.objects.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.DataHandle;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;

@XmlRootElement(name = "game")
@XmlAccessorType(XmlAccessType.FIELD)
public class Game implements GameActions {

    private static final ObservableList<Sliceable> gameObjects = FXCollections.observableArrayList();
    private static Game game;
    @XmlElement(name = "maximumScore")
    private int maximumScore;

    private Game() {

    }

    public static Game getGameInstance() {
        if (game == null) {
            game = new Game();
        }

        return game;
    }

    public void reset() {
        game = null;
    }

    public static ObservableList<Sliceable> getGameObjects() {
        return gameObjects;
    }

    public static void play() throws IOException {
        Font.loadFont(Game.class.getResourceAsStream("/view/resources/go3v2.ttf"), 40);
        Parent root = FXMLLoader.load(Game.class.getResource("/view/windows/main-menu.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Fruit Ninja");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public Sliceable createGameObject() {
        switch ((int) (Math.random() * (8))) {
            case 0:
                return new FatalBomb();
            case 1:
                return new DangerousBomb();
            case 2:
                return new Orange();
            case 3:
                return new Orange();
            case 4:
                return new Kiwi();
            case 5:
                return new Kiwi();
            case 6:
                return new Pom();
            case 7:
                return new Pom();
            case 8:
                return new Kiwi();
        }
        return null;
    }

    @Override
    public void saveGame() {
        try {
            DataHandle.save(game);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadGame() {
        Player.getPlayerInstance();
        game = DataHandle.load();
    }

    @Override
    public void resetGame() {
        this.setMaximumScore(0);
        try {
            DataHandle.save(game);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void newGame() {

    }

    public long getMaximumScore() {
        return maximumScore;
    }

    public void setMaximumScore(int maximumScore) {
        this.maximumScore = maximumScore;
    }

    public void endGame() {
        game = null;
    }
}
