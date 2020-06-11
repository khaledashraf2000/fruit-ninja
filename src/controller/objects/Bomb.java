package controller.objects;

import controller.entities.Player;
import javafx.scene.media.MediaPlayer;

public class Bomb extends Sliceable {
    int risk;

    @Override
    public void slice() {
        if (!isSliced()) {
            //playMedia();
            Player.getPlayerInstance().dispenseLive(risk);
            this.sliced = true;
        }

    }

    protected void playMedia() {
        MediaPlayer mediaPlayer = new MediaPlayer(this.media);
        mediaPlayer.setAutoPlay(true);
    }

    @Override
    public ObjectType getObjectType() {
        return ObjectType.Bomb;
    }
}

