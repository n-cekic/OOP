package procesi;

public class AktivniProces extends Proces {
    private int iskoriscenostCPU;

    public AktivniProces(int pId, String naziv, int memZauzece, int iskoriscenostCPU) {
        super(pId, naziv, memZauzece);
        this.iskoriscenostCPU = iskoriscenostCPU;
    }

    public int getIskoriscenostCPU() {
        return iskoriscenostCPU;
    }

    @Override
    public String toString() {
        return super.toString() + "/ " + this.iskoriscenostCPU + "% CPU\n";
    }
}
