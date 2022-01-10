package FilesHandler;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileLoader {

    private final int MAX_BUFFER_SIZE = 50 * 1024 * 1024; // 50MB

    private RandomAccessFile randomAccessFile;
    private MappedByteBuffer buff;
    private int loadedBytesCount;
    private int currBuffSize;
    private int buffLoadedBytes;

    public FileLoader(String path) throws IOException {
        if (path == null)
            throw new IllegalStateException();

        this.randomAccessFile = new RandomAccessFile(path, "r");
        loadedBytesCount = 0;
    }

    public int getAvailableBytes() throws IOException {
        return (int) (randomAccessFile.length() - loadedBytesCount);
    }

    public int getFileSize() throws IOException {
        return (int) randomAccessFile.length();
    }

    public byte[] loadNBytesBinFile(int numOfBytes) throws IOException {
        int numOfBytesToRead = Math.min(numOfBytes, getAvailableBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] b = null;

        while (numOfBytesToRead != 0) {
            int toRead = Math.min(numOfBytesToRead, currBuffSize - buffLoadedBytes);
            if (toRead == 0) {
                if(loadToBuff())
                    return outputStream.size() == 0 ? null : outputStream.toByteArray();

                toRead = Math.min(numOfBytesToRead, currBuffSize - buffLoadedBytes);

            }
            numOfBytesToRead -= toRead;
            buffLoadedBytes += toRead;
            loadedBytesCount += toRead;
            b = new byte[toRead];
            buff.get(b);
            outputStream.write(b);
        }

        return outputStream.size() == 0 ? null : outputStream.toByteArray();
    }

    private boolean loadToBuff() throws IOException {
        currBuffSize = (int) Math.min(randomAccessFile.length() - loadedBytesCount, MAX_BUFFER_SIZE);
        if (currBuffSize == 0)
            return true;

        buffLoadedBytes = 0;
        buff = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, loadedBytesCount, currBuffSize);

        return false;
    }


    public void changeFile(String path) throws IOException {
        if (path == null)
            throw new IllegalArgumentException();

        this.randomAccessFile = new RandomAccessFile(path, "r");
        loadedBytesCount = 0;
    }

}
