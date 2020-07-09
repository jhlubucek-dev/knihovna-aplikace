package gui.obrazovky;

import gui.Gui;
import javafx.scene.Scene;

import java.util.Objects;

public abstract class Obrazovka {
    protected Gui gui;
    Gui.ObrazovkaJmeno jmeno;
    protected final int SCENE_WIDTH_DEFAULT = 560;
    protected final int SCENE_WIDTH_LONG = 800;
    protected final int SCENE_HEIGHT_DEFAULT = 380;

    /**
     * konstruktor
     *
     * @param gui Gui
     * @param name Gui.ObrazovkaJmeno
     */
    public Obrazovka(Gui gui, Gui.ObrazovkaJmeno name){
        this.gui = gui;
        this.jmeno = name;
    }

    /**
     *
     * @return jmeno obrazovky
     */
    public Gui.ObrazovkaJmeno getJmeno() {
        return jmeno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Obrazovka)) return false;
        Obrazovka obrazovka = (Obrazovka) o;
        return jmeno == obrazovka.jmeno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmeno);
    }
}
