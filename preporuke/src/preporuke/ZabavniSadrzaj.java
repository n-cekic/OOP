package preporuke;

import java.util.Set;

public abstract class ZabavniSadrzaj implements Comparable {
    String naziv;
    int trajanje;

    public ZabavniSadrzaj(String naziv, int trajanje) {
        this.naziv = naziv;
        this.trajanje = trajanje;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getTrajanje() {
        return trajanje;
    }

    public abstract boolean zaPreporuku(String uslov, int opcija, Set<ZabavniSadrzaj> ocenjenSadrzaj);


    @Override
    public int compareTo(Object o) {
        if (this instanceof Pesma && o instanceof Film)
            return 1;

        else if(this instanceof Film && o instanceof Pesma)
            return -1;

        else if (this instanceof Film && o instanceof Film)
            return this.naziv.compareTo(((Film) o).naziv);

        else if (this instanceof Pesma && o instanceof Pesma)
            return this.naziv.compareTo(((Pesma) o).naziv);

        else
            return 0;
    }
}
