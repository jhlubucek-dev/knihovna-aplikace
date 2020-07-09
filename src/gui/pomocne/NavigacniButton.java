package gui.pomocne;

import gui.Gui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * button který kliknuti zobrazí danou obrazovku
 */
public class NavigacniButton extends Button {
    Gui.ObrazovkaJmeno obrazovka;
    Gui gui;

    /**
     * konstrktor
     * @param string text
     * @param obrazovka
     * @param gui
     */
    public NavigacniButton(String string, Gui.ObrazovkaJmeno obrazovka, Gui gui){
        super(string);
        this.obrazovka = obrazovka;
        this.gui = gui;

        this.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                gui.setSceen(obrazovka);
            }
        });
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NavigacniButton)) return false;
        NavigacniButton that = (NavigacniButton) o;
        return obrazovka.equals(that.obrazovka) &&
                this.getText().equals(that.getText());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
