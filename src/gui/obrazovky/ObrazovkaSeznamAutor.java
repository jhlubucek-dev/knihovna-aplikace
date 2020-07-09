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
import logika.Autor;
import logika.MysqlCon;

/**
 * class ObrazovkaSeznamAutor
 */
public class ObrazovkaSeznamAutor extends Obrazovka implements Zobraz{
    private NavigacniButton TlacitkoZpet;
    private Label nazevObrazovky;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaSeznamAutor(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
        this.TlacitkoZpet = new NavigacniButton("Zpět", Gui.ObrazovkaJmeno.OBR_HLAVNI, gui);
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        nazevObrazovky.setText("Seznam autorů");
    }

    /**
     * vytvoří scenu se seznamem čtenářů
     *
     * @return scenu se seznamem čtenářů
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
     * vytvoří tabulku se seznamem četnářů
     * @return tabulku se seznamem četnářů
     */
    private TableView<Autor> VytvoritTabulku() {
        final TableView<Autor> table = new TableView<>();

//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(550);
        table.setPrefHeight(300);

        MysqlCon databaze = new MysqlCon();
        ObservableList<Autor> vyfiltrovanzList = FXCollections.observableArrayList(databaze.getAutori());
        table.setItems(vyfiltrovanzList);

        TableColumn<Autor, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Autor, String> colName = new TableColumn<>("Jméno");
        colName.setCellValueFactory(new PropertyValueFactory<>("jmeno"));

        TableColumn<Autor, String> colPrijmeni = new TableColumn<>("Kód");
        colPrijmeni.setCellValueFactory(new PropertyValueFactory<>("kod"));



        TableColumn<Autor, Void> colEdit = VytvoritSloupecEditovat();

        colId.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        colName.prefWidthProperty().bind(table.widthProperty().multiply(0.35));
        colPrijmeni.prefWidthProperty().bind(table.widthProperty().multiply(0.35));
        colEdit.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        table.getColumns().addAll(colId, colName, colPrijmeni, colEdit);


        return table;
    }

    /**
     * vytvoří sloupec s talčítky editovat
     * @return
     */
    private TableColumn<Autor, Void> VytvoritSloupecEditovat() {
        TableColumn<Autor, Void> colBtn = new TableColumn("akce");

        Callback<TableColumn<Autor, Void>, TableCell<Autor, Void>> cellFactory = new Callback<TableColumn<Autor, Void>, TableCell<Autor, Void>>() {
            @Override
            public TableCell<Autor, Void> call(final TableColumn<Autor, Void> param) {
                final TableCell<Autor, Void> cell = new TableCell<Autor, Void>() {

                    private final Button btn = new Button("Editovat");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Autor data = getTableView().getItems().get(getIndex());
                            gui.setSceen(Gui.ObrazovkaJmeno.OBR_AUTOR_EDITOVAT, data.getId());
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
