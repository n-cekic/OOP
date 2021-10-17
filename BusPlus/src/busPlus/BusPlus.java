package busPlus;

public abstract class BusPlus {
    public int id;
    public int zona;

    public BusPlus(int id, int zona){
        this.id = id;
        this.zona = zona;
    }

    public int getId() {
        return id;
    }

    public int getZona() {
        return zona;
    }

    @Override
    public String toString() {
        return "zona: " + zona;
    }
}
