package procesi;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MenadzerGui extends Application {

    private MenadzerProcesa menadzerProcesa = new MenadzerProcesa();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        HBox root = new HBox(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        TextArea taIspis = new TextArea();
        taIspis.setMaxSize(400, 400);

        VBox right = new VBox(10);
        Button btnIzlistaj = new Button("Izlistaj");
        Button btnMem = new Button("Memorijski najzahtevniji");
        Button btnSis = new Button("Sistemski procesi");
        Button btnIskoriscenostCPU = new Button("Ukupna iskoriscenost CPU");
        TextField tfUnos = new TextField();
        Button btnStop = new Button("Zaustavi proces");

        right.getChildren().addAll(btnIzlistaj, btnMem, btnSis,
                btnIskoriscenostCPU, tfUnos, btnStop);

        root.getChildren().addAll(taIspis, right);

        if (menadzerProcesa.ucitajProcese("procesi.txt"))
            taIspis.setText("Uspesno ucitavanje procesa\n");
        else
            taIspis.setText("Neuspesno ucitavanje procesa\n");

        btnIzlistaj.setOnAction(event -> {
            taIspis.clear();
            menadzerProcesa.getProcesi().values().forEach(proces -> taIspis.appendText(proces.toString()));
            taIspis.appendText("\n");
        });

        btnMem.setOnAction(event -> {
            int granica;
            try {
                 granica = Integer.parseInt(tfUnos.getText().trim());
            }
            catch (NumberFormatException e){
                granica = 0;
            }
            taIspis.appendText(menadzerProcesa.memNajzahtevniji(granica).toString() + "\n");
        });

        btnSis.setOnAction(event -> {
            menadzerProcesa.getProcesi().values().forEach(proces -> {
                if(proces instanceof PozadinskiProces){
                    if(((PozadinskiProces) proces).isSistemski())
                        taIspis.appendText(proces.toString());
                }
            });
            taIspis.appendText("\n");
        });

        btnIskoriscenostCPU.setOnAction(event -> {
            taIspis.appendText(menadzerProcesa.ukupnaIskoriscenostCPU() + "\n");
        });

        btnStop.setOnAction(event -> {
            int pid = Integer.parseInt(tfUnos.getText());
            taIspis.appendText(menadzerProcesa.zaustaviProces(pid) ?
                            "Uspesno" + " uklonjen\n" : "Neuspelo " +
                    "uklanjanje\n");
        });
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Manager");
        primaryStage.show();
    }
}
