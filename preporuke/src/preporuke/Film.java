package preporuke;

import java.util.Set;

public class Film extends ZabavniSadrzaj {
    String glumac;
    int godina;

    public Film(String naziv, int trajanje, String glumac, int godina) {
        super(naziv, trajanje);
        this.glumac = glumac;
        this.godina = godina;
    }

    public boolean zaPreporuku(String uslov, int opcija, Set<ZabavniSadrzaj> ocenjenSadrzaj){
        if(ocenjenSadrzaj.contains(this))
            return false;

        if(opcija == 1){
            if(uslov.trim().equals(this.glumac.trim()))
                return true;
            else
                return false;
        }else if(opcija == 2){
            try {
                if(Integer.parseInt(uslov) == this.godina)
                    return true;
                else
                    return false;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        else
            return false;
    }

    @Override
    public String toString() {
        return String.format("[film] %s %d:%d", this.naziv, this.trajanje / 60, this.trajanje % 60);
    }

}
