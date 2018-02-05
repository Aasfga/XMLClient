import App.Controller;
import Storage.ExampleProvider;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        new Controller(new ExampleProvider(), new ExampleProvider()).start(primaryStage);
    }

    public static void main(String[] args)
    {
        launch();
    }
}
