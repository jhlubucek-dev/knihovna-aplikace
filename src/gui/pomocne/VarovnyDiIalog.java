package gui.pomocne;

import javafx.scene.control.Alert;

public class VarovnyDiIalog extends Alert {
    public VarovnyDiIalog(String header, String text) {
        super(Alert.AlertType.WARNING);
        this.setTitle("Varovný dialog");
        this.setHeaderText(header);
        this.setContentText(text);
    }
    public VarovnyDiIalog(String header) {
        super(Alert.AlertType.WARNING);
        this.setTitle("Varovný dialog");
        this.setHeaderText(header);
        this.setContentText(null);
    }
}
