package FilesHandler;

import java.io.*;

public class FileLoader {

    InputStream input;
    public FileLoader(String path) throws Exception {
        if (path == null)
            throw new IllegalStateException();

        this.input = new FileInputStream(path);
    }

    public String loadNBytesBinFile(int numOfBytes) throws Exception {

        byte[] b = new byte[numOfBytes];
        int available = input.available();
        if (available > 0) {
            input.read(b);
            System.out.println(b[0]);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < Math.min(available, numOfBytes); i++)
                stringBuilder.append((char) b[i]);

            return stringBuilder.toString();
        }

        closeFile();
        return null;
    }

    private void closeFile() throws Exception {
        input.close();
    }

    public void changeFile(String path) throws Exception {
        if (path == null)
            throw new IllegalArgumentException();

        //input.close();
        input = new FileInputStream(path);
    }

}
