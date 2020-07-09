package gui.obrazovky;

import gui.Gui;
//import gui.Button;
import gui.pomocne.NavigacniButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * class HlavniObrazovka
 */
public class HlavniObrazovka extends Obrazovka implements Zobraz {
    Button seznamVypujcek;
    Button seznamCtenaru;
    Button seznamOblastiZamereni;
    Button seznamKnih;
    Button seznamAutoru;
    Button zalozitVypujcku;
    Button zalozitCtenare;
    Button zalozitOblastZamereni;
    Button zalozitKnihu;
    Button zalozitAutora;
    ArrayList<Button> buttons;
    Scene scena;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public HlavniObrazovka(Gui gui, Gui.ObrazovkaJmeno name) {
        super(gui, name);

        seznamVypujcek = new NavigacniButton("Zobrazit seznam výpůjček", Gui.ObrazovkaJmeno.OBR_VYPUJCKA_SEZNAM, gui);
        seznamCtenaru = new NavigacniButton("Zobrazit seznam čtenářů", Gui.ObrazovkaJmeno.OBR_CTENAR_SEZNAM, gui);
        seznamOblastiZamereni = new NavigacniButton("Zobrazit seznam oblasti zaměření", Gui.ObrazovkaJmeno.OBR_OBLAST_SEZNAM, gui);
        seznamKnih = new NavigacniButton("Zobrazit seznam knih", Gui.ObrazovkaJmeno.OBR_KNIHA_SEZNAM, gui);
        seznamAutoru = new NavigacniButton("Zobrazit seznam autoru", Gui.ObrazovkaJmeno.OBR_AUTOR_SEZNAM, gui);
        zalozitVypujcku = new NavigacniButton("Založit výpůjčku", Gui.ObrazovkaJmeno.OBR_VYPUJCKA_ZALOZIT, gui);
        zalozitCtenare = new NavigacniButton("Založit čtenáře", Gui.ObrazovkaJmeno.OBR_CTENAR_ZALOZIT, gui);
        zalozitOblastZamereni = new NavigacniButton("Založit oblast zaměření", Gui.ObrazovkaJmeno.OBR_OBLAST_ZALOZIT, gui);
        zalozitKnihu = new NavigacniButton("Založit knihu", Gui.ObrazovkaJmeno.OBR_KNIHA_ZALOZIT, gui);
        zalozitAutora = new NavigacniButton("Založit autora", Gui.ObrazovkaJmeno.OBR_AUTOR_ZALOZIT, gui);


        buttons = new ArrayList<>();
        buttons.add(seznamVypujcek);
        buttons.add(seznamCtenaru);
        buttons.add(seznamOblastiZamereni);
        buttons.add(seznamKnih);
        buttons.add(seznamAutoru);
        buttons.add(zalozitVypujcku);
        buttons.add(zalozitCtenare);
        buttons.add(zalozitOblastZamereni);
        buttons.add(zalozitKnihu);
        buttons.add(zalozitAutora);

        for (Button button : buttons) {
            button.setPrefWidth(200);
        }

        GridPane layout = new GridPane();
        layout.setPadding(new Insets(10,10,10,10));
        layout.setVgap(8);
        layout.setHgap(10);

        GridPane.setConstraints(seznamVypujcek,0,0);
        GridPane.setConstraints(seznamCtenaru,0,1);
        GridPane.setConstraints(seznamOblastiZamereni,0,2);
        GridPane.setConstraints(seznamKnih,0,3);
        GridPane.setConstraints(seznamAutoru,0,4);
        GridPane.setConstraints(zalozitVypujcku,1,0);
        GridPane.setConstraints(zalozitCtenare,1,1);
        GridPane.setConstraints(zalozitOblastZamereni,1,2);
        GridPane.setConstraints(zalozitKnihu,1,3);
        GridPane.setConstraints(zalozitAutora,1,4);

        layout.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().addAll(seznamVypujcek, seznamCtenaru, seznamOblastiZamereni, seznamKnih, seznamAutoru, zalozitVypujcku, zalozitCtenare, zalozitOblastZamereni, zalozitKnihu, zalozitAutora);

        scena = new Scene(layout, SCENE_WIDTH_DEFAULT, SCENE_HEIGHT_DEFAULT);
    }

    /**
     *
     * @return scenu této obrazovky
     */
    @Override
    public Scene getScene() {
        return scena;
    }
}
