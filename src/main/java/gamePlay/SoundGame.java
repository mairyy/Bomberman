package main.java.gamePlay;

import javafx.scene.input.KeyCode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.List;
import java.io.File;


public class SoundGame {
    final private MediaPlayer soundPlayerMove;
    final private MediaPlayer soundPlayerDead;
    final private MediaPlayer musicMenu;
    final private MediaPlayer soundClick;
    public SoundGame () {
        Media newSound = new Media(new File("res/resource/music/Sounds/Player/playerMove.wav").toURI().toString());
        soundPlayerMove = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Sounds/Player/Player Dead.wav").toURI().toString());
        soundPlayerDead = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Music/sound_game.mp3").toURI().toString());
        musicMenu = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Sounds/Menu/soundClick.wav").toURI().toString());
        soundClick = new MediaPlayer(newSound);
    }

    public void playSoundMove(List<KeyCode> events) {
        if (events.contains(KeyCode.UP) || events.contains(KeyCode.DOWN)
        || events.contains(KeyCode.LEFT) || events.contains(KeyCode.RIGHT)) {
            soundPlayerMove.setMute(false);
            soundPlayerMove.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    soundPlayerMove.seek(Duration.ZERO);
                }
            });
            soundPlayerMove.play();
        } else {
            soundPlayerMove.setMute(true);
        }
    }

    public void playSoundPlayerDead(Map map) {
        if(map.player.isDestroy()) {
            soundPlayerDead.play();
        }
    }

    public void playSoundMenu() {
        musicMenu.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                soundPlayerMove.seek(Duration.ZERO);
            }
        });
        musicMenu.play();
    }

    public void playSound(Map map, List<KeyCode> events) {
        musicMenu.stop();
        playSoundMove(events);
        playSoundPlayerDead(map);
        for(int i = 0; i < map.boms.size(); i++) {
            map.boms.get(i).playSound();
        }
    }
}
