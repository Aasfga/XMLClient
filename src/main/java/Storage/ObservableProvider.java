package Storage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class ObservableProvider implements Provider
{

    private Provider provider;
    private ObservableList<FileInfo> list = FXCollections.observableList(new ArrayList<>());

    public ObservableProvider(Provider provider)
    {
        this.provider = provider;
    }

    @Override
    public Collection<FileInfo> getFileNames() throws IOException
    {
        return provider.getFileNames();
    }

    @Override
    public boolean uploadFile(FileInfo info, byte[] file) throws IOException, JAXBException
    {
        boolean res = provider.uploadFile(info, file);
        if(res)
            list.add(info);
        return res;
    }

    @Override
    public byte[] downloadFile(FileInfo info) throws IOException
    {
        return provider.downloadFile(info);
    }

    public ObservableList<FileInfo> getList()
    {
        return list.sorted();
    }

    public void refresh() throws Exception
    {
        list.setAll(getFileNames());
    }
}
