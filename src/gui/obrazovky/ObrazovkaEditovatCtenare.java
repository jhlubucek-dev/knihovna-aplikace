package gui.obrazovky;

import gui.Gui;
import gui.pomocne.VarovnyDiIalog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import logika.Ctenar;
import logika.MysqlCon;

/**
 * class ObrazovkaEditovatCtenare
 */
public class ObrazovkaEditovatCtenare extends Obrazovka implements Zobraz, Edituj {
    private TextField id;
    private TextField jmeno;
    private TextField prijmeni;
    private TextField email;
    private Label idLabel;
    private Label jmenoLabel;
    private Label prijmeniLabel;
    private Label emailLabel;
    private Label nazevObrazovky;
    private Button tlacitkoUlozit;
    private Button tlacitkoZpet;
    private Button tlacitkoEditovat;
    private MysqlCon databaze;
    private Scene scenaZloz;
    private Scene scenaEdituj;
    private Ctenar ctenar;
    private boolean novyCtenar;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public ObrazovkaEditovatCtenare(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);
    }


    /**
     * vytvori scenu
     *
     * @return scenu teto obrazovky
     */
    private Scene VytvoritScenu(){

        databaze = new MysqlCon();
        id = new TextField();
        id.setEditable(false);
        id.setStyle("-fx-background-color: #b5b5b5");
        idLabel = new Label("ID");
        jmenoLabel = new Label("Jméno");
        prijmeniLabel = new Label("Příjmení");
        emailLabel = new Label("Email");
        nazevObrazovky = new Label();
        nazevObrazovky.setTextAlignment(TextAlignment.CENTER);
        nazevObrazovky.setFont(new Font("Arial", 18));
        nazevObrazovky.setStyle("-fx-font-weight: bold;");

        jmeno = new TextField();
        prijmeni = new TextField();
        email = new TextField();

        tlacitkoUlozit = new Button("Uložit");
        tlacitkoUlozit.setPrefWidth(100);
        tlacitkoUlozit.setStyle("-fx-background-color: #7a09ff;" +
                "-fx-border-color:rgba(0,0,0,0.64);" +
                "");
        tlacitkoUlozit.setTextFill(Color.WHITE);
        tlacitkoUlozit.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                uloz();
            }
        });

        tlacitkoZpet = new Button("Zpět");
        tlacitkoZpet.setPrefWidth(100);
        tlacitkoZpet.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                if (novyCtenar){
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_HLAVNI);
                }else {
                    gui.setSceen(Gui.ObrazovkaJmeno.OBR_CTENAR_SEZNAM);
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


        GridPane.setConstraints(nazevObrazovky,0,0,2 ,1);
        GridPane.setConstraints(idLabel,0,1,1 ,1);
        GridPane.setConstraints(id,0,2,2 ,1);
        GridPane.setConstraints(jmenoLabel,0,3,1 ,1);
        GridPane.setConstraints(jmeno,0,4, 2, 1);
        GridPane.setConstraints(prijmeniLabel,0,5,1 ,1);
        GridPane.setConstraints(prijmeni,0,6, 2, 1);
        GridPane.setConstraints(emailLabel,0,7,1 ,1);
        GridPane.setConstraints(email,0,8, 2, 1);
        GridPane.setConstraints(tlacitkoUlozit,1,9);
        GridPane.setConstraints(tlacitkoZpet,0,9);

        layout.getChildren().addAll(nazevObrazovky, idLabel, jmenoLabel, prijmeniLabel, emailLabel, id, jmeno, prijmeni, email, tlacitkoUlozit, tlacitkoZpet);
        return new Scene(layout, SCENE_WIDTH_DEFAULT, SCENE_HEIGHT_DEFAULT);
    }


    /**
     * vytvoří a vrátí scenu této obrazovky pro založení
     *
     * @return scenu této obrazovky pro založení
     */
    @Override
    public Scene getScene() {
        Scene scene = VytvoritScenu();
        novyCtenar = true;
        nazevObrazovky.setText("Založit čtenáře");

//        id.setText(String.valueOf(databaze.getCtenarNexId()));
        return scene;
    }

    /**
     * vytvoří a vrátí scenu této obrazovky pro editaci
     *
     * @return scenu této obrazovky pro editaci
     */
    @Override
    public Scene getScene(int idCtenare) {
        Scene scena = VytvoritScenu();
        novyCtenar = false;
        nazevObrazovky.setText("Editovat čtenáře");

        ctenar = databaze.getCtenar(idCtenare);
        this.id.setText(ctenar.getIdString());
        jmeno.setText(ctenar.getJmeno());
        prijmeni.setText(ctenar.getPrijmeni());
        email.setText(ctenar.getEmail());
        return scena;
    }


    /**
     * kontroluje udaje a vyhodi varovne okno, pokud udaje chyby
     *
     * @return spravnost udaju
     */
    private boolean kontrolaUdaju(){
        if (jmeno.getText().length() == 0 || prijmeni.getText().length() == 0 || email.getText().length() == 0 ){
            new VarovnyDiIalog("Prosím vyplňte všechny údaje").showAndWait();
            return false;
        }else if(!email.getText().toLowerCase().matches("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,7}")){
            new VarovnyDiIalog("Špatně vyplněný email").showAndWait();
            return false;
        }
        return true;
    }

    /**
     * vytvoří nebo edituje položku v databázi
     */
    private void uloz(){
        if (kontrolaUdaju()) {
            if(novyCtenar) {
                ctenar = new Ctenar(jmeno.getText(), prijmeni.getText(), email.getText());
                databaze.addCtenar(ctenar);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_CTENAR_SEZNAM);
            }else{
                ctenar.setJmeno(jmeno.getText());
                ctenar.setPrijmeni(prijmeni.getText());
                ctenar.setEmail(email.getText());
                databaze.editCtenar(ctenar);
                gui.setSceen(Gui.ObrazovkaJmeno.OBR_CTENAR_SEZNAM);
            }
        }
    }
}
