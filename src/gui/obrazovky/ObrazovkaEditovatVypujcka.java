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
import logika.*;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * class ObrazovkaEditovatVypujcka
 */
public class ObrazovkaEditovatVypujcka extends Obrazovka implements Zobraz, Edituj {
    private final TableView<OblastZamereni> table = new TableView<>();
    private final ObservableList<OblastZamereni> tvObservableList = FXCollections.observableArrayList();

    private TextField id;
    private ComboBox kniha;
    private ComboBox ctenar;
    private DatePicker datumVypujcky;
    private DatePicker datumPredpokladanehoVraceni;
    private DatePicker datumVraceni;
    private TextField stav;
    private TextField ctenarId;
    private TextField ctenarJmeno;
    private TextField ctenarEmail;
    private TextField knihaId;
    private TextField knihaNazev;
    private TextField knihaAutor;
    private Label labelStav;
    private Label nazevObrazovky;
    private Label labelCtenarId;
    private Label labelCtenarJmeno;
    private Label labelCtenarEmail;
    private Label labelKnihaId;
    private Label labelKnihaNazev;
    private Label labelKnihaAutor;
    private Label labelId;
    private Label labelKniha;
    private Label labelCtenar;
    private Label labelDatumVypujcky;
    private Label labelDatumPredpokladanehoVraceni;
    private Label labelDatumVraceni;
    private Button tlacitkoUlozit;
    private Button tlacitkoZpet;
    private MysqlCon databaze;
    private Vypujcka vypujcka;
    private boolean novaVypujcka;


    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaEditovatVypujcka(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
    }

    /**
     * vytvoří a vrátí scenu této obrazovky
     * @return scenu této obrazovky
     */
    private Scene VytvoritScenu(){
        id = new TextField();
        ctenarId = new TextField();
        ctenarJmeno = new TextField();
        ctenarEmail = new TextField();
        knihaId = new TextField();
        knihaNazev = new TextField();
        knihaAutor = new TextField();
        stav = new TextField();
        id.setEditable(false);
        ctenarId.setEditable(false);
        ctenarJmeno.setEditable(false);
        ctenarEmail.setEditable(false);
        knihaId.setEditable(false);
        knihaNazev.setEditable(false);
        knihaAutor.setEditable(false);
        stav.setEditable(false);
        id.setStyle("-fx-background-color: #b5b5b5");
        ctenarId.setStyle("-fx-background-color: #b5b5b5");
        ctenarJmeno.setStyle("-fx-background-color: #b5b5b5");
        ctenarEmail.setStyle("-fx-background-color: #b5b5b5");
        knihaId.setStyle("-fx-background-color: #b5b5b5");
        knihaNazev.setStyle("-fx-background-color: #b5b5b5");
        knihaAutor.setStyle("-fx-background-color: #b5b5b5");
        stav.setStyle("-fx-background-color: #b5b5b5");

        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        labelStav = new Label("Stav");
        labelCtenarId = new Label("ID čtenáře");
        labelCtenarJmeno = new Label("Jméno čtenáře");
        labelCtenarEmail = new Label("Email čtenáře");
        labelKnihaId = new Label("ID knihy");
        labelKnihaNazev = new Label("Název knihy");
        labelKnihaAutor = new Label("Autor knihy");
        labelId = new Label("ID výpůjčky");
        labelKniha = new Label("Vyber knihu");
        labelCtenar = new Label("Vyber čtenáře");
        labelDatumVypujcky = new Label("Datum půjčení");
        labelDatumPredpokladanehoVraceni = new Label("Předpokládané datum vrácení");
        labelDatumVraceni = new Label("Skutečné datum Vrácení");

        kniha = new ComboBox();
        kniha.setPrefWidth(200);
        ctenar = new ComboBox();
        datumVypujcky = new DatePicker();
        datumPredpokladanehoVraceni = new DatePicker();
        datumVraceni = new DatePicker();
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
                if (novaVypujcka){
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_HLAVNI);
                }else {
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_VYPUJCKA_SEZNAM);
                }
            }
        });

        kniha.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                Kniha k = (Kniha) kniha.getValue();
                knihaAutor.setText(k.getAutor().getJmeno());
                knihaId.setText(k.getIdString());
                knihaNazev.setText(k.getNazev());
            }
        });
        ctenar.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                Ctenar c = (Ctenar) ctenar.getValue();
                ctenarId.setText(c.getIdString());
                ctenarJmeno.setText(c.getJmeno() + " " + c.getPrijmeni());
                ctenarEmail.setText(c.getEmail());
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20, 0, 0, 0));
        layout.setVgap(8);
        layout.setHgap(30);
        layout.getColumnConstraints().add(new ColumnConstraints(130));
        layout.getColumnConstraints().add(new ColumnConstraints(130));
        layout.setAlignment(Pos.TOP_CENTER);


        GridPane.setConstraints(nazevObrazovky,1,0, 3, 1);

        GridPane.setConstraints(labelKniha,0,1);
        GridPane.setConstraints(kniha,0,2);
        GridPane.setConstraints(labelKnihaId,0,3);
        GridPane.setConstraints(knihaId,0,4);
        GridPane.setConstraints(labelKnihaNazev,0,5);
        GridPane.setConstraints(knihaNazev,0,6);
        GridPane.setConstraints(labelKnihaAutor,0,7);
        GridPane.setConstraints(knihaAutor,0,8);
        
        GridPane.setConstraints(labelCtenar,1,1);
        GridPane.setConstraints(ctenar,1,2);
        GridPane.setConstraints(labelCtenarId,1,3);
        GridPane.setConstraints(ctenarId,1,4);
        GridPane.setConstraints(labelCtenarJmeno,1,5);
        GridPane.setConstraints(ctenarJmeno,1,6);
        GridPane.setConstraints(labelCtenarEmail,1,7);
        GridPane.setConstraints(ctenarEmail,1,8);

        GridPane.setConstraints(labelId,2,1);
        GridPane.setConstraints(id,2,2);
        GridPane.setConstraints(labelStav,2,3);
        GridPane.setConstraints(stav,2,4);
        GridPane.setConstraints(labelDatumVypujcky,2,5);
        GridPane.setConstraints(datumVypujcky,2,6);
        GridPane.setConstraints(labelDatumPredpokladanehoVraceni,2,7);
        GridPane.setConstraints(datumPredpokladanehoVraceni,2,8);
        GridPane.setConstraints(labelDatumVraceni,2,9);
        GridPane.setConstraints(datumVraceni,2,10);


        GridPane.setConstraints(tlacitkoUlozit,0,9);
        GridPane.setConstraints(tlacitkoZpet,0,10);

        layout.getChildren().addAll(nazevObrazovky);
        layout.getChildren().addAll(labelKniha, kniha, labelKnihaId, knihaId, labelKnihaNazev, knihaNazev, labelKnihaAutor, knihaAutor);
        layout.getChildren().addAll(labelCtenar, ctenar, labelCtenarId, ctenarId, labelCtenarJmeno, ctenarJmeno, labelCtenarEmail, ctenarEmail);
        layout.getChildren().addAll(labelId, id, labelStav, stav, labelDatumVypujcky, datumVypujcky, labelDatumPredpokladanehoVraceni, datumPredpokladanehoVraceni, labelDatumVraceni, datumVraceni);
        layout.getChildren().addAll(tlacitkoUlozit, tlacitkoZpet);

        return new Scene(layout, SCENE_WIDTH_DEFAULT, SCENE_HEIGHT_DEFAULT);
    }

    /**
     * upravi  a vrátí scenu této obrazovky pro založení
     *
     * @return scenu této obrazovky pro založení
     */
    @Override
    public Scene getScene() {
        Scene scena = VytvoritScenu();
        novaVypujcka = true;
        nazevObrazovky.setText("Založit výpůjčku");
        nastavitSeznamy();
        datumVypujcky.setValue(LocalDate.now());
        datumPredpokladanehoVraceni.setValue(LocalDate.now().plusMonths(1));
        return scena;
    }

    /**
     * upravi  a vrátí scenu této obrazovky pro editaci
     *
     * @param idVypujcky
     * @return scenu této obrazovky pro editaci
     */
    @Override
    public Scene getScene(int idVypujcky) {
        Scene scena = VytvoritScenu();
        novaVypujcka = false;
        nazevObrazovky.setText("Editovat výpůjčku");
        vypujcka = databaze.getVypujcka(idVypujcky);
        nastavitSeznamy();
        nastavStav();

        id.setText(String.valueOf(vypujcka.getId()));
        ctenarId.setText(String.valueOf(vypujcka.getIdCtenar()));
        ctenarJmeno.setText(String.valueOf(vypujcka.getCtenar().getCeleJmeno()));
        ctenarEmail.setText(String.valueOf(vypujcka.getCtenar().getEmail()));
        knihaId.setText(String.valueOf(vypujcka.getIdKniha()));
        knihaNazev.setText(String.valueOf(vypujcka.getKniha().getNazev()));
        knihaAutor.setText(String.valueOf(vypujcka.getKniha().getAutor()));
        kniha.setValue(vypujcka.getKniha());
        ctenar.setValue(vypujcka.getCtenar());
        datumVypujcky.setValue(vypujcka.getDatumPujceni());
        datumPredpokladanehoVraceni.setValue(vypujcka.getPredpokladeneDatumVraceni());
        datumVraceni.setValue(vypujcka.getDatumVraceni());
        return scena;
    }

    /**
     * nacte hodnoty do ComboBoxu
     */
    private void nastavitSeznamy(){
        databaze = new MysqlCon();
        ArrayList<Kniha> knihy = databaze.getKnihy();
        ArrayList<Ctenar> ctenari = databaze.getCtenari();
        kniha.getItems().clear();
        ctenar.getItems().clear();

        for (Kniha k: knihy) {
            kniha.getItems().add(k);
        }
        for (Ctenar c: ctenari) {
            ctenar.getItems().add(c);
        }
    }

    /**
     * nastevy text v TextBoxu stav
     */
    private void nastavStav(){
        if(vypujcka != null){
            if (vypujcka.getDatumVraceni() == null || vypujcka.getDatumVraceni().compareTo(LocalDate.now()) > 0){
                stav.setText("aktivní");
            }else {
                stav.setText("vráceno");
            }
        }
    }

    /**
     * kontroluje udaje a vyhodi varovne okno, pokud udaje chyby
     *
     * @return spravnost udaju
     */
    private boolean kontrolaUdaju(){
        if (!(kniha.getValue() instanceof Kniha)){
            new VarovnyDiIalog("Prosím vyberte knihu.").showAndWait();
            return false;
        }
        else if (!(ctenar.getValue() instanceof Ctenar)){
            new VarovnyDiIalog("Prosím vyberte oblast zaměření.").showAndWait();
            return false;
        }
        else if (datumVypujcky.getValue() == null ){
            new VarovnyDiIalog("Prosím vyberte datum vypůjčky.").showAndWait();
            return false;
        }
        else if (datumPredpokladanehoVraceni.getValue() == null ){
            new VarovnyDiIalog("Prosím vyberte datum předpokládaného vrácení.").showAndWait();
            return false;
        }
        else if (datumPredpokladanehoVraceni.getValue().compareTo(datumVypujcky.getValue()) < 0){
            new VarovnyDiIalog("Datum předpokládaného vrácení nemůže být starší než datum výpujčky").showAndWait();
            return false;
        }
        else if (datumVraceni.getValue() != null && datumVraceni.getValue().compareTo(datumVypujcky.getValue()) < 0){
            new VarovnyDiIalog("Datum vrácení nemůže být starší než datum výpujčky").showAndWait();
            return false;
        }

        return true;
    }

    /**
     * vytvoří nebo edituje položku v databázi
     */
    private void uloz(){
        if (kontrolaUdaju()) {
            if(novaVypujcka) {
                Kniha k = (Kniha) kniha.getValue();
                Ctenar c = (Ctenar) ctenar.getValue();
                Vypujcka vypujcka = new Vypujcka(datumVypujcky.getValue(), datumPredpokladanehoVraceni.getValue(), datumVraceni.getValue(), k, c);
                databaze.addVypujcka(vypujcka);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_VYPUJCKA_SEZNAM);
            }else{
                Kniha k = (Kniha) kniha.getValue();
                Ctenar c = (Ctenar) ctenar.getValue();
                Vypujcka vypujcka = new Vypujcka(Integer.parseInt(id.getText()), datumVypujcky.getValue(), datumPredpokladanehoVraceni.getValue(), datumVraceni.getValue(), k, c);
                databaze.editVypujcka(vypujcka);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_VYPUJCKA_SEZNAM);
            }
        }
    }
}
