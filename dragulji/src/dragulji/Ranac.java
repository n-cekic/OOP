package dragulji;

public class Ranac {
    int maxTezina;
    int slobodnoTezina;

    public Ranac(int maxTezina) {
        this.maxTezina = maxTezina;
        this.slobodnoTezina = maxTezina;
    }

    public int getslobodnoTezina() {
        return slobodnoTezina;
    }

    boolean smestiDragulj(double tezina){
        if (tezina < this.slobodnoTezina){
            this.slobodnoTezina -= tezina;
            return true;
        }else
            return false;
    }

    @Override
    public String toString() {
        return this.slobodnoTezina + "(" + maxTezina + ")";
    }
}
