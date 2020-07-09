package gui;

import gui.obrazovky.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.HashMap;

/**
 * class s grafickým prostředím
 */
public class Gui {
    Stage primaryStage;
    HashMap<ObrazovkaJmeno, Obrazovka> obrazovky;

    /**
     * enum s nazvy obrazovek
     */
    public enum ObrazovkaJmeno {
        OBR_HLAVNI,
        OBR_CTENAR_SEZNAM,
        OBR_KNIHA_SEZNAM,
        OBR_OBLAST_SEZNAM,
        OBR_VYPUJCKA_SEZNAM,
        OBR_AUTOR_SEZNAM,
        OBR_OBLAST_EDITOVAT,
        OBR_OBLAST_ZALOZIT,
        OBR_KNIHA_ZALOZIT,
        OBR_KNIHA_EDITOVAT,
        OBR_CTENAR_ZALOZIT,
        OBR_CTENAR_EDITOVAT,
        OBR_VYPUJCKA_ZALOZIT,
        OBR_VYPUJCKA_EDITOVAT,
        OBR_AUTOR_ZALOZIT,
        OBR_AUTOR_EDITOVAT
    }


    /**
     * konstruktor
     * vytvoří obrazovky
     */
    public Gui() {
        obrazovky = new HashMap<>();
        pridatObrazovku(new HlavniObrazovka(this, ObrazovkaJmeno.OBR_HLAVNI));
        pridatObrazovku(new ObrazovkaEditovatCtenare(this, ObrazovkaJmeno.OBR_CTENAR_ZALOZIT));
        pridatObrazovku(new ObrazovkaSeznamKnihy(this, ObrazovkaJmeno.OBR_KNIHA_SEZNAM));
        pridatObrazovku(new ObrazovkaSeznamOblastiZamereni(this, ObrazovkaJmeno.OBR_OBLAST_SEZNAM));
        pridatObrazovku(new ObrazovkaSeznamVypujcky(this, ObrazovkaJmeno.OBR_VYPUJCKA_SEZNAM));
        pridatObrazovku(new ObrazovkaSeznamAutor(this, ObrazovkaJmeno.OBR_AUTOR_SEZNAM));
        pridatObrazovku(new ObrazovkaEditovatOblastZamereni(this, ObrazovkaJmeno.OBR_OBLAST_EDITOVAT));
        pridatObrazovku(new ObrazovkaEditovatOblastZamereni(this, ObrazovkaJmeno.OBR_OBLAST_ZALOZIT));
        pridatObrazovku(new ObrazovkaEditovatKnihu(this, ObrazovkaJmeno.OBR_KNIHA_ZALOZIT));
        pridatObrazovku(new ObrazovkaEditovatKnihu(this, ObrazovkaJmeno.OBR_KNIHA_EDITOVAT));
        pridatObrazovku(new ObrazovkaSeznamCtenar(this, ObrazovkaJmeno.OBR_CTENAR_SEZNAM));
        pridatObrazovku(new ObrazovkaEditovatCtenare(this, ObrazovkaJmeno.OBR_CTENAR_EDITOVAT));
        pridatObrazovku(new ObrazovkaEditovatVypujcka(this, ObrazovkaJmeno.OBR_VYPUJCKA_ZALOZIT));
        pridatObrazovku(new ObrazovkaEditovatVypujcka(this, ObrazovkaJmeno.OBR_VYPUJCKA_EDITOVAT));
        pridatObrazovku(new ObrazovkaEditovatAutora(this, ObrazovkaJmeno.OBR_AUTOR_ZALOZIT));
        pridatObrazovku(new ObrazovkaEditovatAutora(this, ObrazovkaJmeno.OBR_AUTOR_EDITOVAT));
    }

    /**
     * přidá obrazovku do hashMap
     * @param obrazovka
     */
    private void pridatObrazovku(Obrazovka obrazovka){
        obrazovky.put(obrazovka.getJmeno(), obrazovka);
    }

    /**
     * zobrazí okno dané obrazovky
     * @param obrazovkaJmeno
     */
    public void setSceen(ObrazovkaJmeno obrazovkaJmeno){
        Obrazovka obrazovka = obrazovky.get(obrazovkaJmeno);
        if (obrazovka instanceof Zobraz){
            primaryStage.setScene(((Zobraz) obrazovka).getScene());
        }
    }

    /**
     * zobrazí okno dané obrazovky pro editaci
     * @param obrazovkaJmeno
     * @param id id editovaného obektu
     */
    public void setSceen(ObrazovkaJmeno obrazovkaJmeno, int id){
        Obrazovka obrazovka = obrazovky.get(obrazovkaJmeno);
        if (obrazovka instanceof Edituj){
            primaryStage.setScene(((Edituj) obrazovka).getScene(id));
        }
    }

    /**
     * nastavý primaryStage a zobrazí hlavní obrazovku
     * @param primaryStage
     */
    public void run(Stage primaryStage){
        this.primaryStage = primaryStage;

        setSceen(ObrazovkaJmeno.OBR_HLAVNI);
        primaryStage.show();
    }
}
