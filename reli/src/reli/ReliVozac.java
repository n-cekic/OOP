package reli;

public class ReliVozac {
    String ime;
    ReliAuto auto;

    public ReliVozac (String ime, ReliAuto auto){
        this.ime = ime;
        this.auto = auto;
    }

    @Override
    public String toString() {
        return ime + " - " + auto;
    }
}
