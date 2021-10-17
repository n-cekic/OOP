package busPlus;

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


import java.util.List;

public class GSPVoziloGUI extends Application {
    static GSPVozilo gsp;
    List<BusPlus> kartice = null;

    public static void main(String[] args) {
        gsp = new GSPVozilo();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        HBox root = new HBox(10);
        root.setPadding(new Insets(10,10,10,10));
        VBox left = new VBox(10);
        VBox right = new VBox(10);

        TextArea taLevo = new TextArea();
        taLevo.setEditable(false);
        taLevo.setPrefSize(300,550);
        Button btnputniciUVozilu = new Button("Putnici u vozilu");
        HBox radioBtns = new HBox(10);
        RadioButton rbPers = new RadioButton("Personalizovana");
        rbPers.setSelected(true);
        RadioButton rbNePers = new RadioButton("Nepersonalizovana");
        ToggleGroup tg = new ToggleGroup();
        rbNePers.setToggleGroup(tg);
        rbPers.setToggleGroup(tg);
        radioBtns.getChildren().addAll(rbPers, rbNePers);
        Button btnNoviPutnik = new Button("Novi putnik");
        left.getChildren().addAll(taLevo, btnputniciUVozilu, radioBtns, btnNoviPutnik);

        TextArea taDesno = new TextArea();
        taDesno.setPrefSize(300,550);
        Button btnKontrola = new Button("Kontrola");
        Button btnNapustanjeVozila= new Button("Napustanje vozila");

//        right.getChildren().addAll(taDesno, btnKontrola, btnNapustanjeVozila);
        right.getChildren().addAll(taDesno, btnKontrola);

        root.getChildren().addAll(left, right);

        btnputniciUVozilu.setOnAction(event -> {
            gsp.putniciUVozilu("kartice.txt");
            for(BusPlus karta : gsp.kartice.values()){
                taLevo.appendText(karta.toString());
            }
        });

        btnNoviPutnik.setOnAction(event -> {
            BusPlus novi;
            if(rbPers.isSelected())
                novi = gsp.noviPutnik(1);
            else
                novi = gsp.noviPutnik(2);
            taLevo.appendText("\n Novi putnik u vozilu:\n" + novi.toString());
        });

        btnKontrola.setOnAction(event -> {
            taDesno.appendText(gsp.kontrola());
        });

        Scene scene = new Scene(root,650,500);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
