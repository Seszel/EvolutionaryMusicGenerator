package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("70/0.5 82/0.25 57/0.0625 73/0.0625 81/0.0625 81/0.0625 65/0.5625 R/0.0625 53/0.25 53/0.125 79/0.5625 73/0.3125 73/0.0625 63/0.0625 59/0.25 49/0.5625 65/0.0625 66/0.0625 55/0.0625 ");
        melody.setTempo(90);
        player.play(melody);

        try {
            File filePath = new File("sandboxMidi.mid");
            MidiFileManager.savePatternToMidi(melody, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
