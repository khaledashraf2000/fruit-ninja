package controller.objects;

import javafx.scene.Node;

public interface GameObject {
    /**
     * @return the type of game object
     */
    ObjectType getObjectType();

    /**
     * @return X location of game object
     */
    int getXlocation();

    /**
     * @return Y location of game object
     */
    int getYlocation();

    /**
     * @return whether the object is sliced or not
     */
    Boolean isSliced();

    /**
     * @return whether the object is dropped off the screen or not
     */
    Boolean hasMovedOffScreen();

    /**
     * it is used to slice the object
     */
    void slice();

    /**
     * it is used to move the object on the screen
     * using projectile dynamics of motion
     */
    void move();

    /**
     * @return Node: node of the game objec
     */
    Node getNode();

    enum ObjectType {
        Fruit,
        Bomb
    }
}
