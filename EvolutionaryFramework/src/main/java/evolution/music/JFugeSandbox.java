package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("71/0.0625 69/0.0625 68/0.125 69/0.0625 59/0.0625 68/0.0625 " +
                "78/0.0625 71/0.0625 73/0.0625 73/0.0625 71/0.0625 69/0.0625 71/0.0625 59/0.0625 61/0.0625 " +
                "59/0.0625 61/0.125 61/0.0625 59/0.125 59/0.0625 62/0.0625 62/0.0625 64/0.0625 61/0.125 59/0.0625 " +
                "62/0.0625 64/0.0625 72/0.0625 68/0.0625 64/0.0625 71/0.0625 73/0.0625 73/0.0625 71/0.0625 73/0.0625 " +
                "76/0.0625 68/0.0625 66/0.0625 68/0.0625 69/0.0625 68/0.0625 64/0.0625 68/0.0625 R/0.0625 66/0.0625 " +
                "66/0.0625 66/0.125 73/0.0625 76/0.0625 74/0.0625 76/0.125 74/0.0625 73/0.0625 73/0.0625 74/0.0625 73/0.0625 64/0.0625 52/0.0625 ");
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
