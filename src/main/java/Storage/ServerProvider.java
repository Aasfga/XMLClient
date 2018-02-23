package Storage;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class ServerProvider implements Provider
{
    final String SERVER = "localhost";
    final String SERVER_PORT = "7070";

    private static GregorianCalendar getDate(String stringDate)
    {
        GregorianCalendar date = new GregorianCalendar();
        String parts[] = stringDate.split(":");
        Integer values[] = Arrays.stream(parts).map(Integer::valueOf).toArray(Integer[]::new);
        date.set(values[0], values[1], values[2], values[3], values[4]);
        date.set(Calendar.SECOND, Integer.valueOf(parts[5]));
        return date;
    }

    private byte[] extract(InputStream inputStream) throws IOException
    {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        while((read = inputStream.read(buffer, 0, buffer.length)) != -1)
            byteStream.write(buffer, 0, read);
        byteStream.flush();
        return byteStream.toByteArray();
    }

    private static String createFileInfoURI(FileInfo info)
    {
        return info.getFilename() + ";" + convertToString(info.getDate());
    }

    private static String convertToString(GregorianCalendar date)
    {
        return String.format("%d:%d:%d:%d:%d:%d", date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), date.get(Calendar.SECOND));
    }


    @Override
    public Collection<FileInfo> getFileNames() throws IOException
    {
        try
        {
            InputStream stream = new URL(String.format("http://%s:%s/list", SERVER, SERVER_PORT)).openStream();
            Object o = new ObjectInputStream(stream).readObject();
            return (Collection<FileInfo>) o;
        }
        catch(Exception e)
        {
            throw new IOException();
        }
    }

    @Override
    public void uploadFile(String filename, byte[] file) throws IOException, JAXBException
    {
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(String.format("http://%s:%s/put/%s", SERVER, SERVER_PORT, filename)).openConnection();
        httpConnection.setRequestMethod("POST");
        try(OutputStream stream = httpConnection.getOutputStream())
        {
            stream.write(file);
        }
    }

    @Override
    public byte[] downloadFile(FileInfo info) throws IOException
    {
        try
        {
            InputStream stream = new URL(String.format("http://%s:%s/get/%s;%s",
                                                       SERVER, SERVER_PORT,
                                                       info.getFilename(),
                                                       convertToString(info.getDate()))).openStream();
            return extract(stream);
        }
        catch(Exception e)
        {
            throw new IOException();
        }
    }
}
