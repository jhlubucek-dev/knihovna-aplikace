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
import logika.Ctenar;
import logika.MysqlCon;

/**
 * class ObrazovkaSeznamCtenar
 */
public class ObrazovkaSeznamCtenar extends Obrazovka implements Zobraz{
    private NavigacniButton TlacitkoZpet;
    private Label nazevObrazovky;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaSeznamCtenar(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
        this.TlacitkoZpet = new NavigacniButton("Zpět", Gui.ObrazovkaJmeno.OBR_HLAVNI, gui);
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");
        nazevObrazovky.setText("Seznam čtenářů");
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
    private TableView<Ctenar> VytvoritTabulku() {
        final TableView<Ctenar> table = new TableView<>();

//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setMaxWidth(550);
        table.setPrefHeight(300);

        MysqlCon databaze = new MysqlCon();
        ObservableList<Ctenar> vyfiltrovanzList = FXCollections.observableArrayList(databaze.getCtenari());
        table.setItems(vyfiltrovanzList);

        TableColumn<Ctenar, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Ctenar, String> colName = new TableColumn<>("Jméno");
        colName.setCellValueFactory(new PropertyValueFactory<>("jmeno"));

        TableColumn<Ctenar, String> colPrijmeni = new TableColumn<>("Příjmení");
        colPrijmeni.setCellValueFactory(new PropertyValueFactory<>("prijmeni"));

        TableColumn<Ctenar, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Ctenar, Void> colEdit = VytvoritSloupecEditovat();

        colId.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        colName.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        colPrijmeni.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        colEmail.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        colEdit.prefWidthProperty().bind(table.widthProperty().multiply(0.14));

        table.getColumns().addAll(colId, colName, colPrijmeni, colEmail, colEdit);


        return table;
    }

    /**
     * vytvoří sloupec s talčítky editovat
     * @return
     */
    private TableColumn<Ctenar, Void> VytvoritSloupecEditovat() {
        TableColumn<Ctenar, Void> colBtn = new TableColumn("akce");

        Callback<TableColumn<Ctenar, Void>, TableCell<Ctenar, Void>> cellFactory = new Callback<TableColumn<Ctenar, Void>, TableCell<Ctenar, Void>>() {
            @Override
            public TableCell<Ctenar, Void> call(final TableColumn<Ctenar, Void> param) {
                final TableCell<Ctenar, Void> cell = new TableCell<Ctenar, Void>() {

                    private final Button btn = new Button("Editovat");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Ctenar data = getTableView().getItems().get(getIndex());
                            gui.setSceen(Gui.ObrazovkaJmeno.OBR_CTENAR_EDITOVAT, data.getId());
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
