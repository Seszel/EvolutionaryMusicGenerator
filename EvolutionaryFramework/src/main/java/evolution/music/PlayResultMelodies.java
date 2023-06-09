package evolution.music;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;
import org.jfugue.theory.Key;
import org.jfugue.theory.Note;
import org.jfugue.theory.Scale;

import java.util.List;

public class PlayResultMelodies {
    public static void playMelodies(List<Individual> population, Pair<String, String> melodyKey, List<String> chordProgression){
        for (Individual individual : population) {
            Player player = new Player();

            System.out.println(individual.getGenome().getMelodyJFugue());
            System.out.println(individual.getGenome().getMelody());
//            System.out.println(individual.getFitnessByName("TENSION"));
//            System.out.println(individual.getFitnessByName("STABILITY"));
            System.out.println(individual.getFitnessByName("CHORD_TONE"));
//            System.out.println(individual.getFitnessByName("NON_CHORD_TONE"));
//            System.out.println(individual.getFitnessByName("STEP_MOTION"));
//            System.out.println(individual.getFitnessByName("SKIP_MOTION"));
//            System.out.println(individual.getFitnessByName("PERFECT_INTERVAL"));
//            System.out.println(individual.getFitnessByName("NON_PERFECT_INTERVAL"));
//            System.out.println(individual.getFitnessByName("DESCENDING_MELODY_LINE"));
//            System.out.println(individual.getFitnessByName("ASCENDING_MELODY_LINE"));
            System.out.println(individual.getFitnessByName("SIMPLE_RHYTHM"));
//            System.out.println(individual.getFitnessByName("COMPLICATED_RHYTHM"));
            System.out.println(individual.getFitnessByName("UNDESIRABLE_PROPERTIES_MELODY"));
            System.out.println(individual.getFitnessByName("SIMPLE_AND_OBVIOUS"));
//            System.out.println(individual.getFitnessByName("COMPLICATED_AND_ENIGMATIC"));

            StringBuilder durationOfChords = new StringBuilder();
            for (int i=0; i<chordProgression.size(); i++){
                durationOfChords.append("$").append(i).append("w ");
            }


            StringBuilder ChrPrg = new StringBuilder();
            for (String elem : chordProgression){
                ChrPrg.append(elem).append(" ");
            }

            Pattern chords = new ChordProgression(ChrPrg.toString())
                    .setKey(melodyKey.getKey())
                    .allChordsAs(durationOfChords.toString())
                    .getPattern()
                    .setVoice(0)
                    .setInstrument("Piano")
                    .setTempo(90);

            Pattern melody = new Pattern(individual.getGenome().getMelodyJFugue())
                    .setTempo(90)
                    .setInstrument("Piano")
                    .setVoice(1);
            Pattern music = new Pattern().add(chords).add(melody);

//            player.play(chords);
//            player.play(pattern);
            player.play(music);
        }
    }
}
