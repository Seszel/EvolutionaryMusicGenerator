package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;


import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern melody = new Pattern("C D E F G A H C");

        player.play(melody);

        try {
            File filePath = new File("sandboxMidi.mid");
            MidiFileManager.savePatternToMidi(melody, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
