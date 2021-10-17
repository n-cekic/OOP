package reli;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DirtRally3 extends Application {

    private static ReliRaspored raspored;

    public static void main(String[] args) {
        raspored = new ReliRaspored();
        raspored.ucitaj("raspored.txt");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));
        TextArea taIspis = new TextArea();
        taIspis.setText(raspored.toString());

        VBox right = new VBox(10);
        TextField tfIme = new TextField();

        HBox radBtnsTop = new HBox(10);
        RadioButton rbGrupaA = new RadioButton("Grupa A");
        rbGrupaA.setSelected(true);
        RadioButton rbGrupaB = new RadioButton("Grupa B");
        ToggleGroup top = new ToggleGroup();
        rbGrupaA.setToggleGroup(top);
        rbGrupaB.setToggleGroup(top);
        radBtnsTop.getChildren().addAll(rbGrupaA, rbGrupaB);

        TextField tfModel = new TextField();
        TextField tfPogon = new TextField();
        TextField tfGodiste = new TextField();

        HBox bottom = new HBox(10);
        Label sch = new Label("Supercharger:");
        RadioButton rbDa = new RadioButton("Da");
        RadioButton rbNe = new RadioButton("Ne");
        rbNe.setSelected(true);
        ToggleGroup tgBotom = new ToggleGroup();
        rbDa.setToggleGroup(tgBotom);
        rbNe.setToggleGroup(tgBotom);
        bottom.getChildren().addAll(sch, rbDa, rbNe);
        TextField tfVremeH = new TextField();
        TextField tfVremeM = new TextField();
        Button brojAutomobila = new Button("Broj Automobila");
        Button izlistaj = new Button("Izlistaj");
        Button dodaj = new Button("Dodaj");
        Button saPogonom = new Button("Sa poonom");
        right.getChildren().addAll(tfIme, radBtnsTop, tfModel, tfPogon, tfGodiste, bottom, tfVremeH, tfVremeM, izlistaj, dodaj, brojAutomobila, saPogonom);
        root.getChildren().addAll(taIspis, right);

        izlistaj.setOnAction(e -> {
            raspored.sortraj();
            taIspis.appendText("\n" + raspored.toString() + "\n");
        });

        dodaj.setOnAction(event -> {
            StavkaRasporeda novaStavka;
            ReliAuto auto;
            String ime = tfIme.getText();
            String model = tfModel.getText();
            Pogon pogon = Pogon.izNiske(tfPogon.getText().trim());
            int godiste = Integer.parseInt(tfGodiste.getText().trim());
            boolean superCharger = rbDa.isSelected();
            int startH = Integer.parseInt(tfVremeH.getText().trim());
            int startM = Integer.parseInt(tfVremeM.getText().trim());

            if (rbGrupaA.isSelected())
                auto = new GrupaAReliAuto(model, pogon, godiste);
            else
                auto = new GrupaBReliAuto(model, pogon, godiste, superCharger);
            ReliVozac vozac = new ReliVozac(ime, auto);
            if (raspored.dodaj(vozac, startH, startM))
                taIspis.appendText("Ddato u " + startH + ":" + startM + "\n");
            else
                taIspis.appendText("Neuspelo dodavanje u raspored\n");
        });

        brojAutomobila.setOnAction(e -> {
            int godiste = Integer.parseInt(tfGodiste.getText().trim());
            taIspis.appendText("Broj automobila sa godistem vecim od " +
                    "navedenog" + raspored.brAutomobilaSaGodVecimOd(godiste) + "\n");
        });


        Scene scene = new Scene(root, 700, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }
}
