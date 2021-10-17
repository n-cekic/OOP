package busPlus;

public class Nepersonalizovana extends BusPlus {
    public int kredit;
    public boolean ocitana;
    private static int cenaVoznje = 90;

    public Nepersonalizovana(int kredit, boolean ocitana, int id, int zona){
        super(id,zona);
        this.kredit = kredit;
        this.ocitana = ocitana;
    }

    @Override
    public int getZona() {
        return super.getZona();
    }

    public int getKredit() {
        return kredit;
    }

    public boolean dovoljnoKredita(){
        return this.kredit > cenaVoznje ? true : false;
    }

    @Override
    public String toString() {
        String razlog;
        if(ocitana) {
            if (dovoljnoKredita())
                razlog = "ocitana\n";
            else
                razlog = "nedovoljno kredita\n";
        }else
            razlog = "nije ocitana\n";

        return "[NP] zona: " + zona + " kredit: " + kredit + " | " + razlog;
    }

    public boolean ocitajKartu(){
        if(this.ocitana || kredit < cenaVoznje)
            return false;
        this.kredit -= cenaVoznje;
        this.ocitana = true;
        return true;
    }
}
