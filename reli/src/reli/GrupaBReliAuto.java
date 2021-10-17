package reli;

public class GrupaBReliAuto extends ReliAuto {
    boolean superCharger;

    public GrupaBReliAuto(String model, Pogon tipPogona, int godiste, boolean superCharger){
        super(model, tipPogona, godiste);
        this.superCharger = superCharger;
    }

    @Override
    public String toString() {
        String sc = this.superCharger ? "[S]" : "";
        return "Grupa B: " + model + " (" + godiste + ") | " + tipPogona + sc;
    }
}
