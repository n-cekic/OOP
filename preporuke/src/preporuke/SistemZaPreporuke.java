package preporuke;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SistemZaPreporuke extends Application {
    private static Random random = new Random();
    private Map<ZabavniSadrzaj, List<Integer>> zabavniSadrzaj = new TreeMap<>();
    private Korisnik korisnik = new Korisnik("John Wick");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        VBox root = new VBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        Button btnUcitaj = new Button("Ucitaj");
        TextArea taTop = new TextArea();

        HBox radioBtns = new HBox(10);
        RadioButton rbIG = new RadioButton("Izvodjac/Glumac");
        RadioButton rbZG = new RadioButton("Zanr/Godina");
        ToggleGroup tg = new ToggleGroup();
        rbIG.setSelected(true);
        rbIG.setToggleGroup(tg);
        rbZG.setToggleGroup(tg);

        radioBtns.getChildren().addAll(rbIG, rbZG);

        HBox uslov = new HBox(10);
        Label lblUslov = new Label("Unesite uslov pretrage: ");
        TextField tfUslov = new TextField();
        Button btnUslov = new Button("Preporuka");
        uslov.getChildren().addAll(lblUslov, tfUslov, btnUslov);

        Label lblPreporuke = new Label("Preporuke:");
        TextArea taBottom = new TextArea();

        HBox bottom = new HBox(10);
        bottom.setAlignment(Pos.CENTER);
        Label lblOcena = new Label("Ocenite sadrzaj: ");
        TextField tfOcena = new TextField();
        Button btnOcena = new Button("Oceni");

        bottom.getChildren().addAll(lblOcena, tfOcena, btnOcena);

        root.getChildren().addAll(btnUcitaj, taTop, radioBtns, uslov, lblPreporuke, taBottom, bottom);


        btnUcitaj.setOnAction(event -> {
            ucitajIzFajla("preporuke.txt");
            taTop.setText(ispisiSadrzaj(zabavniSadrzaj));
        });

        btnUslov.setOnAction(event -> {
            int flag = 0;
            for (ZabavniSadrzaj z : zabavniSadrzaj.keySet()) {
                boolean b = z.zaPreporuku(tfUslov.getText().trim(), rbIG.isSelected() ? 1 : 2, korisnik.ocenjeniSadrzaj.keySet());
                if (b){
                    Map<ZabavniSadrzaj, List<Integer>> temp = new TreeMap<>();
                    temp.put(z, zabavniSadrzaj.get(z));
                    taBottom.appendText(ispisiSadrzaj(temp));
                    flag++;
                }
            }
            if(flag == 0 )
                taBottom.appendText("Nisu ispunjeni kriterijumi\n");
        });


        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String ispisiSadrzaj(Map<ZabavniSadrzaj, List<Integer>> zabavniSadrzaj) {
        StringBuilder sb = new StringBuilder();
        for (ZabavniSadrzaj z : zabavniSadrzaj.keySet()) {
            sb.append(z.toString());
            OptionalDouble ocena = zabavniSadrzaj.get(z).stream().mapToDouble(i -> i).reduce((a, b) -> a + b);
            sb.append(" " + Math.round(ocena.getAsDouble() / zabavniSadrzaj.get(z).size()));
            sb.append("\n");
        }
        return sb.toString();
    }

    private void ucitajIzFajla(String s) {
        try {
            List<String> linije = Files.readAllLines(Paths.get(s));

            for (String linija : linije) {
                String[] podeljeno = linija.split(",");
                String naziv = podeljeno[1];
                int trajanje = Integer.parseInt(podeljeno[2].trim());
                if (podeljeno[0].equals("f")) {
                    int godina = Integer.parseInt(podeljeno[3].trim());
                    String ime = podeljeno[4];
                    int i = 5;
                    List<Integer> ocene = new ArrayList<>();
                    for (; i < podeljeno.length; i++)
                        ocene.add(Integer.parseInt(podeljeno[i].trim()));
                    Film film = new Film(naziv, trajanje, ime, godina);

                    zabavniSadrzaj.put(film, ocene);
                }
                if (podeljeno[0].equals("p")) {
                    String izvodjac = podeljeno[3];
                    String zanr = podeljeno[4];
                    int i = 5;
                    List<Integer> ocene = new ArrayList<>();
                    for (; i < podeljeno.length; i++)
                        ocene.add(Integer.parseInt(podeljeno[i].trim()));
                    Pesma pesma = new Pesma(naziv, trajanje, izvodjac, zanr);
                    zabavniSadrzaj.put(pesma, ocene);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
