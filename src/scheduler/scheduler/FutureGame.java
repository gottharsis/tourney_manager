package scheduler;

import java.util.List;

public class FutureGame extends Game implements FutureDependency {
    private boolean ready;
    private List<FutureDependency> futureDependencyList;


    public FutureGame(Game g1, Game g2, boolean winner1, boolean winner2) {
        FutureCompetitor c1 = new FutureCompetitor(g1, winner1);
        FutureCompetitor c2 = new FutureCompetitor(g2, winner2);

        this.c1 = c1;
        this.c2 = c2;
        this.ready = false;

        this.futureDependencyList = List.of(c1, c2);

    }

    public FutureGame(Game g1, Game g2) {
        this(g1, g2, true, true);
    }

    @Override
    public boolean isReady() {
        if (this.ready) {
            return true;
        }
        refresh();
        return this.ready;
    }

    @Override
    public void refresh() {
        if (this.futureDependencyList.stream().allMatch(FutureDependency::isReady)) {
            this.ready = true;
        }
    }
}
