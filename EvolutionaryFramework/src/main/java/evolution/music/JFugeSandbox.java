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

        Pattern mel = new Pattern(

                "61/0.125 62/0.0625 60/0.0625 62/0.0625 60/0.0625 60/0.0625 70/0.0625 68/0.0625 66/0.0625 65/0.0625 66/0.0625 64/0.0625 62/0.0625 64/0.0625 66/0.0625 67/0.125 64/0.0625 65/0.0625 65/0.0625 64/0.0625 66/0.0625 65/0.0625 61/0.0625 69/0.0625 66/0.0625 65/0.0625 66/0.0625 67/0.0625 67/0.0625 69/0.0625 67/0.125 66/0.0625 65/0.0625 65/0.0625 67/0.0625 66/0.0625 65/0.0625 65/0.0625 63/0.0625 63/0.0625 65/0.0625 65/0.0625 67/0.0625 67/0.0625 67/0.0625 66/0.0625 66/0.125 65/0.0625 63/0.0625 66/0.0625 68/0.0625 69/0.0625 68/0.0625 67/0.0625 69/0.0625 69/0.0625 69/0.0625 62/0.0625 63/0.0625 62/0.0625 "

                )
                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);


//        player.play(chords);
        Pattern music = new Pattern().add(chords).add(mel);
//        player.play(music);


//                String folderName = "";
//        String fileName = "";
//        String folderName = "descendingAscending/";
        String folderName = "stepSkip/";
        String fileName = "A_MAJOR_I, IV, ii, V_max";

        try {
            File filePath = new File("../Visualization/midi/" + folderName + fileName +".mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
