package reli;

public class GrupaAReliAuto extends ReliAuto {

    public GrupaAReliAuto(String model, Pogon tipPogona, int godiste){
        super(model, tipPogona, godiste < 1990 ? 1990 : godiste);
    }

    @Override
    public String toString() {
        return "Grupa A: " + model + " (" + godiste + ") | " +tipPogona;
    }
}
