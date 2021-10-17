package dragulji;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mapa extends Application {
    List<ObjekatIgre> objekti = new ArrayList<>();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox(10);
        root.setPadding(new Insets(10,10,10,10));

        Button btnUcitaj = new Button("Ucitaj");
        TextArea taTop = new TextArea();
        TextArea taBottom = new TextArea();
        RadioButton rbDD = new RadioButton("Dole ili Desno(x++ / y++)");
        rbDD.setSelected(true);
        RadioButton rbGD = new RadioButton("Gore ili Levo(x-- / y--)");
        ToggleGroup tg = new ToggleGroup();
        rbDD.setToggleGroup(tg);
        rbGD.setToggleGroup(tg);

        HBox akcija = new HBox(10);
        Button btnSmer = new Button("Izaberi smer");
        Button btnPotez = new Button("Simuliraj potez");
        akcija.getChildren().addAll(btnSmer, btnPotez);

        root.getChildren().addAll(btnUcitaj, taTop, taBottom, rbDD, rbGD, akcija);

        btnUcitaj.setOnAction(event -> {
            ucitajObjekte("dragulji.txt");
            sortirajObjekte(objekti);
            taTop.setText(ispisiObjekte(objekti));
        });

        btnSmer.setOnAction(event -> {
            for(ObjekatIgre objekat : objekti){
                if(objekat instanceof Tragac){
                    ((Tragac)objekat).setSmerKretanja(rbDD.isSelected()? "dd" : "gl");
                }
            }
        });

        btnPotez.setOnAction(event -> {
            for (ObjekatIgre objekat : objekti){
                objekat.izvrsitiAkciju();
            }
            int i = 0;
            for (ObjekatIgre tragac : objekti){
                for (ObjekatIgre rudnik : objekti){
                    if(tragac instanceof Tragac && rudnik instanceof Rudnik){
                        if(((Rudnik) rudnik).isOtvoren()){
                            if(tragac.X == rudnik.X && tragac.Y == rudnik.Y){
                                ((Tragac) tragac).pokupiDragulj(((Rudnik) rudnik));
                                taBottom.setText(tragac.naziv + " je sakupio " + rudnik.naziv + "\n");
                                i++;
                            }
                        }
                        
                    }
                }
            }
            taBottom.appendText("Sakupljeno dragulja " + i + "\n");
            taBottom.appendText(ispisiObjekte(objekti));
        });

        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private String ispisiObjekte(List<ObjekatIgre> objekti) {
        StringBuilder sb = new StringBuilder();
        for(ObjekatIgre objekat : objekti){
            sb.append(objekat.toString());
            sb.append("\n");
        }
        sb.append("\n");
        sb.append("\n");
        return sb.toString();
    }

    private void sortirajObjekte(List<ObjekatIgre> objekti) {
        objekti.sort((o1, o2) -> {
            if(o1 instanceof Rudnik && o2 instanceof Tragac)
                return -1;
            else if(o2 instanceof Rudnik && o1 instanceof Tragac)
                return 1;

            return 0;
        });

        objekti.sort((o1, o2) -> {
            if(o1 instanceof Rudnik && o2 instanceof Rudnik){
                return ((Rudnik) o1).otvorenJos - ((Rudnik) o2).otvorenJos;
            }
            else if(o1 instanceof Tragac && o2 instanceof Tragac){
                return ((Tragac) o2).ranac.maxTezina - ((Tragac) o1).ranac.maxTezina;
            }
            else return 0;
        });
    }

    private void ucitajObjekte(String putanja) {
        try {
            List<String> linije = Files.readAllLines(Paths.get(putanja));
            for(String linija : linije){
                String[] linijaPodeljeno = linija.split(",");
                String ime = linijaPodeljeno[1].trim();
                int koordX = Integer.parseInt(linijaPodeljeno[2].trim());
                int koordY = Integer.parseInt(linijaPodeljeno[3].trim());
                int tezina = Integer.parseInt(linijaPodeljeno[4].trim());
                if(linijaPodeljeno[0].equals("t")){
                    Ranac ranac = new Ranac(tezina);
                    Tragac tragac = new Tragac(ime, koordX, koordY, ranac);
                    objekti.add(tragac);
                }
                else if (linijaPodeljeno[0].equals("r")){
                    int josOtvoren = Integer.parseInt(linijaPodeljeno[5].trim());
                    Rudnik rudnik = new Rudnik(ime, koordX, koordY, josOtvoren, tezina);
                    objekti.add(rudnik);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        objekti.sort((o1, o2) -> {
//            if(o1 instanceof Rudnik && o2 instanceof Tragac)
//                return -1;
//            else if(o2 instanceof Rudnik && o1 instanceof Tragac)
//                return 1;
//
//            return 0;
//        });
//
//        objekti.sort((o1, o2) -> {
//            if(o1 instanceof Rudnik && o2 instanceof Rudnik){
//                return ((Rudnik) o1).otvorenJos - ((Rudnik) o2).otvorenJos;
//            }
//            else if(o1 instanceof Tragac && o2 instanceof Tragac){
//                return ((Tragac) o2).ranac.maxTezina - ((Tragac) o1).ranac.maxTezina;
//            }
//            else return 0;
//        });
    }
}
