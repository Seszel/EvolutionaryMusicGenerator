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
                .setKey("D#")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern mel = new Pattern(

                "65/0.625 58/0.125 R/0.0625 55/0.0625 67/0.125 58/0.125 68/0.25 62/0.125 58/0.0625 65/0.0625 75/0.125 70/0.0625 63/0.0625 53/0.0625 58/0.0625 63/0.125 56/0.25 67/0.125 R/0.0625 67/0.0625 60/0.125 67/0.0625 60/0.0625 R/0.0625 63/0.0625 74/0.125 68/0.25 67/0.125 56/0.0625 68/0.0625 63/0.125 68/0.0625 74/0.0625 68/0.0625 56/0.0625 "

                )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);


        player.play(chords);
        Pattern music = new Pattern().add(chords).add(mel);
//        player.play(music);




        try {
            File filePath = new File("A_MAJOR_I, IV, ii, V_MAX.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
