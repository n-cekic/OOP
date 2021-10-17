package reli;

public class StavkaRasporeda {
    public ReliVozac vozac;
    public int startH;
    public int startM;

    public StavkaRasporeda (ReliVozac vozac, int startH, int startM){
        this.vozac = vozac;
        this.startM = startM;
        this.startH = startH;
        if(startH > 23)
            this.startH = 0;
        if(startM > 59)
            this.startM = 0;

    }

    @Override
    public String toString() {
        return "[" + startH + ":" + startM + "] " + vozac;
    }
}
