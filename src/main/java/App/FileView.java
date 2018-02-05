package App;


import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;


public class FileView
{
    private Button refreshBtn;
    private ListView<String> list;
    private Text errorText;

    public FileView(Button refreshBtn, ListView<String> list, Text errorText)
    {
        this.refreshBtn = refreshBtn;
        this.list = list;
        this.errorText = errorText;
    }

    public String getSelectedFile()
    {
        return list.getSelectionModel().getSelectedItem();
    }

    public void setRefreshButtonHandler(EventHandler<? super MouseEvent> handler)
    {
        refreshBtn.setOnMouseClicked(handler);
    }

    public void setObservableList(ObservableList<String> list)
    {
        this.list.setItems(list);
    }

    public void showError()
    {
        errorText.setVisible(true);
    }

    public void hideError()
    {
        errorText.setVisible(false);
    }
}
