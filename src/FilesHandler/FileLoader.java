package FilesHandler;

import java.io.*;
import java.util.Arrays;

public class FileLoader {

    private final int BUFFER_SIZE = 50 * 1024 * 1024; // 50MB

    private FileInputStream file;
    private BufferedInputStream buff;

    public FileLoader(String path) throws IOException {
        if (path == null)
            throw new IllegalStateException();

        this.file = new FileInputStream(path);
        this.buff = new BufferedInputStream(file, BUFFER_SIZE);

    }

    public int getAvailableBytes() throws IOException {
        return buff.available();
    }

    public byte[] loadNBytesBinFile(int numOfBytes) throws IOException {

        byte[] b = new byte[numOfBytes];
        int available = buff.available();
        if (available > 0) {
            buff.read(b);
            return Arrays.copyOf(b, Math.min(available, numOfBytes));
        }

        buff.close();
        return null;

    }


    public void changeFile(String path) throws IOException {
        if (path == null)
            throw new IllegalArgumentException();

        this.file = new FileInputStream(path);
        this.buff = new BufferedInputStream(file, BUFFER_SIZE);
    }

}
