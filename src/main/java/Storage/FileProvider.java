package Storage;

import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileProvider implements Provider
{

    @Override
    public Collection<FileInfo> getFileNames() throws IOException
    {
        File directory = new File("files");
        File files[] = directory.listFiles();
        Set<FileInfo> names = new TreeSet<>();

        if(files == null)
            throw new IOException("Not able to list files. FATAL ERROR");


        for(File f : files)
        {
            GregorianCalendar date = new GregorianCalendar();
            date.setTimeInMillis(f.lastModified());
            names.add(new FileInfo(f.getName(), date));
        }

        return names;
    }

    @Override
    public boolean uploadFile(FileInfo info, byte[] file) throws IOException
    {
            boolean result = !Files.exists(Paths.get("files/" + info.getFilename()));
            Files.write(Paths.get("files/" + info.getFilename()), file);
            return result;
    }

    @Override
    public byte[] downloadFile(FileInfo info) throws IOException
    {
            return Files.readAllBytes(Paths.get("files/" + info.getFilename()));
    }
}
