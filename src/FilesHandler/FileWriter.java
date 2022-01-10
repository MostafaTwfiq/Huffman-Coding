package FilesHandler;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.util.Arrays;

public class FileWriter {

    private final int BUFFER_SIZE = 50 * 1024 * 1024; // 50MB
    private boolean fileIsOpened;
    private String filePath;
    private FileOutputStream file;
    private BufferedOutputStream buff;

    public FileWriter(String path) throws IOException {
        if (path == null)
            throw new IllegalStateException();

        this.filePath = path;
        openFile();
    }
    public void writeAtPosition(byte[] b, int pos) throws IOException {
        var fb = new RandomAccessFile(filePath,"rw");
        fb.seek(0);
        fb.write(b);
        fb.close();
    }

    private void openFile() throws IOException {
        this.file = new FileOutputStream(filePath);
        this.buff = new BufferedOutputStream(file, BUFFER_SIZE);
        fileIsOpened = true;
    }

    public void writeBytesToBuff(byte[] bytesArr) throws IOException {
        if (!fileIsOpened)
            openFile();

        buff.write(bytesArr);
    }

    public void writeBuffToDisk() throws IOException{
        buff.flush();
    }

    public void closeFile() throws IOException {
        buff.flush();
        buff.close();
        fileIsOpened = false;
    }


    public void changeFile(String path) throws IOException {
        if (path == null)
            throw new IllegalArgumentException();

        this.filePath = path;
        fileIsOpened = false;
    }

}
