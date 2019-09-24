package scheduler;

public interface FutureDependency {
    boolean isReady();
    void refresh();
}
