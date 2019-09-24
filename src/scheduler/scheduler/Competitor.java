package scheduler;

public class Competitor {

    String name;
    int id;
    private static int nextId = 1;

    public Competitor(String name) {
        this.name = name;
        this.id = Competitor.nextId++;
    }

    Competitor() {
        this.name = "Not set";
        this.id = Competitor.nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Competitor)) return false;
        if (!super.equals(object)) return false;

        Competitor that = (Competitor) object;

        if (getId() != that.getId()) return false;
        if (!getName().equals(that.getName())) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getId();
        return result;
    }

    public static final Competitor BYE = new Competitor("BYE");
}
