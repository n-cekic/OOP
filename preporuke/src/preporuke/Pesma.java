package preporuke;

import java.util.Set;

public class Pesma extends ZabavniSadrzaj {
    String izvodjac;
    String zanr;

    public Pesma(String naziv, int trajanje, String izvodjac, String zanr) {
        super(naziv, trajanje);
        this.izvodjac = izvodjac;
        this.zanr = zanr;
    }

    public boolean zaPreporuku(String uslov, int opcija, Set<ZabavniSadrzaj> ocenjenSadrzaj) {
        if (ocenjenSadrzaj.contains(this))
            return false;

        if (opcija == 1) {
            if (this.izvodjac.trim().equals(uslov.trim()))
                return true;
            else
                return false;
        }
        if (opcija == 2) {
            String[] zanrovi = this.zanr.split(";");
            for (String z : zanrovi) {
                if (z.trim().equals(uslov.trim()))
                    return true;
            }
            return false;
        } else
            return false;
    }

    @Override
    public String toString() {
        return String.format("[pesma] %s %d:%d", naziv, this.trajanje / 60, this.trajanje % 60);
    }
}
