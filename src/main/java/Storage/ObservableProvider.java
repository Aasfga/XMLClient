package Storage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ObservableProvider implements Provider
{

    Provider provider;
    ObservableList<String> list = FXCollections.observableList(new ArrayList<>());

    public ObservableProvider(Provider provider)
    {
        this.provider = provider;
    }

    @Override
    public Collection<String> getFileNames() throws DownloadException
    {
        return provider.getFileNames();
    }

    @Override
    public void uploadFile(String filename, byte[] file) throws UploadException
    {
        provider.uploadFile(filename, file);
        list.add(filename);
    }

    @Override
    public byte[] downloadFile(String filename) throws DownloadException
    {
        return provider.downloadFile(filename);
    }

    public ObservableList<String> getList()
    {
        return list;
    }

    public void refresh() throws Exception
    {
        list.setAll(getFileNames());
    }
}
