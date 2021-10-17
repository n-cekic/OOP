package busPlus;

public class Personalizovana extends BusPlus {
    public Kategorija kategorija;
    public boolean imaDopunu;

    Personalizovana(Kategorija kategorija, boolean imaDopunu, int id, int zona) {
        super(id, zona);
        this.imaDopunu = imaDopunu;
        this.kategorija = kategorija;
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public int getZona() {
        return super.getZona();
    }

    @Override
    public String toString() {
        return "[" + kategorija + "] " + "zona: " + this.zona + " | " + (this.imaDopunu ? "dopunjena\n" : "bez dopune\n");
    }


}
