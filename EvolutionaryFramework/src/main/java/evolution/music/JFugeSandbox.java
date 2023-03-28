package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("76/0.0625 64/0.0625 64/0.0625 55/0.0625 60/0.0625 55/0.125 55/0.0625 67/0.0625 55/0.0625 50/0.0625 60/0.125 76/0.0625 67/0.0625 64/0.0625 62/0.0625 71/0.0625 74/0.125 74/0.25 59/0.0625 59/0.0625 67/0.0625 76/0.0625 67/0.125 55/0.0625 53/0.0625 52/0.0625 52/0.0625 57/0.0625 57/0.0625 69/0.0625 69/0.0625 69/0.0625 52/0.0625 69/0.0625 81/0.0625 76/0.0625 76/0.125 84/0.0625 72/0.0625 60/0.0625 65/0.125 72/0.125 77/0.0625 77/0.125 68/0.0625 84/0.125 84/0.125 63/0.0625 72/0.0625 60/0.0625 84/0.0625 ");

        player.play(melody);

        try {
            File filePath = new File("sandboxMidi.mid");
            MidiFileManager.savePatternToMidi(melody, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
