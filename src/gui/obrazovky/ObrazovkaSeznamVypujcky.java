package gui.obrazovky;

import gui.Gui;
import gui.pomocne.NavigacniButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import logika.MysqlCon;
import logika.Vypujcka;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * class ObrazovkaSeznamVypujcky
 */
public class ObrazovkaSeznamVypujcky extends Obrazovka implements Zobraz{
    private NavigacniButton TlacitkoZpet;
    private ComboBox filtr;
    private Label labelFiltr;
    private ArrayList<Vypujcka> vypujcky;
    private Label nazevObrazovky;


    /**
     * enum s variantami filtru
     */
    private enum NastaveniFiltru{
        VSE("Vše"),
        SPLATNE_DNES("Splatné dnes"),
        PO_SPLATNOSTI("Po splatnosti");

        private String nazev;

        NastaveniFiltru(String envUrl) {
            this.nazev = envUrl;
        }

        public String getNazev() {
            return nazev;
        }

        @Override
        public String toString() {
            return nazev;
        }
    }

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaSeznamVypujcky(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
        this.TlacitkoZpet = new NavigacniButton("Zpět", Gui.ObrazovkaJmeno.OBR_HLAVNI, gui);

        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        nazevObrazovky.setText("Seznam výpůjček");
    }

    /**
     * vytvoří scenu se seznamem výpůjček
     *
     * @return scenu se seznamem výpůjček
     */
    @Override
    public Scene getScene() {
        MysqlCon databaze = new MysqlCon();
        vypujcky = databaze.getVypujcky();
        labelFiltr = new Label("Filtr");

        final TableView<Vypujcka> table = VytvoritTabulku();

        filtr = new ComboBox();
        filtr.getItems().addAll(NastaveniFiltru.VSE, NastaveniFiltru.SPLATNE_DNES, NastaveniFiltru.PO_SPLATNOSTI);
        filtr.setValue(NastaveniFiltru.VSE);
        filtr.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                ObservableList<Vypujcka> x = FXCollections.observableArrayList();
                NastaveniFiltru nastaveni = (NastaveniFiltru) filtr.getValue();
                table.setItems(filtrovat(nastaveni));
            }
        });


        VBox vBox = new VBox(nazevObrazovky, labelFiltr, filtr, table,  TlacitkoZpet);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        return new Scene(vBox, SCENE_WIDTH_LONG, SCENE_HEIGHT_DEFAULT);
    }


    /**
     * vyfiltruje vypujčky dle zvoleného filtru
     * @param nastaveni NastaveniFiltru
     * @return ObservableList s vypujčkamy dle zvoleného filtru
     */
    private ObservableList<Vypujcka> filtrovat(NastaveniFiltru nastaveni){
        ArrayList<Vypujcka> vysledek = new ArrayList<>();

        if (nastaveni == NastaveniFiltru.SPLATNE_DNES){
            for ( Vypujcka vypujcka : vypujcky) {
                if (vypujcka.getPredpokladeneDatumVraceni().compareTo(LocalDate.now()) == 0){
                    vysledek.add(vypujcka);
                }
            }
        }
        else if (nastaveni == NastaveniFiltru.PO_SPLATNOSTI){
            for ( Vypujcka vypujcka : vypujcky) {
                if (vypujcka.getPredpokladeneDatumVraceni().compareTo(LocalDate.now()) < 0){
                    vysledek.add(vypujcka);
                }
            }
        }else {
            vysledek = vypujcky;
        }

        return FXCollections.observableArrayList(vysledek);
    }

    /**
     * vytvoří tabulku se seznamem výpůjček
     * @return tabulku se seznamem výpůjček
     */
    private TableView<Vypujcka> VytvoritTabulku() {
        final TableView<Vypujcka> table = new TableView<>();

//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(740);
        table.setPrefHeight(300);


        ObservableList<Vypujcka> vyfiltrovanyList = FXCollections.observableArrayList(vypujcky);
        table.setItems(vyfiltrovanyList);

        TableColumn<Vypujcka, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Vypujcka, String> colOblastId = new TableColumn<>("Vypůjčeno");
        colOblastId.setCellValueFactory(new PropertyValueFactory<>("datumPujceni"));

        TableColumn<Vypujcka, String> colPredpokladaneDatumVraceni = new TableColumn<>("Vypůjčeno do");
        colPredpokladaneDatumVraceni.setCellValueFactory(new PropertyValueFactory<>("predpokladeneDatumVraceni"));

        TableColumn<Vypujcka, String> colDatumVraceni = new TableColumn<>("Vráceno");
        colDatumVraceni.setCellValueFactory(new PropertyValueFactory<>("datumVraceni"));

        TableColumn<Vypujcka, String> colName = new TableColumn<>("Kniha");
        colName.setCellValueFactory(new PropertyValueFactory<>("kniha"));

        TableColumn<Vypujcka, String> colAutor = new TableColumn<>("Čtenář");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("ctenar"));

        TableColumn<Vypujcka, Void> colEdit = VytvoritSloupecEditovat();

        colId.prefWidthProperty().bind(table.widthProperty().multiply(0.05));
        colName.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        colAutor.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        colOblastId.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        colPredpokladaneDatumVraceni.prefWidthProperty().bind(table.widthProperty().multiply(0.13));
        colDatumVraceni.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        colEdit.prefWidthProperty().bind(table.widthProperty().multiply(0.09));




        table.getColumns().addAll(colId, colName, colAutor, colOblastId, colPredpokladaneDatumVraceni, colDatumVraceni, colEdit);

        return table;
    }

    /**
     * vytvoří sloupec s talčítky editovat
     * @return
     */
    private TableColumn<Vypujcka, Void> VytvoritSloupecEditovat() {
        TableColumn<Vypujcka, Void> colBtn = new TableColumn("Akce");

        Callback<TableColumn<Vypujcka, Void>, TableCell<Vypujcka, Void>> cellFactory = new Callback<TableColumn<Vypujcka, Void>, TableCell<Vypujcka, Void>>() {
            @Override
            public TableCell<Vypujcka, Void> call(final TableColumn<Vypujcka, Void> param) {
                final TableCell<Vypujcka, Void> cell = new TableCell<Vypujcka, Void>() {

                    private final Button btn = new Button("Editovat");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vypujcka data = getTableView().getItems().get(getIndex());
                            gui.setSceen(Gui.ObrazovkaJmeno.OBR_VYPUJCKA_EDITOVAT, data.getId());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };


        colBtn.setCellFactory(cellFactory);

        return colBtn;
    }

}
