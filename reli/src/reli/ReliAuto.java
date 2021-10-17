package reli;

public abstract class ReliAuto {
    public String model;
    public Pogon tipPogona;
    public int godiste;

    protected ReliAuto(String model, Pogon tipPogona, int godiste){
        this.godiste = godiste;
        this.model = model;
        this.tipPogona = tipPogona;
    }

    @Override
    public abstract String toString();
}
