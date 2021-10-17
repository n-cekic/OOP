package dragulji;

public abstract class ObjekatIgre {
    String naziv;
    int X;
    int Y;

    public ObjekatIgre(String naziv, int x, int y) {
        this.naziv = naziv;
        this.X = x;
        this.Y = y;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public abstract void izvrsitiAkciju();

    @Override
    public String toString() {
        return "[" + this.X + ", " + this.Y + "] " + this.naziv;
    }

}
