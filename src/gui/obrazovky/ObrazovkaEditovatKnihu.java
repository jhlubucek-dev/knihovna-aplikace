package gui.obrazovky;

import gui.Gui;
import gui.pomocne.VarovnyDiIalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logika.Autor;
import logika.Kniha;
import logika.MysqlCon;
import logika.OblastZamereni;

import java.util.ArrayList;

/**
 * class ObrazovkaEditovatKnihu
 */
public class ObrazovkaEditovatKnihu extends Obrazovka implements Zobraz, Edituj {
    private final TableView<OblastZamereni> table = new TableView<>();
    private final ObservableList<OblastZamereni> tvObservableList = FXCollections.observableArrayList();

    private TextField id;
    private ComboBox autorComboBox;
    private TextField nazev;
    private Label labelId;
    private Label labelAutor;
    private Label labelNazev;
    private Label labelOblast;
    private Label nazevObrazovky;
    private ComboBox oblastNazev;
    private Button tlacitkoUlozit;
    private Button tlacitkoZpet;
    private MysqlCon databaze;
    private boolean novaKniha;
    private Kniha kniha;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaEditovatKnihu(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
    }


    /**
     * vytvoří a vrátí scenu této obrazovky
     *
     * @return scenu této obrazovky
     */
    private Scene VytvoritScenu(){
        id = new TextField();
        id.setEditable(false);
        id.setStyle("-fx-background-color: #b5b5b5");
        autorComboBox = new ComboBox();
        nazev = new TextField();
        labelId = new Label("ID");
        labelAutor = new Label("Autor");
        labelNazev = new Label("Název");
        labelOblast = new Label("Oblast zaměření");
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");

        oblastNazev = new ComboBox();
        databaze = new MysqlCon();
        tlacitkoUlozit = new Button("Uložit");
        tlacitkoUlozit.setStyle("-fx-background-color: #7a09ff");
        tlacitkoUlozit.setTextFill(Color.WHITE);
        tlacitkoUlozit.setPrefWidth(100);
        tlacitkoUlozit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                uloz();
            }
        });

        tlacitkoZpet = new Button("Zpět");
        tlacitkoZpet.setPrefWidth(100);
        tlacitkoZpet.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                if (novaKniha){
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_HLAVNI);
                }else {
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_KNIHA_SEZNAM);
                }
            }
        });
        nastavitSeznamy();

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20, 0, 0, 0));
        layout.setVgap(8);
        layout.setHgap(10);
        layout.getColumnConstraints().add(new ColumnConstraints(100));
        layout.getColumnConstraints().add(new ColumnConstraints(100));
//        layout.
        layout.setAlignment(Pos.TOP_CENTER);


        GridPane.setConstraints(nazevObrazovky,0,0,2 ,1);
        GridPane.setConstraints(labelId,0,1,1 ,1);
        GridPane.setConstraints(id,0,2,2 ,1);
        GridPane.setConstraints(labelNazev,0,3,1 ,1);
        GridPane.setConstraints(nazev,0,4, 2, 1);
        GridPane.setConstraints(labelAutor,0,5,1 ,1);
        GridPane.setConstraints(autorComboBox,0,6, 2, 1);
        GridPane.setConstraints(labelOblast,0,7,1 ,1);
        GridPane.setConstraints(oblastNazev,0,8, 2, 1);
        GridPane.setConstraints(tlacitkoUlozit,1,9);
        GridPane.setConstraints(tlacitkoZpet,0,9);

        layout.getChildren().addAll(nazevObrazovky, id, nazev, autorComboBox, oblastNazev, labelId, labelAutor, labelNazev, labelOblast, tlacitkoUlozit, tlacitkoZpet);
        return new Scene(layout, SCENE_WIDTH_DEFAULT, SCENE_HEIGHT_DEFAULT);
    }

    /**
     * upravií  a vrátí scenu této obrazovky pro založení
     *
     * @return scenu této obrazovky pro založení
     */
    @Override
    public Scene getScene() {
        Scene scena = VytvoritScenu();
        novaKniha = true;
        nazevObrazovky.setText("Založit knihu");

//        id.setText(String.valueOf(databaze.getKnihaNexId()));
        id.setEditable(false);
        return scena;
    }

    /**
     * upraví  a vrátí scenu této obrazovky pro editaci
     *
     * @return scenu této obrazovky pro editaci
     */
    @Override
    public Scene getScene(int idKnihy) {
        Scene scena = VytvoritScenu();
        novaKniha = false;
        nazevObrazovky.setText("Editovat knihu");

        nastavitSeznamy();
        kniha = databaze.getKniha(idKnihy);
        id.setText(kniha.getIdString());
        nazev.setText(kniha.getNazev());
        autorComboBox.setValue(kniha.getAutor());
        oblastNazev.setValue(kniha.getOblastZamereni());
        return scena;
    }

    /**
     * nacte hodnoty do ComboBoxu
     */
    private void nastavitSeznamy(){
        ArrayList<OblastZamereni> oblasti = databaze.getOblasti();
        oblastNazev.getItems().clear();

        for (OblastZamereni oblast: oblasti) {
            oblastNazev.getItems().add(oblast);
        }

        ArrayList<Autor> autori = databaze.getAutori();
        autorComboBox.getItems().clear();

        for (Autor autor: autori) {
            autorComboBox.getItems().add(autor);
        }
    }

    /**
     * kontroluje udaje a vyhodi varovne okno, pokud udaje chyby
     *
     * @return spravnost udaju
     */
    private boolean kontrolaUdaju(){
        if (!(oblastNazev.getValue() instanceof OblastZamereni)){
            new VarovnyDiIalog("Prosím vyberte oblast zaměření.").showAndWait();
            return false;
        }else if (!(autorComboBox.getValue() instanceof Autor)){
            new VarovnyDiIalog("Prosím vyberte autora.").showAndWait();
            return false;
        }
        return true;
    }

    /**
     * vytvoří nebo edituje položku v databázi
     */
    private void uloz(){
        if (kontrolaUdaju()) {
            OblastZamereni oblast = (OblastZamereni) oblastNazev.getValue();
            Autor autor = (Autor) autorComboBox.getValue();
            if(novaKniha) {
                kniha = new Kniha(nazev.getText(), autor, oblast);
                databaze.addKniha(kniha);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_KNIHA_SEZNAM);
            }else{
                kniha.setNazev(nazev.getText());
                kniha.setAutor(autor);
                kniha.setOblastZamereni(oblast);
                databaze.editKniha(kniha);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_KNIHA_SEZNAM);
            }
        }
    }
}
