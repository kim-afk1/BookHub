import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javax.sound.sampled.*;
import javazoom.jl.player.Player;

public class AudioPlayer {
    private static AudioPlayer instance;

    // For WAV files
    private Clip clip;
    private long clipPosition = 0;

    // For MP3 files
    private Player mp3Player;
    private Thread mp3Thread;
    private File currentFile;
    private long mp3Position = 0; // in milliseconds
    private long mp3Duration = 0;

    private boolean isPlaying = false;
    private boolean isPaused = false;
    private boolean isMp3 = false;
    private float volume = 0.8f;
    private List<PlayerListener> listeners = new ArrayList<>();

    private AudioPlayer() {}

    public static synchronized AudioPlayer getInstance() {
        if (instance == null) {
            instance = new AudioPlayer();
        }
        return instance;
    }

    public void loadSong(File file) throws Exception {
        stop();
        currentFile = file;
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".mp3")) {
            isMp3 = true;
            loadMP3(file);
        } else if (fileName.endsWith(".wav")) {
            isMp3 = false;
            loadWAV(file);
        } else {
            throw new Exception("Unsupported file format. Only MP3 and WAV are supported.");
        }

        notifyListeners();
    }

    private void loadWAV(File file) throws Exception {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clipPosition = 0;
            setVolume(volume);
        } catch (Exception e) {
            throw new Exception("Failed to load WAV file: " + e.getMessage());
        }
    }

    private void loadMP3(File file) throws Exception {
        try {
            mp3Position = 0;
            // Estimate duration (this is approximate)
            mp3Duration = (long) (file.length() / 16); // Rough estimate: 128kbps MP3
        } catch (Exception e) {
            throw new Exception("Failed to load MP3 file: " + e.getMessage());
        }
    }

    public void play() {
        if (isMp3) {
            playMP3();
        } else {
            playWAV();
        }
        isPlaying = true;
        isPaused = false;
        notifyListeners();
    }

    private void playWAV() {
        if (clip != null) {
            clip.setMicrosecondPosition(clipPosition);
            clip.start();
        }
    }

    private void playMP3() {
        if (currentFile == null) return;

        mp3Thread = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(currentFile);

                // Skip to saved position if paused
                if (isPaused && mp3Position > 0) {
                    long bytesToSkip = (long) (mp3Position * 16); // Approximate
                    fis.skip(bytesToSkip);
                }

                mp3Player = new Player(fis);

                long startTime = System.currentTimeMillis();
                mp3Player.play();

                if (isPlaying) {
                    mp3Position = 0;
                    isPlaying = false;
                    notifyListeners();
                }

                fis.close();
            } catch (Exception e) {
                System.err.println("Error playing MP3: " + e.getMessage());
            }
        });
        mp3Thread.start();
    }

    public void pause() {
        if (isMp3) {
            pauseMP3();
        } else {
            pauseWAV();
        }
        isPlaying = false;
        isPaused = true;
        notifyListeners();
    }

    private void pauseWAV() {
        if (clip != null && clip.isRunning()) {
            clipPosition = clip.getMicrosecondPosition();
            clip.stop();
        }
    }

    private void pauseMP3() {
        if (mp3Player != null) {
            mp3Player.close();
            mp3Player = null;
        }
        if (mp3Thread != null) {
            mp3Thread.interrupt();
        }
    }

    public void stop() {
        if (isMp3) {
            stopMP3();
        } else {
            stopWAV();
        }
        isPlaying = false;
        isPaused = false;
        notifyListeners();
    }

    private void stopWAV() {
        if (clip != null) {
            clip.stop();
            clip.close();
            clipPosition = 0;
        }
    }

    private void stopMP3() {
        if (mp3Player != null) {
            mp3Player.close();
            mp3Player = null;
        }
        if (mp3Thread != null) {
            mp3Thread.interrupt();
        }
        mp3Position = 0;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public long getCurrentPosition() {
        if (isMp3) {
            return mp3Position * 1000; // Convert to microseconds
        } else {
            return clip != null ? clip.getMicrosecondPosition() : 0;
        }
    }

    public long getDuration() {
        if (isMp3) {
            return mp3Duration * 1000; // Convert to microseconds
        } else {
            return clip != null ? clip.getMicrosecondLength() : 0;
        }
    }

    public void setPosition(long position) {
        if (isMp3) {
            mp3Position = position / 1000; // Convert from microseconds
            if (isPlaying) {
                pause();
                play();
            }
        } else {
            if (clip != null) {
                clipPosition = position;
                clip.setMicrosecondPosition(position);
            }
        }
    }

    public void setVolume(float vol) {
        volume = Math.max(0, Math.min(1, vol));

        if (!isMp3 && clip != null && clip.isOpen()) {
            try {
                FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                volumeControl.setValue(dB);
            } catch (Exception e) {
                System.err.println("Volume control not supported");
            }
        }
        // Note: JLayer doesn't support volume control easily
    }

    public float getVolume() {
        return volume;
    }

    public void addListener(PlayerListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (PlayerListener listener : listeners) {
            listener.onPlayerStateChanged();
        }
    }

    public interface PlayerListener {
        void onPlayerStateChanged();
    }
}