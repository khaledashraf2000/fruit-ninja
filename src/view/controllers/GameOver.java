package view.controllers;

import controller.entities.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GameOver {

    public Button playAgain;
    public Button exit;

    public void playAgain(ActionEvent mouseEvent) {
        System.out.println("NEW GAME");
        Player.getPlayerInstance().resetPlayer();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/windows/game.fxml"));
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.exit(0);
    }
}
