import gui.Gui;
import gui.obrazovky.HlavniObrazovka;
import javafx.application.Application;
import javafx.stage.Stage;

import java.time.LocalDate;

/**
 *
 */
public class Main extends Application{
    HlavniObrazovka hlavniObrazovka;
    Gui gui;

    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * spustí aplikaci
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        gui = new Gui();
        gui.run(primaryStage);

    }
}
