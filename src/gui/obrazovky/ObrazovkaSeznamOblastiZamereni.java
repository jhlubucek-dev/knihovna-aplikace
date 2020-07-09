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
import logika.OblastZamereni;

import java.util.ArrayList;

/**
 * class ObrazovkaSeznamOblastiZamereni
 */
public class ObrazovkaSeznamOblastiZamereni extends Obrazovka implements Zobraz {
    private NavigacniButton TlacitkoZpet;
    private Label nazevObrazovky;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaSeznamOblastiZamereni(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
        this.TlacitkoZpet = new NavigacniButton("Zpět", Gui.ObrazovkaJmeno.OBR_HLAVNI, gui);
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        nazevObrazovky.setText("Seznam oblastí zaměření");
    }

    /**
     * vytvoří scenu se seznamem oblastí zaměření
     *
     * @return scenu se seznamem oblastí zaměření
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
     * vytvoří tabulku se seznamem oblastí zaměření
     * @return tabulku se seznamem oblastí zaměření
     */
    private TableView<OblastZamereni> VytvoritTabulku() {
        final TableView<OblastZamereni> table = new TableView<>();

//        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(550);
        table.setPrefHeight(300);

        MysqlCon databaze = new MysqlCon();
        ObservableList<OblastZamereni> vyfiltrovanzList = FXCollections.observableArrayList(databaze.getOblasti());
        table.setItems(vyfiltrovanzList);

        TableColumn<OblastZamereni, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OblastZamereni, String> colName = new TableColumn<>("Název");
        colName.setCellValueFactory(new PropertyValueFactory<>("nazev"));

        TableColumn<OblastZamereni, Void> colEdit = VytvoritSloupecEditovat();

        colId.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        colName.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        colEdit.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        table.getColumns().addAll(colId, colName, colEdit);


        return table;
    }

    /**
     * vytvoří sloupec s talčítky editovat
     * @return
     */
    private TableColumn<OblastZamereni, Void> VytvoritSloupecEditovat() {
        TableColumn<OblastZamereni, Void> colBtn = new TableColumn("Akce");

        Callback<TableColumn<OblastZamereni, Void>, TableCell<OblastZamereni, Void>> cellFactory = new Callback<TableColumn<OblastZamereni, Void>, TableCell<OblastZamereni, Void>>() {
            @Override
            public TableCell<OblastZamereni, Void> call(final TableColumn<OblastZamereni, Void> param) {
                final TableCell<OblastZamereni, Void> cell = new TableCell<OblastZamereni, Void>() {

                    private final Button btn = new Button("Editovat");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            OblastZamereni data = getTableView().getItems().get(getIndex());
                            gui.setSceen(Gui.ObrazovkaJmeno.OBR_OBLAST_EDITOVAT, data.getId());
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
