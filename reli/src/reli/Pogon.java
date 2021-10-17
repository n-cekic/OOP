package reli;

public enum Pogon {
    Prednji,
    Zadnji,
    SvaCetiri;

    public static Pogon izNiske(String pogon){
        switch (pogon){
            case "RWD" : return Zadnji;
            case "FWD" : return Prednji;
            case "4WD" : return SvaCetiri;
            default: return Zadnji;
        }
    }

    @Override
    public String toString() {
        switch (this){
            case Zadnji: return "RWD";
            case Prednji: return "FWD";
            case SvaCetiri: return "4WD";
            default: return "ASDASD";
        }
    }
}
