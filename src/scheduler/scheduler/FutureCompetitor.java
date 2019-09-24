package scheduler;

/**
 * Represents the winner of a game
 */
public class FutureCompetitor extends Competitor implements FutureDependency {
    private Game game;
    private boolean ready;
    private boolean winner;

    public FutureCompetitor(Game game, boolean winner) {
        this.name = String.format("%s of game %d",
                (winner) ? "Winner" : "loser", game.getId());
        this.game = game;
        this.winner = winner;
    }

    public FutureCompetitor(Game game) {
        this(game, true);
    }

    public boolean isReady() {
        refresh();
        return this.ready;
    }

    public void refresh() {
        if (this.ready) {
            return;
        }
        if (this.game.isFinished()) {
            this.ready = true;
            if (this.winner) {
                this.name = this.game.getWinner().name;
                this.id = this.game.getWinner().id;
            } else {
                this.name = this.game.getLoser().name;
                this.id = this.game.getLoser().id;
            }
        }
    }

}
