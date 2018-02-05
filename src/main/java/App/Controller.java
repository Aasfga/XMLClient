package App;

import Storage.ObservableProvider;
import Storage.Provider;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller
{
    private static Logger logger = Logger.getLogger("Controller");

    static
    {
        Level level = Level.ALL;
        logger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(level);
        logger.setLevel(level);
        logger.addHandler(handler);
    }

    private ObservableProvider clientProvider;
    private ObservableProvider serverProvider;
    private FileView clientView;
    private FileView serverView;
    private View view;

    public Controller(Provider clientProvider, Provider serverProvider)
    {
        this.clientProvider = new ObservableProvider(clientProvider);
        this.serverProvider = new ObservableProvider(serverProvider);
    }

    private static EventHandler<MouseEvent> refreshGenerator(ObservableProvider provider, FileView view)
    {
        return event -> {
            try
            {
                provider.refresh();
                view.hideError();
            } catch(Exception e)
            {
                logger.warning("Not able to refresh. Error msg: " + e.getMessage());
                view.showError();
            }
        };
    }

    private static EventHandler<MouseEvent> buttonEventGenerator(ObservableProvider sender, FileView senderView, ObservableProvider receiver, FileView receiverView)
    {
        return event -> {
            try
            {
                String filename = senderView.getSelectedFile();
                byte file[] = sender.downloadFile(filename);
                senderView.hideError();
                receiver.uploadFile(filename, file);
                receiverView.hideError();
            }
            catch(Provider.DownloadException e)
            {
                logger.warning("Not able to download file. Error msg: " + e.getMessage());
                senderView.showError();
            } catch(Provider.UploadException e)
            {
                logger.warning("Not able to save file. Error msg: " + e.getMessage());
                receiverView.showError();
            }
            catch(NullPointerException e)
            {
                logger.info("Not able to download file with \"null\" name");
            }
        };
    }

    private void setHandlers()
    {
        clientView.setRefreshButtonHandler(refreshGenerator(clientProvider, clientView));
        serverView.setRefreshButtonHandler(refreshGenerator(serverProvider, serverView));
        view.getBtn.setOnMouseClicked(buttonEventGenerator(serverProvider, serverView, clientProvider, clientView));
        view.postBtn.setOnMouseClicked(buttonEventGenerator(clientProvider, clientView, serverProvider, serverView));
    }

    private void setVariables(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root, 600, 400));
        view = loader.getController();
        clientView = new FileView(view.refreshClient, view.clientFiles, view.clientError);
        clientView.setObservableList(clientProvider.getList());
        serverView = new FileView(view.refreshServer, view.serverFiles, view.serverError);
        serverView.setObservableList(serverProvider.getList());
    }

    public void start(Stage stage) throws Exception
    {
        setVariables(stage);
        setHandlers();
        stage.show();
    }


}
