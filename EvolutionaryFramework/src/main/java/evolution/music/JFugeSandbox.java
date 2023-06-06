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
//        player.play("67/0.875 67/0.0625 67/0.0625 67/0.75 71/0.0625 59/0.0625 62/0.0625 55/0.0625 64/0.875 72/0.0625 60/0.0625 69/0.8125 60/0.0625 36/0.0625 65/0.0625 ");

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

                "50/0.125 60/0.75 62/0.0625 72/0.0625 62/0.5 52/0.125 50/0.1875 60/0.0625 67/0.0625 69/0.0625 74/0.3125 72/0.25 65/0.25 60/0.0625 50/0.0625 52/0.0625 59/0.125 64/0.0625 69/0.6875 74/0.0625 53/0.0625 "

        )

                .setTempo(90)
                .setInstrument("Piano")
                .setVoice(1);

        Pattern music = new Pattern().add(chords).add(mel);
        player.play(music);




        try {
            File filePath = new File("random.mid");
            MidiFileManager.savePatternToMidi(music, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}
