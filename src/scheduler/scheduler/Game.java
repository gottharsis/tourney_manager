package scheduler;

/**
 * @author Ayush Nene
 * @version 1
 * Represents a game played between 2 players
 */
public class Game {
    private int id;
    Competitor c1;
    Competitor c2;
    private Competitor winner;
    private Competitor loser;
    private boolean finished;

    private static int nextId = 1;

    Game() {
        this(null, null);
    }
    public Game(Competitor c1, Competitor c2) {
        this.c1 = c1;
        this.c2 = c2;
        this.winner = null;
        this.loser = null;

        this.id = Game.nextId++;
        this.finished = false;
    }

    /**
     * @param winner The winner of the scheduler.Game
     */
    public void setWinner(Competitor winner) throws IllegalArgumentException {
        setWinner(winner.getId());
    }

    public void setWinner(int winnerId) throws IllegalArgumentException {
        if (this.finished) {
            throw new IllegalArgumentException(String.format("scheduler.Game %d is already finished", this.id));
        }
        if (winnerId == this.c1.getId()) {
            this.winner = this.c1;
            this.loser = this.c2;
        } else if (winnerId == this.c2.getId()) {
            this.winner = this.c2;
            this.loser = this.c1;
        } else {
            throw new IllegalArgumentException(String.format("Competitor %d is not part of game %d", winnerId, this.id));
        }
        this.finished = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        return getId() == game.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    public int getId() {
        return id;
    }

    public Competitor getWinner() {
        return winner;
    }

    public Competitor getLoser() {
        return loser;
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public String toString(){
        return String.format("Game %d between %s and %s", this.id, this.c1.getName(), this.c2.getName());
    }
}