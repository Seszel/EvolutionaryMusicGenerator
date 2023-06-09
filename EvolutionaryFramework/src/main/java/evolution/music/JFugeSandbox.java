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

        Pattern chords = new ChordProgression("i iv v i")
                .setKey("C")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern mel = new Pattern(

                "60/0.125 48/0.25 60/0.125 67/0.125 72/0.0625 65/0.0625 60/0.125 51/0.0625 63/0.0625 56/0.125 56/0.25 56/0.0625 68/0.125 79/0.0625 80/0.125 68/0.0625 72/0.0625 60/0.125 62/0.125 74/0.25 74/0.125 62/0.125 62/0.0625 62/0.0625 50/0.125 55/0.0625 67/0.0625 72/0.125 79/0.25 67/0.125 63/0.125 51/0.0625 62/0.0625 55/0.125 60/0.0625 48/0.0625 "

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
