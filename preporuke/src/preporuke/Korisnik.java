package preporuke;

import java.util.Map;
import java.util.TreeMap;

public class Korisnik {
    String nadimak;
    Map<ZabavniSadrzaj, Integer> ocenjeniSadrzaj;

    public Korisnik(String nadimak) {
        this.nadimak = nadimak;
        this.ocenjeniSadrzaj = new TreeMap<>();
    }

    public Map<ZabavniSadrzaj, Integer> getOcenjeniSadrzaj() {
        return ocenjeniSadrzaj;
    }

    public String getNadimak() {
        return nadimak;
    }

    public void oceniSadrzaj(ZabavniSadrzaj zs, int ocena){
        this.ocenjeniSadrzaj.put(zs, ocena);
    }
}
