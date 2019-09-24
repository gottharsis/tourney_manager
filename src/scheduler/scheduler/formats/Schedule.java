package scheduler.formats;

import scheduler.*;

import java.util.List;
import java.util.Optional;

public abstract class Schedule {
    private List<Competitor> competitors;
    protected List<Game> gameOrder = null;
    protected List<List<Game>> rounds = null;
    protected int currentRoundIndex = 0;
    private static int nextId = 1;


    private boolean finished = false;
    public boolean isFinished() {
        return finished;
    }

    protected void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    private int id;

    public abstract String getFormat();
    public List<Competitor> getCompetitors() {
        return this.competitors;
    }
    public abstract List<List<Game>> getRounds();
    public List<Game> getCurrentRound() {
        return this.rounds.get(this.currentRoundIndex);
    }

    public Schedule(List<Competitor> competitors) {
        this.competitors = competitors;
        this.id = Schedule.nextId++;
    }

    /**
     * Updates the current round with the results provided
     * will ignore invalid results and increment the current round if
     * all the games are finished a
     * @param results the results with which to update
     */
    public void update(List<GameResult> results){
        if (this.isFinished()) return;
        for (var result: results) {
            Optional<Game> og = this.getCurrentRound().stream()
                    .filter(i -> i.getId() == result.getGameId())
                    .findAny();
            if (og.isEmpty()) continue;
            Game g= og.get();
            g.setWinner(result.getWinnerId());
        }
    }
}
