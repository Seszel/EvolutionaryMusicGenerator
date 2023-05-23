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
        + "67/0.0625 72/0.125 76/0.0625 64/0.0625 64/0.0625 64/0.125 64/0.0625 67/0.0625 84/0.0625 84/0.1875 84/0.0625 79/0.0625 79/0.375 62/0.125 50/0.0625 55/0.0625 55/0.1875 62/0.0625 67/0.0625 67/0.0625 64/0.0625 64/0.125 64/0.125 64/0.0625 60/0.125 60/0.375 72/0.0625 72/0.0625 72/0.0625 72/0.0625 72/0.125 72/0.375 72/0.0625 72/0.125 77/0.0625 81/0.0625 64/0.0625 "
        )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);

        Pattern music = new Pattern(chords, melody);
        player.play(music);



        try {
            File filePath = new File("random.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
