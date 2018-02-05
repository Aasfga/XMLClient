package Storage;

import java.util.Collection;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class ExampleProvider implements Provider
{
    Set<String> files = new TreeSet<>();
    static private Random random = new Random();

    private static String generateWord(int length)
    {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < length; i++)
        {
            builder.append((char) ('a' + random.nextInt(26)));
        }
        return builder.toString();
    }

    {
        for(int i = 1 + random.nextInt(10); i >= 0; i--)
            files.add(generateWord(1 + random.nextInt(10)) + ".xml");
    }
    @Override
    public Collection<String> getFileNames()
    {
        return files;
    }

    @Override
    public void uploadFile(String filename, byte[] file) throws UploadException
    {
        if(random.nextInt(10) < 2)
            throw new UploadException();
        if(filename == null)
            throw new NullPointerException();
        if(!files.contains(filename))
            files.add(filename);
    }

    @Override
    public byte[] downloadFile(String filename) throws DownloadException
    {
        if(random.nextInt(10) < 2)
            throw new DownloadException();
        if(filename == null)
            throw new NullPointerException();
        if(files.contains(filename))
            return new byte[]{10, 10, 10};
        else
            throw new DownloadException();
    }
}
