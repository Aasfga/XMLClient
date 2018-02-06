package Storage;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.Collection;

public interface Provider
{
    Collection<FileInfo> getFileNames() throws IOException;

    boolean uploadFile(FileInfo info, byte[] file) throws IOException, JAXBException;

    byte[] downloadFile(FileInfo info) throws IOException;
}
