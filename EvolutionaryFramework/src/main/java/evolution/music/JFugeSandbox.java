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

        StringBuilder durationOfChords = new StringBuilder();
        for (int i=0; i<4; i++){
            durationOfChords.append("$").append(i).append("w ");
        }

        Pattern chords = new ChordProgression("I V vi IV")
                .setKey("A")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern melody = new Pattern("X[Volume]=13000"
        +
                "60/0.0625 65/0.125 66/0.1875 R/0.1875 R/0.0625 61/0.0625 68/0.0625 R/0.125 61/0.0625 52/0.0625 57/0.125 57/0.0625 R/0.0625 52/0.0625 59/0.1875 66/0.125 R/0.0625 72/0.125 65/0.0625 58/0.0625 65/0.0625 60/0.0625 53/0.125 64/0.125 R/0.0625 57/0.25 R/0.0625 51/0.0625 56/0.0625 63/0.0625 70/0.0625 58/0.0625 63/0.3125 64/0.0625 69/0.0625 71/0.0625 64/0.125 69/0.0625 68/0.125 63/0.0625 63/0.0625 57/0.0625 "
                )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);

        Pattern music = new Pattern(chords, melody);
        player.play(melody);
//        player.play(music);



        try {
            File filePath = new File("random.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
