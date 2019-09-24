package scheduler.formats;

import scheduler.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RoundRobin extends Schedule{
    public static final String FORMAT = "Round Robin";

    public RoundRobin(List<Competitor> competitors) {
        super(competitors);
        this.rounds = Collections.unmodifiableList(this.createGameOrder());
    }

    @Override
    public String getFormat() {
        return FORMAT;
    }


    @Override
    public List<List<Game>> getRounds() {
        return this.rounds;
    }

    private List<List<Game>> createGameOrder() {
        List<List<Game>> rounds = new ArrayList<>();
        LinkedList<Competitor> firstHalf, secondHalf;
        int halfIndex = (getCompetitors().size() + 1)/2;
        firstHalf = new LinkedList<>(getCompetitors().subList(0, halfIndex));
        secondHalf = new LinkedList<>(getCompetitors().subList(halfIndex, getCompetitors().size()));
        Collections.reverse(secondHalf);
        if (secondHalf.size() < firstHalf.size()) {
            secondHalf.add(Competitor.BYE);
        }

        int numRounds = getCompetitors().size() - 1;
        for (int i = 0; i < numRounds; i++) {
            var currentRound = IntStream.range(0, halfIndex)
                    .mapToObj(j -> new Game(firstHalf.get(j), secondHalf.get(j)))
                    .collect(Collectors.toList());
            rounds.add(currentRound);
            // rotate
            var first = firstHalf.poll();
            firstHalf.addFirst(secondHalf.poll());
            secondHalf.add(firstHalf.pollLast());
            firstHalf.addFirst(first);
        }

        return rounds;
    }
}
