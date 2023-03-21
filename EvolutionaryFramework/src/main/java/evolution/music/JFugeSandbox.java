package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("0/0.25 68/0.0625 60/0.0625 60/0.125 72/0.125 74/0.0625 72/0.125 79/0.0625 84/0.0625 60/0.0625 55/0.0625 67/0.0625 71/0.125 59/0.125 62/0.0625 R/0.3125 55/0.125 55/0.0625 52/0.0625 64/0.125 69/0.125 69/0.0625 69/0.125 60/0.0625 81/0.0625 76/0.1875 69/0.125 71/0.0625 69/0.0625 81/0.1875 53/0.3125 58/0.3125 57/0.0625 69/0.0625 63/0.0625 ");

        player.play(melody);

        try {
            File filePath = new File("sandboxMidi.mid");
            MidiFileManager.savePatternToMidi(melody, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
