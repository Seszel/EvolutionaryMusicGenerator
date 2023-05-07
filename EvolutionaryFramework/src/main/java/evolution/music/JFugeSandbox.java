package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.Chord;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Note;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
//        Pattern melody = new Pattern("0/0.1875 74/0.125 69/0.1875 69/0.125 69/0.1875 69/0.0625 59/0.0625 71/0.0625 59/0.0625 71/0.25 64/0.125 71/0.0625 " +
//                "71/0.125 68/0.125 80/0.125 80/0.0625 80/0.0625 73/0.0625 73/0.375 " +
//                "66/0.0625 73/0.0625 66/0.0625 66/0.0625 78/0.1875 78/0.0625 76/0.0625 69/0.1875 74/0.3125 74/0.375 66/0.0625 54/0.0625 ");
//        melody.setTempo(90);
//        player.play(melody);

        Pattern introRhythm = new Pattern("T180 V9 [CLOSED_HI_HAT]x Rx [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt");

        Pattern mainChords = new Pattern("T180 V0 D4Min9hqit Ri G3Maj13hqi Ri C4Maj9wh Rh");
        mainChords.add("D4Minhqit Ri G4Majhqi Ri C4Majwh Rht");

        Pattern pianoTouch = new Pattern("T180 V1 Rw | Rw | Rhi | G4qi G3s A3is CMajis ri");
        pianoTouch.add("Rw | Rw | Rhi | G4s C5wa100d0");
        Pattern introOnce = new Pattern(mainChords, pianoTouch);

        player.play(introOnce, introRhythm.repeat(8));
//        try {
//            File filePath = new File("sandboxMidi.mid");
//            MidiFileManager.savePatternToMidi(melody, filePath);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }

    }

}
