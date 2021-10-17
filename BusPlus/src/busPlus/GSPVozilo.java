package busPlus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class GSPVozilo {
    public Map<Integer, BusPlus> kartice;
    public List<Integer> nevalidne;

    private static Random random;
    private static int noviId = 10000;

    public GSPVozilo() {
        this.kartice = new TreeMap<>();
        this.nevalidne = new ArrayList<>();
    }

    public List<BusPlus> getKartice() {
        return Arrays.asList((BusPlus) this.kartice.values());
    }

    boolean putniciUVozilu(String putanja) {
        this.kartice = new TreeMap<>();
        try {
            List<String> linije = Files.readAllLines(Paths.get(putanja));
            for (String linija : linije) {
                BusPlus kartica;
                String[] delovi = linija.split(",");

                String pers = delovi[0].trim();
                int id = Integer.parseInt(delovi[1].trim());
                int zona = Integer.parseInt(delovi[2].trim());
                if (pers.equals("P")) {
                    Kategorija kategorija = Kategorija.valueOf(delovi[3].trim());
                    boolean dopuna = delovi[4].trim().equals("da");
                    kartica = new Personalizovana(kategorija, dopuna, id, zona);
                } else if (pers.equals("N")) {
                    int kredit = Integer.parseInt(delovi[3].trim());
                    boolean ocitana = delovi[4].trim().equals("+");
                    kartica = new Nepersonalizovana(kredit, ocitana, id, zona);
                } else {
                    System.err.println("nepoznat tip kartice");
                    //kartica = new Personalizovana(Kategorija.A1, false, -1, 0);
                    return false;
                    //  MOZDA NIJE INICIJALIZOVANA, VIDI KAKO SU ONI RADILI!!!
                }

                this.kartice.put(id, kartica);
            }

        } catch (IOException e) {
            System.err.println("Neuspelo ucitavanje fajla");
            return false;
        }
        return true;
    }

    public BusPlus noviPutnik(int vrstaKartice) {
        random = new Random();
        if(vrstaKartice == 1){
            Personalizovana putnik = new Personalizovana(Kategorija.izBroja((random.nextInt() % 4))
                                                         ,random.nextBoolean(),noviId++,vrstaKartice);
            this.kartice.put(noviId, putnik);
            return putnik;
        }
        Nepersonalizovana putnik = new Nepersonalizovana(random.nextInt(90)*2,
                                                             random.nextBoolean(), noviId++, vrstaKartice);
        this.kartice.put(noviId, putnik);
        return putnik;
    }

    public String kontrola(){
        StringJoiner sj = new StringJoiner("\n");
        sj.add("Kontrola!!!");
        List<BusPlus> kartice = new ArrayList<>(this.kartice.values());
        for(BusPlus karta : kartice){

            if(karta instanceof Personalizovana){
                if(((Personalizovana) karta).imaDopunu) {
                    sj.add("+" + karta.toString());
                }
                else {
                    sj.add("-" + karta.toString());
                }
            }
            else if (karta instanceof Nepersonalizovana) {
                if(((Nepersonalizovana) karta).ocitana) {
                    sj.add("+" + karta.toString());
                }
                else {
                    sj.add("-" + karta.toString());
                }
            }
           /* if(karta.toString().contains("bez dopune") || karta.toString().contains("nedovoljno") || karta.toString().contains("nije")) {
                sj.add("-");
                this.nevalidne.add(karta.id);
            }
            else
                sj.add("+");
            sj.add(karta.toString());*/
        }
        return sj.toString();
    }

    public boolean izbaciPutnike(){
        for (int key : this.nevalidne){
            this.kartice.remove(key);
        }
        return this.nevalidne.isEmpty() ? false : true;
    }

}
