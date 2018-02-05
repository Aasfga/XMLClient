package Storage;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Collection;
import java.util.List;

public interface Provider
{
    Collection<String> getFileNames() throws DownloadException;

    void uploadFile(String filename, byte file[]) throws UploadException;

    byte[] downloadFile(String filename) throws DownloadException;


    class DownloadException extends IOException
    {
        public DownloadException()
        {
        }

        public DownloadException(String message)
        {
            super(message);
        }

        public DownloadException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public DownloadException(Throwable cause)
        {
            super(cause);
        }
    }

    class UploadException extends IOException
    {
        public UploadException()
        {
        }

        public UploadException(String message)
        {
            super(message);
        }

        public UploadException(String message, Throwable cause)
        {
            super(message, cause);
        }

        public UploadException(Throwable cause)
        {
            super(cause);
        }
    }
}
