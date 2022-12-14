package gamePlay;

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
    final private MediaPlayer soundWin;
    final private MediaPlayer soundLose;

    public SoundGame() {
        Media newSound = new Media(new File("res/resource/music/Sounds/Player/playerMove.wav").toURI().toString());
        soundPlayerMove = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Sounds/Player/Player Dead.wav").toURI().toString());
        soundPlayerDead = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Music/sound_game.mp3").toURI().toString());
        musicMenu = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Music/res_sounds_won.mp3").toURI().toString());
        soundWin = new MediaPlayer(newSound);
        newSound = new Media(new File("res/resource/music/Music/res_sounds_lost.mp3").toURI().toString());
        soundLose = new MediaPlayer(newSound);

        soundPlayerMove.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                soundPlayerMove.seek(Duration.ZERO);
            }
        });
    }

    //sound move.
    public void playSoundMove(List<KeyCode> events) {
        if (events.contains(KeyCode.UP) || events.contains(KeyCode.DOWN)
                || events.contains(KeyCode.LEFT) || events.contains(KeyCode.RIGHT)) {
            soundPlayerMove.setMute(false);
            soundPlayerMove.play();
        } else {
            soundPlayerMove.setMute(true);
        }
    }

    //nhân vật chết.
    public void playSoundPlayerDead(Map map) {
        if (map.player.isDestroy()) {
            soundPlayerDead.play();
        }
    }

    public void playSoundWin() {
        soundWin.play();
    }

    public void playSoundLose() {
        soundLose.play();
    }

    //tiếng game.
    public void playSoundMenu() {
        musicMenu.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                musicMenu.seek(Duration.ZERO);
            }
        });
        musicMenu.play();
    }

    // play sound.
    public void playSound(Map map, List<KeyCode> events) {
        musicMenu.stop();
        playSoundMove(events);
        playSoundPlayerDead(map);
        for (int i = 0; i < map.boms.size(); i++) {
            map.boms.get(i).playSound();
        }
    }

    //dừng tiếng menu.
    public void closeMenu() {
        musicMenu.stop();
    }



    //dừng tiếng game.
    public void close() {
        soundWin.stop();
        soundLose.stop();
        soundPlayerDead.stop();
        soundPlayerMove.stop();
    }
}
