package dragulji;

public class Rudnik extends ObjekatIgre {
    int otvorenJos;
    double tezinaDragulja;

    public Rudnik(String naziv, int x, int y, int otvorenJos, double tezinaDragulja) {
        super(naziv, x, y);
        this.otvorenJos = otvorenJos;
        this.tezinaDragulja = tezinaDragulja;
    }

    public int getOtvorenJos() {
        return otvorenJos;
    }

    public double getTezinaDragulja() {
        return tezinaDragulja;
    }

    public boolean isOtvoren(){
        if(otvorenJos == 0)
            return false;
        return true;
    }

    @Override
    public void izvrsitiAkciju() {
        if(isOtvoren()){
            this.otvorenJos -= 1;
        }
    }

    @Override
    public String toString() {
        //return "Rudnik" + "\\textit{" + naziv + "} " + "je otvoren jos ";
        return String.format("Rudnik: [%d, %d] %s je otvoren jos %d poteza",this.X, this.Y, naziv, otvorenJos);
    }
}
