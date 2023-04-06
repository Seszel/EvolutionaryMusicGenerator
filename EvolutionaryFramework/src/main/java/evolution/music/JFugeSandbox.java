package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("66/0.1875 64/0.0625 64/0.1875 62/0.0625 73/0.125 73/0.0625 64/0.1875 64/0.0625 73/0.0625 " +
                "68/0.125 73/0.0625 71/0.125 71/0.0625 59/0.125 59/0.1875 64/0.0625 64/0.125 69/0.0625 71/0.0625 69/0.125 71/0.125 73/0.0625 73/0.125 73/0.0625 73/0.1875 " +
                "66/0.0625 66/0.0625 66/0.0625 61/0.0625 60/0.0625 62/0.125 62/0.1875 64/0.125 74/0.1875 69/0.0625 69/0.0625 57/0.0625 57/0.125 62/0.0625 ");
        melody.setTempo(90);
        player.play(melody);

//        try {
//            File filePath = new File("sandboxMidi.mid");
//            MidiFileManager.savePatternToMidi(melody, filePath);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

    }

}
