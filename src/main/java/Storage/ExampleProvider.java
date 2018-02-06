package Storage;

import java.io.IOException;
import java.util.*;

public class ExampleProvider implements Provider
{
    private Map<FileInfo, byte[]> files = new TreeMap<>();
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

    public ExampleProvider()
    {
        for(int i = 1 + random.nextInt(10); i >= 0; i--)
            files.put(new FileInfo(generateWord(1 + random.nextInt(10)) + ".xml",
                    new GregorianCalendar()), generateWord(20 + random.nextInt(20)).getBytes());
    }

    @Override
    public Collection<FileInfo> getFileNames()
    {
        return files.keySet();
    }

    @Override
    public boolean uploadFile(FileInfo info, byte[] file) throws IOException
    {
        if(random.nextInt(10) < 2)
            throw new IOException("Connection error. Try again");
        if(info == null)
            throw new NullPointerException();

        if(!files.containsKey(info))
        {
            files.put(info, file);
            return true;
        }
        else
            return false;
    }

    @Override
    public byte[] downloadFile(FileInfo info) throws IOException
    {
        if(random.nextInt(10) < 2)
            throw new IOException("Connection error. Try again");
        if(info == null)
            throw new NullPointerException();
        return files.getOrDefault(info, null);
    }
}
