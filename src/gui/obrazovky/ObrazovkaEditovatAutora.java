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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logika.MysqlCon;
import logika.Autor;

/**
 * class ObrazovkaEditovatAutor
 */
public class ObrazovkaEditovatAutora extends Obrazovka implements Zobraz, Edituj {
    private final TableView<Autor> table = new TableView<>();
    private final ObservableList<Autor> tvObservableList = FXCollections.observableArrayList();

    private TextField id;
    private TextField jmeno;
    private TextField kod;
    private Label labelId;
    private Label labelJmeno;
    private Label labelNazevObrazovky;
    private Label labelKod;
    private Button tlacitkoUlozit;
    private Button tlacitkoZpet;
    private MysqlCon databaze;
    private boolean novyAutor;
    private Autor autor;


    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaEditovatAutora(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
    }

    /**
     * vytvoří a vrátí scenu této obrazovky
     * @return scenu této obrazovky
     */
    private Scene VytvoritScenu(){
        id = new TextField();
        kod = new TextField();
        id.setEditable(false);
        id.setStyle("-fx-background-color: #b5b5b5");
        jmeno = new TextField();
        labelId = new Label("ID");
        labelJmeno = new Label("Jméno");
        labelKod = new Label("Kód");
        labelNazevObrazovky = new Label();
        labelNazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        labelNazevObrazovky.setFont(new Font("Arial", 18));
        labelNazevObrazovky.setStyle("-fx-font-weight: bold;");

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
                if (novyAutor){
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_HLAVNI);
                }else {
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_OBLAST_SEZNAM);
                }
            }
        });

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(20, 0, 0, 0));
        layout.setVgap(8);
        layout.setHgap(10);
        layout.getColumnConstraints().add(new ColumnConstraints(100));
        layout.getColumnConstraints().add(new ColumnConstraints(100));
//        layout.
        layout.setAlignment(Pos.TOP_CENTER);

        GridPane.setConstraints(labelNazevObrazovky,0,0,2 ,1);
        GridPane.setConstraints(labelId,0,1,1 ,1);
        GridPane.setConstraints(id,0,2,2 ,1);
        GridPane.setConstraints(labelKod,0,3,1 ,1);
        GridPane.setConstraints(kod,0,4,2 ,1);
        GridPane.setConstraints(labelJmeno,0,5,1 ,1);
        GridPane.setConstraints(jmeno,0,6, 2, 1);
        GridPane.setConstraints(tlacitkoUlozit,1,7);
        GridPane.setConstraints(tlacitkoZpet,0,7);

        layout.getChildren().addAll(labelNazevObrazovky, labelId, id , jmeno, labelJmeno, tlacitkoUlozit, tlacitkoZpet, labelKod, kod);
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
        novyAutor = true;
        labelNazevObrazovky.setText("Založit autora");

        return scena;
    }

    /**
     * upraví  a vrátí scenu této obrazovky pro editaci
     *
     * @param idAutori
     * @return scenu této obrazovky pro editaci
     */
    @Override
    public Scene getScene(int idAutori) {
        Scene scena = VytvoritScenu();
        novyAutor = false;
        labelNazevObrazovky.setText("Editovat autora");

        MysqlCon databaze = new MysqlCon();
        autor = databaze.getAutor(idAutori);
        id.setText(String.valueOf(autor.getId()));
        jmeno.setText(autor.getJmeno());
        kod.setText(autor.getKod());
        return scena;
    }

    /**
     * kontroluje udaje a vyhodi varovne okno, pokud udaje chyby
     *
     * @return spravnost udaju
     */
    private boolean kontrolaUdaju(){
        if (jmeno.getText().length() == 0){
            new VarovnyDiIalog("Prosím vyplňte jmeno").showAndWait();
            return false;
        }

        return true;
    }

    /**
     * vytvoří nebo edituje položku v databázi
     */
    private void uloz(){
        if (kontrolaUdaju()) {
            if(novyAutor) {
                autor = new Autor(kod.getText(), jmeno.getText());
                databaze.addAutor(autor);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_OBLAST_SEZNAM);
            }else{
                MysqlCon databaze = new MysqlCon();
                autor.setJmeno(jmeno.getText());
                autor.setKod(kod.getText());
                databaze.editAutor(autor);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_AUTOR_SEZNAM);
            }
        }
    }
}
