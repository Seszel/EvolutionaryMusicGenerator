package evolution.operator;

import evolution.music.Genome;
import evolution.util.Util;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Crossover {
    public static Pair<Genome, Genome> crossover(double crossoverProbability, List<Pair<String, Double>> crossoverType, Pair<Genome, Genome> parents) {
        Random randomObj = new Random();

        double maxDouble = crossoverType.stream()
                .mapToDouble(Pair::getValue)
                .max()
                .orElse(Double.MIN_VALUE);

        List<Pair<String, Double>> mutableList = new ArrayList<>(crossoverType);
        mutableList.sort(Comparator.comparing(Pair::getValue));
        mutableList.replaceAll(pair -> new ImmutablePair<>(pair.getKey(), pair.getValue() / maxDouble));

        double randomProbability = randomObj.nextDouble();
        String crossoverName = "";

        for (int i=0; i<mutableList.size(); i++){
            if (randomProbability < mutableList.get(i).getRight()){
                crossoverName = mutableList
                        .subList(i,mutableList.size())
                        .get(new Random().nextInt(mutableList.size()-i))
                        .getLeft();
                break;
            }
        }

        double randomCrossoverProbability = randomObj.nextDouble();
        if (randomCrossoverProbability > crossoverProbability){
            crossoverName = "NO_CROSSOVER";
        }

        switch (crossoverName) {
            case "ONE_POINT_CROSSOVER":
                return onePointCrossover(parents);
            case "TWO_POINT_CROSSOVER":
                return twoPointCrossover(parents);
            case "MUSICAL_CONTEXT":
                return musicalContextCrossover(parents);
            case "NO_CROSSOVER":
            default:
                return parents;
        }
    }

    public static Pair<Genome, Genome> onePointCrossover(Pair<Genome, Genome> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx = Util.getRandomNumber(0, parents.getLeft().getMelody().get(i).size() - 1);
            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getMelody().get(i).subList(0, idx));
            offspring1.addAll(parents.getRight().getMelody().get(i).subList(idx, parents.getLeft().getMelody().get(i).size()));

            List<Integer> offspring2 = new ArrayList<>(parents.getRight().getMelody().get(i).subList(0, idx));
            offspring2.addAll(parents.getLeft().getMelody().get(i).subList(idx, parents.getRight().getMelody().get(i).size()));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Genome genome1 = new Genome();
        Genome genome2 = new Genome();
        genome1.setMelody(offspringMelody1);
        genome2.setMelody(offspringMelody2);

        return new MutablePair<>(genome1, genome2);
    }

    public static Pair<Genome, Genome> twoPointCrossover(Pair<Genome, Genome> parents) {
        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();

        int idx1, idx2;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx1 = Util.getRandomNumber(0, parents.getLeft().getMelody().get(i).size() - 2);
            do {
                idx2 = Util.getRandomNumber(idx1 + 1, parents.getLeft().getMelody().get(i).size() - 1);
            } while (idx1 == idx2);

            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getMelody().get(i).subList(0, idx1));
            offspring1.addAll(parents.getRight().getMelody().get(i).subList(idx1, idx2));
            offspring1.addAll(parents.getLeft().getMelody().get(i).subList(idx2, parents.getLeft().getMelody().get(i).size()));

            List<Integer> offspring2 = new ArrayList<>(parents.getRight().getMelody().get(i).subList(0, idx1));
            offspring2.addAll(parents.getLeft().getMelody().get(i).subList(idx1, idx2));
            offspring2.addAll(parents.getRight().getMelody().get(i).subList(idx2, parents.getRight().getMelody().get(i).size()));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Genome genome1 = new Genome();
        Genome genome2 = new Genome();
        genome1.setMelody(offspringMelody1);
        genome2.setMelody(offspringMelody2);

        return new MutablePair<>(genome1, genome2);
    }

    public static Pair<Genome, Genome> musicalContextCrossover(Pair<Genome, Genome> parents){

        List<HashMap<Integer, Double>> scoredCuttingPoints = new ArrayList<>();

        int pLeft_pRight1, pRight_pLeft1;
        double functionValue;
        int pLeft, pRight;
        int pLeftValue = Integer.MAX_VALUE, pRightValue = Integer.MAX_VALUE;
        int pLeft1Value = Integer.MAX_VALUE, pRight1Value = Integer.MAX_VALUE;
        List<Integer> leftParentMelodyBar, rightParentMelodyBar;
        int numberOfBars = parents.getLeft().getMelody().size();
        int numberOfNotes = parents.getLeft().getMelody().get(0).size();

        for (int i=0; i<numberOfBars; i++){
            HashMap<Integer, Double> tempHashMap = new HashMap<>();

            leftParentMelodyBar = parents.getLeft().getMelody().get(i);
            rightParentMelodyBar = parents.getRight().getMelody().get(i);
            for (int j=1; j<numberOfNotes-1; j++){
                pLeft = leftParentMelodyBar.get(j);
                pRight = rightParentMelodyBar.get(j);

                if (pLeft != 0 && pLeft != -1){
                    pLeftValue = pLeft;
                } else {
                    for (int k=j-1; k>=0; k--){
                        if (leftParentMelodyBar.get(k) != 0 && leftParentMelodyBar.get(k) != -1){
                            pLeftValue = leftParentMelodyBar.get(k);
                            break;
                        }
                    }
                    for (int k=j+1; k<numberOfNotes; k++){
                        if (leftParentMelodyBar.get(k) != 0 && leftParentMelodyBar.get(k) != -1){
                            pLeft1Value = leftParentMelodyBar.get(k);
                            break;
                        }
                        if (k==numberOfNotes-1){
                            pLeft1Value = pLeftValue;
                        }
                    }

                }

                if (pRight != 0 && pRight != -1){
                    pRightValue = pRight;
                } else {
                    for (int k=j-1; k>=0; k--){
                        if (rightParentMelodyBar.get(k) != 0 && rightParentMelodyBar.get(k) != -1){
                            pRightValue = rightParentMelodyBar.get(k);
                            break;
                        }
                    }
                    for (int k=j+1; k<numberOfNotes; k++){
                        if (rightParentMelodyBar.get(k) != 0 && rightParentMelodyBar.get(k) != -1){
                            pRight1Value = rightParentMelodyBar.get(k);
                            break;
                        }
                        if (k==numberOfNotes-1){
                            pRight1Value = pRightValue;
                        }
                    }
                }

                pLeft_pRight1 = Math.abs(pLeftValue - pRight1Value);
                pRight_pLeft1 = Math.abs(pRightValue - pLeft1Value);

                if (Math.max(pLeft_pRight1, pRight_pLeft1) > 14){
                    functionValue = Double.MAX_VALUE;
                } else if (Math.min(pLeft_pRight1, pRight_pLeft1) <= 2){
                    functionValue = 0;
                } else {
                    functionValue = Math.min(pLeft_pRight1, pRight_pLeft1);
                }

                tempHashMap.put(j,functionValue);
            }
            scoredCuttingPoints.add(tempHashMap);
        }



        scoredCuttingPoints.forEach(map -> {
            List<Double> sortedValues = map.values().stream()
                    .distinct()
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());

            Map<Double, Integer> rankMap = IntStream.range(0, sortedValues.size())
                    .boxed()
                    .collect(Collectors.toMap(sortedValues::get, i -> i));

            if (sortedValues.get(0) == Double.MAX_VALUE){
                map.replaceAll((k, v) -> (double) rankMap.get(v));
            } else {
                map.replaceAll((k, v) -> (double) rankMap.get(v) + 1);
            }
        });

        scoredCuttingPoints.forEach(map -> {
            double sum = map.values().stream().mapToDouble(Double::doubleValue).sum();

            map.replaceAll((k, v) -> v / sum);
        });

        Random randomObj = new Random();
        double randomProbability = randomObj.nextDouble();
        List<Integer> idxCuttingPoints = new ArrayList<>();

        scoredCuttingPoints.forEach(map -> {
            List<Map.Entry<Integer, Double>> entries = new ArrayList<>(map.entrySet());

            for (int i = 1; i < entries.size(); i++) {
                Map.Entry<Integer, Double> currentEntry = entries.get(i);
                Map.Entry<Integer, Double> previousEntry = entries.get(i-1);
                currentEntry.setValue(currentEntry.getValue() + previousEntry.getValue());

            }

            for (int i = entries.size()-1; i >= 0; i--) {
                if (entries.get(i).getValue() < randomProbability) {
                    idxCuttingPoints.add(entries.get(i).getKey());
                    break;
                }
                if (i == 0){
                    idxCuttingPoints.add(entries.get(i).getKey());
                }
            }
            });

        List<List<Integer>> offspringMelody1 = new ArrayList<>();
        List<List<Integer>> offspringMelody2 = new ArrayList<>();
        int idx;

        for (int i = 0; i < parents.getLeft().getMelody().size(); i++) {
            idx = idxCuttingPoints.get(i);
            List<Integer> offspring1 = new ArrayList<>(parents.getLeft().getMelody().get(i).subList(0, idx));
            offspring1.addAll(parents.getRight().getMelody().get(i).subList(idx, parents.getLeft().getMelody().get(i).size()));

            List<Integer> offspring2 = new ArrayList<>(parents.getRight().getMelody().get(i).subList(0, idx));
            offspring2.addAll(parents.getLeft().getMelody().get(i).subList(idx, parents.getRight().getMelody().get(i).size()));

            offspringMelody1.add(offspring1);
            offspringMelody2.add(offspring2);

        }

        Genome genome1 = new Genome();
        Genome genome2 = new Genome();
        genome1.setMelody(offspringMelody1);
        genome2.setMelody(offspringMelody2);

        return new MutablePair<>(genome1, genome2);

    }

}
