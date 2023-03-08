package evolution.music;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.rhythm.Rhythm;
import org.jfugue.theory.ChordProgression;

import java.io.File;
import java.io.IOException;

public class JFugeSandbox {

    public static void main(String[] args) {
        Player player = new Player();
        Pattern mainChords = new Pattern("T180 V0 D4Min9hqit Ri G3Majhqi Ri C4Maj9wh Rht ");
        mainChords.add("  D4Minhqit  Ri G4Majhqi   Ri C4Majwh Rht ");
        Pattern pianoTouch = new Pattern("T180 V1 Rw | Rw | Rhi | G4qi G3s A3is CMajis ri");
        pianoTouch.add(" Rw | Rw | Rhi | G4s C5wa100d0 Rw ");

        Pattern introOnce = new Pattern(mainChords, pianoTouch);

        Pattern introRhythm = new Pattern(
                "T180 V9 [CLOSED_HI_HAT]x Rx [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt ");

        Pattern introFirstPart = new Pattern(introOnce, introRhythm.repeat(8));

        Pattern mainRhythm = new Pattern(
                "T180 V9 [BASS_DRUM]x [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt  [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rtt [BASS_DRUM]x [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt [BASS_DRUM]x [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rtt [BASS_DRUM]x [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [ELECTRIC_SNARE]x [CLOSED_HI_HAT]x [MARACAS]x Rss [BASS_DRUM]x [MARACAS]x Rtt [CLOSED_HI_HAT]x [MARACAS]x Rss [CLOSED_HI_HAT]x [MARACAS]x Rtt ");

        Pattern vocalsSilence = new Pattern("T180 V4 Rw Rw Rw Rw | Rw Rw Rw Rw | Rq ");

        Pattern vocals = new Pattern("T180 V04 ");
        vocals.add("I[TROMBONE]  Rh G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs D5q rs C5h Rs");
        vocals.add("I[ALTO_SAX] C4i A5q G5isa50d0 Rs A5s E5i D5is Rs C5qis");
        vocals.add("I[TROMBONE] Rqi A4s G5i E5i Rs | G5is Rs E5q | D5is C5i Rs C5q G4q Ri");
        vocals.add("I[TRUMPET] G3is A3s C4is D4s C4is D4s G4is A4s G4is A4s | E4q rs F4h");
        vocals.add("I[TROMBONE] G5is E5i Ri | G5s Ris E5q Rs | G5q E5i Rs A5is rs G5q A5s E5i D5i ri C5h Rit");
        vocals.add("I[TROMBONE] C5s A3q C5i Rs | D5i Rs Eb5qs Rs | D5q Eb5i Rs D5is Eb5s D4q Rs | C5i A4q C5h Rw Rhi");

        Pattern introSecondPart = new Pattern(mainChords, mainRhythm.repeat(2));

        Pattern bassGuitarSilence = new Pattern("T180 V3 Rw Rw Rw Rw | Rw Rw Rw Rw | Rq ");
        Pattern bassGuitar = new Pattern(
                "T180 V3  I[SLAP_BASS_1] D3is D3s Rhq G3is G3s Rqis B2qi | C3is C3s Rhq D3is D3s Rq E3is E3s Rq | D3is D3s Rhq G2is G2s Rqis B2qi | C3is C3s Rhq G3is G3s Rq A3s Ri G3s E3q ");
        bassGuitar.add(
                "D3is D3s Rhq G2is G2s Rqis B2qi | C3is C3s Rhq D3is D3s Rq E3is E3s Rq D3is D3s Rhq G2is G2s Rqis B2qi C3is C3s Rhq G3i Ri A3q G3is F3s E3q ");

        Pattern introThirdPart = new Pattern(introFirstPart, bassGuitarSilence, bassGuitar.repeat(2), vocalsSilence,
                vocals.repeat(2), introSecondPart.repeat(4));

        try {
            File filePath = new File("sandboxMidi.mid");
            MidiFileManager.savePatternToMidi(introThirdPart, filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

}
