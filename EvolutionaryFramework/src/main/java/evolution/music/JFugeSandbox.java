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
                .setKey("C")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern melody = new Pattern("X[Volume]=13000"
        +
                "52/0.75 55/0.125 60/0.0625 52/0.0625 50/0.5625 62/0.3125 59/0.0625 59/0.0625 57/0.625 52/0.25 36/0.0625 57/0.0625 48/0.0625 60/0.5 48/0.3125 57/0.0625 R/0.0625"
        )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);

        Pattern music = new Pattern(chords, melody);
//        player.play(melody);
        player.play(music);



        try {
            File filePath = new File("random.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
