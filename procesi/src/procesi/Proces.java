package procesi;

public abstract class Proces {
    private int pId;
    private String naziv;
    private int memZauzece;

    public Proces(int pId, String naziv, int memZauzece) {
        this.pId = pId;
        this.naziv = naziv;
        this.memZauzece = memZauzece;
    }

    public int getpId() {
        return pId;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getMemZauzece() {
        return memZauzece;
    }

    @Override
    public String toString() {
        return "[" + this.pId + "] " + this.naziv + " | "
                + this.memZauzece + " MB ";
    }
}
