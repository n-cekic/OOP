package procesi;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class MenadzerProcesa {
    private Map<Integer, Proces> procesi;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        procesi.values().forEach(proces -> sb.append(proces));
        return sb.toString();
    }

    public Map<Integer, Proces> getProcesi() {
        return procesi;
    }

    public boolean ucitajProcese(String putanja) {
        try {
            List<String> linije = Files.readAllLines(Paths.get(putanja));
            procesi = new HashMap<>();
            for (String linija : linije) {
                Proces proces;
                String[] podeljeno = linija.split(",");
                int pID = Integer.parseInt(podeljeno[1].trim());
                String naziv = podeljeno[2].trim();
                int memZauzece = Integer.parseInt(podeljeno[3].trim());
                if (podeljeno[0].trim().equals("A")) {
                    int iskoriscenostCPU = Integer.parseInt(podeljeno[4].trim());
                    proces = new AktivniProces(pID, naziv, memZauzece,
                            iskoriscenostCPU);
                } else {
                    boolean sistemski = (podeljeno.length == 5);
                    proces = new PozadinskiProces(pID, naziv, memZauzece, sistemski);
                }
                procesi.put(pID, proces);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //trazim u mapi proces koji zauzima manje memorije od gornjaGranica
    // (ako je gornjaGranica == 0 uzimam sve u obzir) i vracam ga (odnosno
    // prvi takav ako ih ima vise)
    public Proces memNajzahtevniji(int gornjaGranica) {
        return procesi.values().stream().filter(proces -> {
            if (proces.getMemZauzece() - gornjaGranica > 0 && gornjaGranica != 0) {
                return true;
            } else if (gornjaGranica == 0) {
                return true;
            } else
                return false;
        }).findFirst().orElse(null);
    }

    //iz mape izvlacim i vracam sistemske procese
    public List<Proces> sistemskiProcesi() {
        return procesi.values().stream().filter(proces -> {
            if (proces instanceof PozadinskiProces) {
                return ((PozadinskiProces) proces).isSistemski();
            } else
                return false;
        }).collect(Collectors.toList());
    }

    public int ukupnaIskoriscenostCPU() {
        return procesi.values().stream()
                .filter(proces -> proces instanceof AktivniProces)
                .mapToInt(proces -> ((AktivniProces) proces).getIskoriscenostCPU())
                .reduce((mem1, mem2) -> mem1 + mem2).orElse(0);
    }

    public boolean zaustaviProces(int pID) {
        if ( procesi.remove(pID) == null)
            return false;
        else
            return true;
    }
}
