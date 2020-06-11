package controller.objects;

import controller.entities.Player;
import javafx.scene.media.MediaPlayer;

public class Fruit extends Sliceable {

    protected int score;

    public long getScore() {
        return score;
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.Fruit;
    }

    @Override
    public void slice() {
        if (!sliced && Player.getPlayerInstance().isAlive()) {
            //playMedia();
            this.fade();
            this.node.setImage(this.images[1]);
            Player.getPlayerInstance().addScore(this.score);
            this.sliced = true;
        }
    }

    protected void playMedia() {
        MediaPlayer mediaPlayer = new MediaPlayer(this.media);
        mediaPlayer.setAutoPlay(true);
    }
}
