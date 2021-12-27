package FilesHandler;

import java.io.*;
import java.util.Arrays;

public class FileLoader {

    int BUFFER_SIZE = 400 * 1024 * 1024; // 16KB

    private FileInputStream file;
    private BufferedInputStream buff;

    public FileLoader(String path) throws Exception {
        if (path == null)
            throw new IllegalStateException();

        this.file = new FileInputStream(path);
        this.buff = new BufferedInputStream(file, BUFFER_SIZE);

    }

    public byte[] loadNBytesBinFile(int numOfBytes) throws Exception {

        byte[] b = new byte[numOfBytes];
        int available = buff.available();
        if (available > 0) {
            buff.read(b);
            return Arrays.copyOf(b, Math.min(available, numOfBytes));
        }

        buff.close();
        return null;

    }


    public void changeFile(String path) throws Exception {
        if (path == null)
            throw new IllegalArgumentException();

        this.file = new FileInputStream(path);
        this.buff = new BufferedInputStream(file, BUFFER_SIZE);
    }

}
