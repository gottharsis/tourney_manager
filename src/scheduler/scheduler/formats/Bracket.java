package scheduler.formats;

import scheduler.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Bracket extends Schedule {
    public static final String FORMAT = "Single-Elimination Bracket";

    public Bracket(List<Competitor> competitors) {
        super(competitors);
        this.rounds = createRounds();
    }

    @Override
    public void update(List<GameResult> results) {
        super.update(results);
        if (getCurrentRound().stream().allMatch(Game::isFinished)) {
            if (this.currentRoundIndex + 1 == this.rounds.size()) {
                this.setFinished(true);
            } else {
                this.currentRoundIndex ++;
                for (Game g: getCurrentRound()) {
                    if (g instanceof FutureDependency) {
                        ((FutureDependency) g).refresh();
                    }
                }
            }
        }
    }

    private static int nextPowerOfTwo(int n) {
        int highestBit = Integer.highestOneBit(n);
        if (n == highestBit) {
            return n;
        }
        return highestBit << 1;
    }

    /**
     * Add byes until the length of the competitors is a power of two.
     * modifies in place
     * @param competitors the list of teams to pad
     */
    private static void pad(List<Competitor> competitors) {
        int size = competitors.size();
        int numByes = nextPowerOfTwo(size) - size;
        for (int i = 0; i < numByes; i++) {
            competitors.add(Competitor.BYE);
        }
    }

    private List<List<Game>> createRounds() {
        // create byes if necessary
        var teams = List.copyOf(this.getCompetitors());
        pad(teams);
        List<List<Game>> rounds = new ArrayList<>();

        // create the first round
        List<Game> prevRound = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            Competitor c1 = teams.get(i);
            Competitor c2 = teams.get(teams.size() - i - 1);
            Game g = new Game(c1, c2);
            prevRound.add(g);
        }

        rounds.add(prevRound);
        // create subsequent rounds
        while (prevRound.size() > 1) {
            List<Game> rd = new ArrayList<>();
            for (int i = 0; i < prevRound.size(); i++) {
                Game c1 = prevRound.get(i);
                Game c2 = prevRound.get(prevRound.size() - 1 - i);
                Game g= new FutureGame(c1, c2);
                rd.add(g);
            }
            rounds.add(rd);
            prevRound = rd;
        }
        return rounds;
    }

    @Override
    public String getFormat() {
        return FORMAT;
    }


    @Override
    public List<List<Game>> getRounds() {
        return this.rounds;
    }

    @Override
    public List<Game> getCurrentRound() {
        return null;
    }
}
