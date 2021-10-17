package procesi;

public class PozadinskiProces extends Proces {
    private boolean sistemski;

    public PozadinskiProces(int pId, String naziv, int memZauzece, boolean sistemski) {
        super(pId, naziv, memZauzece);
        this.sistemski = sistemski;
    }

    public boolean isSistemski() {
        return this.sistemski;
    }

    @Override
    public String toString() {
        return super.toString() + (isSistemski() ? "(Sistem)\n" : "\n");
    }
}
