package App;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

public class View
{
    public Button refreshClient;
    public ListView<String> clientFiles;
    public Button refreshServer;
    public ListView<String> serverFiles;
    public Button postBtn;
    public Button getBtn;
    public Text serverError;
    public Text clientError;
}
