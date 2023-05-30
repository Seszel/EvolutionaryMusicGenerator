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
                "76/0.8125 83/0.0625 62/0.0625 73/0.0625 64/0.25 83/0.5625 78/0.125 83/0.0625 81/0.5625 61/0.25 73/0.125 80/0.0625 81/0.1875 62/0.25 62/0.25 64/0.0625 74/0.1875 78/0.0625 "
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
