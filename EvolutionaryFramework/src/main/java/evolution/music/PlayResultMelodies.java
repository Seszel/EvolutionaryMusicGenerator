package evolution.music;

import evolution.solution.Individual;
import org.apache.commons.lang3.tuple.Pair;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;
import org.jfugue.theory.ChordProgression;

import java.util.List;

public class PlayResultMelodies {
    public static void playMelodies(List<Individual> population, Pair<String, String> melodyKey, List<String> chordProgression){
        for (Individual individual : population) {
            Player player = new Player();

            System.out.println(individual.getGenome().getMelodyJFugue());
            System.out.println(individual.getFitnessByName("TENSION"));
//            System.out.println(individual.getFitnessByName("STABILITY"));

            StringBuilder durationOfChords = new StringBuilder();
            for (int i=0; i<chordProgression.size(); i++){
                durationOfChords.append("$").append(i).append("w ");
            }

            Pattern chords = new ChordProgression(chordProgression.toString())
                    .setKey(melodyKey.getKey())
                    .allChordsAs(durationOfChords.toString())
                    .getPattern()
                    .setVoice(0)
                    .setInstrument("Piano")
                    .setTempo(90);

            Pattern pattern = new Pattern("X[Volume]=13000" + individual.getGenome().getMelodyJFugue())
                    .setTempo(90)
                    .setInstrument("Piano")
                    .setVoice(1);
            player.play(chords, pattern);
        }
    }
}
