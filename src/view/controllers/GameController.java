package view.controllers;

import controller.Game;
import controller.Observer;
import controller.entities.Player;
import controller.objects.GameObject;
import controller.objects.Sliceable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Observer, Initializable {

    @FXML
    public AnchorPane gameCanvas;
    @FXML
    public ImageView lives;
    @FXML
    public Label currentScore;
    @FXML
    public Label maxScore;
    @FXML
    public Label timerLabel;
    private float objCreationRate;
    private Timeline throwTimeline, timerTimeline;
    private boolean isPaused = false;
    private int globalTimer;
    private static KeyFrame addObjKeyFrame;
    private static KeyFrame timerKeyFrame;
    private static GameController instance;
    private final Image[] livesImages = new Image[4];

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
        maxScore.setText("Best: " + Game.getGameInstance().getMaximumScore());
        instance = this;
        globalTimer = 0;
        objCreationRate = 0.5f;
        throwPeriodic();
        throwTimeline.play();
        timer();
        timerTimeline.play();
        gameCanvas.setOnMouseEntered(e -> {
            gameCanvas.requestFocus();
        });
        gameCanvas.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                if (!isPaused)
                    pauseGame();
                else
                    resumeGame();
        });
        Player.getPlayerInstance().register(this);
    }

    private void load() {
        Game.getGameInstance().loadGame();
        String path = "/view/resources/";
        for (int i = 0; i <= 3; i++) {
            livesImages[i] = new Image(path + "lives" + i + ".png");
        }
    }

    private void throwPeriodic() {
        addObjKeyFrame = new KeyFrame(Duration.seconds(objCreationRate), e -> {
            throwGameObject();
        });
        throwTimeline = new Timeline(addObjKeyFrame);
        throwTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void timer() {
        timerKeyFrame = new KeyFrame(Duration.seconds(1), e -> {
            globalTimer++;
            if (globalTimer / 60 < 1) {
                if (globalTimer % 60 < 10) {
                    timerLabel.setText("0" + (globalTimer / 60) + ":0" + globalTimer % 60);
                } else {
                    timerLabel.setText("0" + (globalTimer / 60) + ":" + globalTimer % 60);
                }
            } else {
                if (globalTimer % 60 < 10) {
                    timerLabel.setText((globalTimer / 60) + ":0" + globalTimer % 60);
                } else {
                    timerLabel.setText((globalTimer / 60) + ":" + globalTimer % 60);
                }
            }
        });
        timerTimeline = new Timeline(timerKeyFrame);
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void throwGameObject() {
        Sliceable gameObject = Game.getGameInstance().createGameObject();
        Game.getGameObjects().add(gameObject);
        gameCanvas.getChildren().add(gameObject.getNode());
        gameObject.getNode().setTranslateY(700);
        gameCanvas.setOnDragDetected(e -> gameCanvas.startFullDrag());
        gameObject.getNode().setOnMouseDragEntered(e -> {
            gameObject.slice();
        });
        gameObject.move();
    }

    private void pauseGame() {
        //TODO: add pause game menu here
        if (!isPaused) {
            isPaused = true;
            gameCanvas.setMouseTransparent(true);
            throwTimeline.pause();
            timerTimeline.pause();
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/windows/pause-menu.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Sliceable gameObject : Game.getGameObjects()) {
                gameObject.getTransition().pause();
            }
        }
    }

    private void resumeGame() {
        if (isPaused) {
            isPaused = false;
            gameCanvas.setMouseTransparent(false);
            throwTimeline.play();
            timerTimeline.play();
            for (Sliceable gameObject : Game.getGameObjects()) {
                gameObject.getTransition().play();
            }
        }
    }

    public void endGame() {
        Platform.runLater(() -> {
            /*for (Sliceable gameObject : Game.getGameObjects()) {
                gameObject.fade();
            }
             */
            Game.getGameObjects().removeAll();
            gameCanvas.getChildren().removeAll();
            throwTimeline.stop();
            timerTimeline.stop();
            Player.getPlayerInstance().unregister(this);
            Game.getGameInstance().saveGame();
            try {
                Parent root;
                root = FXMLLoader.load(getClass().getResource("/view/windows/game-over.fxml"));
                Stage stage = (Stage) gameCanvas.getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void checkMovedOffScreen(Sliceable gameObject) {
        if (gameObject.hasMovedOffScreen()) {
            if (!gameObject.isSliced() && gameObject.getObjectType().equals(GameObject.ObjectType.Fruit)
                    && gameCanvas.getChildren().contains(gameObject.getNode())) {
                Player.getPlayerInstance().dispenseLive(1);
            }
            gameCanvas.getChildren().remove(gameObject.getNode());
        }
    }

    @Override
    public void update(int lives, int score) {
        System.out.println("Lives: " + Player.getPlayerInstance().getLives() + " Score: " + Player.getPlayerInstance().getScore());
        this.lives.setImage(livesImages[lives]);
        this.currentScore.setText("Score: " + score);
        if (score > Game.getGameInstance().getMaximumScore()) {
            Game.getGameInstance().setMaximumScore(score);
            this.maxScore.setText("Best: " + score);
        }
    }

}
