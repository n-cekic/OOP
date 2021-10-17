package reli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class ReliRaspored {
    List<StavkaRasporeda> redVoznje;
    int maxVremeVoznje; //min

    public ReliRaspored(int maxVremeVoznje) {
        this.maxVremeVoznje = maxVremeVoznje;
        this.redVoznje = new ArrayList<>();
    }

    public ReliRaspored() {
        this(30);
    }

    public boolean ucitaj(String putanja) {
        boolean uspesnost = true;
        this.redVoznje = new ArrayList<>();

        try {
            List<String> linije = Files.readAllLines(Paths.get(putanja));
            for (String linija : linije) {
                String[] linijaParsirano = linija.split(",");

                int vremeH = Integer.parseInt(linijaParsirano[0].trim());
                int vremeM = Integer.parseInt(linijaParsirano[1].trim());
                String ime = linijaParsirano[2].trim();
                String grupa = linijaParsirano[3].trim();
                String model = linijaParsirano[4].trim();
                int godiste = Integer.parseInt(linijaParsirano[5].trim());
                Pogon pogon = Pogon.izNiske(linijaParsirano[6].trim());

                if (linijaParsirano.length > 7) {
                    boolean superCharger = linijaParsirano[7].equals("S");
                    dodajAutoGrupeB(vremeH, vremeM, ime, model, pogon, godiste, superCharger);
                } else {
                    dodajAutoGrupeA(vremeH, vremeM, ime, model, pogon, godiste);
                }
            }
        } catch (IOException e) {
            uspesnost = false;
        }
        return uspesnost;
    }

    private void dodajAutoGrupeA(int vremeH, int vremeM, String ime, String model, Pogon pogon, int godiste) {
        GrupaAReliAuto auto = new GrupaAReliAuto(model, pogon, godiste);
        ReliVozac vozac = new ReliVozac(ime, auto);
        StavkaRasporeda stavkaRasporeda = new StavkaRasporeda(vozac, vremeH, vremeM);
        redVoznje.add(stavkaRasporeda);

    }

    private void dodajAutoGrupeB(int vremeH, int vremeM, String ime, String model, Pogon pogon, int godiste,
                                 boolean superCharger) {
        GrupaBReliAuto auto = new GrupaBReliAuto(model, pogon, godiste, superCharger);
        ReliVozac vozac = new ReliVozac(ime, auto);
        StavkaRasporeda stavkaRasporeda = new StavkaRasporeda(vozac, vremeH, vremeM);
        redVoznje.add(stavkaRasporeda);
    }

    @Override
    public String toString() {
       return String.join("\n", this.redVoznje.stream().map(StavkaRasporeda::toString).collect(Collectors.toList()));
        /* StringJoiner sj = new StringJoiner("\n");
        for (StavkaRasporeda r : redVoznje) {
            sj.add(r.toString());
        }
        return sj.toString() + "\n\n";
        */
    }

    public boolean dodaj(ReliVozac vozac, int h, int m) {
        boolean flag = true;
        for (StavkaRasporeda s : redVoznje) {
            if (Math.abs(s.startH * 60 + s.startM - h * 60 - m) < 30)
                flag = false;
        }

        if (flag)
            redVoznje.add(new StavkaRasporeda(vozac, h, m));

        return flag;
    }

    public void sortraj() {
        this.redVoznje.sort((e1, e2) -> e1.startH * 60 + e1.startM - e2.startH * 60 - e2.startH);
    }

    public int brAutomobilaSaGodVecimOd(int godiste) {
        return (int) this.redVoznje
                .stream()
                .filter(s -> s.vozac.auto.godiste > godiste)
                .count();
        /*int i = 0;
        for (StavkaRasporeda s : redVoznje){
            if(s.vozac.auto.godiste > godiste)
                i++;
        }
        return i;
        */
    }

    public List<ReliAuto> saPogonom(Pogon pogon) {
        //List<ReliAuto> lista = new ArrayList<>();
        return this.redVoznje
                .stream()
                .filter(e -> e.vozac.auto.tipPogona == pogon)
                .map(e -> e.vozac.auto)
                .collect(Collectors.toList());
        // return lista;
    }


}
