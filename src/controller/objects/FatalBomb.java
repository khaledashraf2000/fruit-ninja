package controller.objects;

import controller.entities.Player;

public class FatalBomb extends Bomb {

    public FatalBomb() {
        risk = Player.getPlayerInstance().getLives();
        this.name = "bombFatal";
        this.load();
    }

}
