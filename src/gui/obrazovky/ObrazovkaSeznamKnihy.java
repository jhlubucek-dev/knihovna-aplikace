package gui.obrazovky;

import gui.Gui;
import gui.pomocne.NavigacniButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import logika.Kniha;
import java.util.ArrayList;

/**
 * class ObrazovkaSeznamKnihy
 */
public class ObrazovkaSeznamKnihy extends Obrazovka implements Zobraz{
    private NavigacniButton TlacitkoZpet;
    private Label nazevObrazovky;


    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaSeznamKnihy(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
        this.TlacitkoZpet = new NavigacniButton("Zpět", Gui.ObrazovkaJmeno.OBR_HLAVNI, gui);
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        nazevObrazovky.setText("Seznam knih");
    }

    /**
     * vytvoří scenu se seznamem knih
     *
     * @return scenu se seznamem knih
     */
    @Override
    public Scene getScene() {
        VBox vBox = new VBox(nazevObrazovky, VytvoritTabulku(), TlacitkoZpet);
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(20, 0, 0, 0));
        vBox.setAlignment(Pos.TOP_CENTER);
        return new Scene(vBox, SCENE_WIDTH_LONG, SCENE_HEIGHT_DEFAULT);
    }

    /**
     * vytvoří tabulku se seznamem knih
     * @return tabulku se seznamem knih
     */
    private TableView<Kniha> VytvoritTabulku() {
        final TableView<Kniha> table = new TableView<>();

//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(600);
        table.setPrefHeight(300);

        MysqlCon databaze = new MysqlCon();
        ObservableList<Kniha> vyfiltrovanzList = FXCollections.observableArrayList(databaze.getKnihy());
        table.setItems(vyfiltrovanzList);

        TableColumn<Kniha, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Kniha, String> colName = new TableColumn<>("Název");
        colName.setCellValueFactory(new PropertyValueFactory<>("nazev"));

        TableColumn<Kniha, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Kniha, String> colOblastNazev = new TableColumn<>("Oblast Zamereni");
        colOblastNazev.setCellValueFactory(new PropertyValueFactory<>("OblastNazev"));

        TableColumn<Kniha, Void> colEdit = VytvoritSloupecEditovat();


        colId.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        colName.prefWidthProperty().bind(table.widthProperty().multiply(0.32));
        colAutor.prefWidthProperty().bind(table.widthProperty().multiply(0.22));
        colOblastNazev.prefWidthProperty().bind(table.widthProperty().multiply(0.22));
        colEdit.prefWidthProperty().bind(table.widthProperty().multiply(0.13));

        table.getColumns().addAll(colId, colName, colAutor, colOblastNazev, colEdit);


        return table;
    }

    /**
     * vytvoří sloupec s talčítky editovat
     * @return
     */
    private TableColumn<Kniha, Void> VytvoritSloupecEditovat() {
        TableColumn<Kniha, Void> colBtn = new TableColumn("Akce");

        Callback<TableColumn<Kniha, Void>, TableCell<Kniha, Void>> cellFactory = new Callback<TableColumn<Kniha, Void>, TableCell<Kniha, Void>>() {
            @Override
            public TableCell<Kniha, Void> call(final TableColumn<Kniha, Void> param) {
                final TableCell<Kniha, Void> cell = new TableCell<Kniha, Void>() {

                    private final Button btn = new Button("Editovat");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Kniha data = getTableView().getItems().get(getIndex());
                            gui.setSceen(Gui.ObrazovkaJmeno.OBR_KNIHA_EDITOVAT, data.getId());
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
