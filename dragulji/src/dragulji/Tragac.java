package dragulji;

import java.util.Random;

public class Tragac extends ObjekatIgre {
    public Ranac ranac;
    private static Random random = new Random();
    private static String smerKretanja;

    public Tragac(String naziv, int x, int y, Ranac ranac) {
        super(naziv, x, y);
        this.ranac = ranac;
    }

    public Ranac getRanac() {
        return ranac;
    }

    public static void setSmerKretanja(String smerKretanja) {
        Tragac.smerKretanja = smerKretanja;
    }

    public boolean pokupiDragulj(Rudnik rudnik){
        return ranac.smestiDragulj(rudnik.tezinaDragulja);
    }

    @Override
    public void izvrsitiAkciju() {
        if (smerKretanja.equals("dd")){
            if(random.nextInt() % 2 == 0)
                this.Y += 1;
            else
                this.X += 1;
        }else if (smerKretanja.equals("gl")){
            if (random.nextInt() % 2 == 0)
                this.Y -= 1;
            else
                this.X -= 1;
        }
    }

    @Override
    public String toString() {
        return String.format("Tragac: [%d, %d] (%s) ima ranac: %s",this.X, this.Y, naziv, ranac.toString());
    }
}
