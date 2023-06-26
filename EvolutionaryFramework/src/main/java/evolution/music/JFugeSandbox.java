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

        Pattern chords = new ChordProgression("I IV ii V")
                .setKey("A")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern mel = new Pattern(

                "73/0.375 73/0.0625 73/0.25 64/0.0625 64/0.125 64/0.0625 64/0.0625 74/0.125 62/0.1875 57/0.1875 52/0.1875 57/0.125 62/0.0625 69/0.0625 74/0.0625 74/0.5 74/0.1875 74/0.125 71/0.0625 R/0.0625 62/0.0625 64/0.25 59/0.375 59/0.0625 71/0.0625 64/0.0625 59/0.0625 64/0.0625 71/0.0625 "

                )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);

        Pattern music = new Pattern().add(chords).add(mel);
//        player.play(music);




        try {
            File filePath = new File("Z_ankieta2/A_MAJOR_I, IV, ii, V_MAX.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
