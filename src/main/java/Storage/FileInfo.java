package Storage;

import java.net.ConnectException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

public class FileInfo implements Comparable<FileInfo>
{
    private String filename;
    private GregorianCalendar date;

    public FileInfo(String filename, GregorianCalendar date)
    {
        this.filename = filename;
        this.date = date;
    }


    public String getFilename()
    {
        return filename;
    }

    public GregorianCalendar getDate()
    {
        return date;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return Objects.equals(filename, fileInfo.filename) &&
                Objects.equals(date, fileInfo.date);
    }

    @Override
    public int hashCode()
    {

        return Objects.hash(filename, date);
    }

    @Override
    public int compareTo(FileInfo o)
    {
        return filename.equals(o.filename) ? o.date.compareTo(date) : filename.compareTo(o.filename);
    }
}
