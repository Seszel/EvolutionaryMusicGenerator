package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("0/0.1875 74/0.125 69/0.1875 69/0.125 69/0.1875 69/0.0625 59/0.0625 71/0.0625 59/0.0625 71/0.25 64/0.125 71/0.0625 " +
                "71/0.125 68/0.125 80/0.125 80/0.0625 80/0.0625 73/0.0625 73/0.375 " +
                "66/0.0625 73/0.0625 66/0.0625 66/0.0625 78/0.1875 78/0.0625 76/0.0625 69/0.1875 74/0.3125 74/0.375 66/0.0625 54/0.0625 ");
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
