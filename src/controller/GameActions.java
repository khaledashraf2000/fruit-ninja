package controller;

import controller.objects.GameObject;

public interface GameActions {
    /*
     *@return created game object
     */
    GameObject createGameObject();

    /*
     *saves the current state of the game
     */
    void saveGame();

    /*
     * loads the last saved state of the game
     */
    void loadGame();

    /*
     *resets the game to its initial state
     */
    void resetGame();

    /*
     *new game
     */
    void newGame();
}
