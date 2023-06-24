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
        for (int i=0; i<8; i++){
            durationOfChords.append("$").append(i).append("w ");
        }

        Pattern chords = new ChordProgression("i iv v i i iv v i")
                .setKey("C")
                .allChordsAs(durationOfChords.toString())
                .getPattern()
                .setVoice(0)
                .setInstrument("Piano")
                .setTempo(90);

        Pattern mel = new Pattern(

//"57/0.0625 62/0.75 63/0.0625 63/0.0625 63/0.0625 60/0.0625 58/0.75 53/0.125 60/0.0625 50/0.0625 60/0.3125 74/0.125 67/0.125 69/0.25 70/0.0625 60/0.0625 55/0.1875 57/0.75 55/0.0625 62/0.6875 72/0.0625 74/0.125 63/0.0625 60/0.0625 75/0.6875 65/0.125 60/0.0625 49/0.0625 65/0.0625 55/0.375 57/0.4375 67/0.0625 62/0.0625 65/0.0625 55/0.1875 56/0.625 63/0.0625 69/0.0625 63/0.0625"
//"57/0.0625 62/0.75 63/0.0625 63/0.0625 63/0.0625 65/0.375 72/0.3125 77/0.125 65/0.125 60/0.0625 55/0.0625 55/0.4375 55/0.125 62/0.25 62/0.0625 60/0.0625 63/0.1875 63/0.75 63/0.0625 63/0.6875 63/0.125 63/0.0625 63/0.0625 63/0.0625 60/0.6875 60/0.125 60/0.0625 60/0.0625 60/0.0625 55/0.8125 67/0.0625 62/0.0625 60/0.0625 60/0.1875 63/0.625 63/0.0625 63/0.0625 63/0.0625"
//"81/0.0625 72/0.8125 67/0.0625 67/0.0625 68/0.5 68/0.1875 68/0.0625 68/0.125 68/0.0625 68/0.0625 70/0.6875 70/0.1875 70/0.0625 70/0.0625 72/0.625 67/0.0625 67/0.125 67/0.0625 67/0.0625 67/0.0625 67/0.0625 67/0.4375 67/0.125 67/0.1875 67/0.125 67/0.0625 68/0.0625 68/0.1875 68/0.4375 68/0.0625 68/0.0625 68/0.0625 68/0.0625 68/0.0625 70/0.75 70/0.0625 70/0.125 70/0.0625 75/0.0625 68/0.125 67/0.3125 67/0.1875 67/0.0625 67/0.0625 67/0.0625 79/0.0625 67/0.0625"
//"69/0.75 67/0.1875 55/0.0625 56/0.6875 60/0.1875 65/0.0625 77/0.0625 82/0.9375 70/0.0625 60/0.9375 67/0.0625 55/0.9375 67/0.0625 56/0.9375 68/0.0625 79/0.0625 82/0.875 70/0.0625 60/0.0625 55/0.875 67/0.0625 "
"79/0.75 67/0.1875 55/0.0625 48/0.6875 60/0.1875 65/0.0625 77/0.0625 82/0.875 82/0.0625 70/0.0625 63/0.875 67/0.0625 67/0.0625 67/0.8125 67/0.125 67/0.0625 68/0.875 56/0.0625 68/0.0625 70/0.0625 82/0.875 70/0.0625 60/0.0625 55/0.875 67/0.0625"
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
