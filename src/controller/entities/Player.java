package controller.entities;

import controller.Observer;
import controller.Subject;
import view.controllers.GameController;

import java.util.ArrayList;

public class Player implements Subject {
    private static Player playerInstance = null;
    private final ArrayList<Observer> observers = new ArrayList<>();
    private int score;
    private int lives;
    private boolean alive;

    private Player() {
        alive = true;
        lives = 3;
        score = 0;
    }

    public static Player getPlayerInstance() {
        if (playerInstance == null)
            playerInstance = new Player();

        return playerInstance;
    }

    public void resetPlayer() {
        playerInstance = null;
    }

    public int getLives() {
        return lives;
    }

    private void setLives(int lives) {
        this.lives = lives;
        System.out.println("update called from setLives player");
        notifyObserver();
    }

    public int getScore() {
        return Player.getPlayerInstance().score;
    }

    public void setScore(int score) {
        Player.getPlayerInstance().score = score;
        System.out.println("update called from setScore player");
        notifyObserver();
    }

    public void printScore() {
        System.out.println("Current Score:" + score);
    }

    public void addScore(int score) {
        Player.getPlayerInstance().score += score;
        System.out.println("update called from addScore player");
        notifyObserver();
    }

    public void dispenseLive(int risk) {
        Player.getPlayerInstance().lives -= risk;
        if (lives <= 0) {
            lives = 0;
            die();
        }
        System.out.println("update called from dispenseLive player");
        notifyObserver();
    }

    public void die() {
        lives = 0;
        System.out.println("update called from die player");
        //System.out.println("PLAYER DIE");
        setAlive(false);
        notifyObserver();
        GameController.getInstance().endGame();
    }

    public void printLives() {
        System.out.println("Lives Remaining:" + Player.getPlayerInstance().getLives());
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void register(Observer newObserver) {
        this.observers.add(newObserver);
    }

    @Override
    public void unregister(Observer deletedObserver) {
        this.observers.remove(deletedObserver);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            System.out.println(observer);
            observer.update(lives, score);
        }
    }

}
