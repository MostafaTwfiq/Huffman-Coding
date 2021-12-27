import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class FilesReader {

    public static String readTextFile(String path) throws Exception {
        File file = new File(path);

        BufferedReader br = new BufferedReader(new java.io.FileReader(file));

        String st;
        StringBuilder sb = new StringBuilder();

        while ((st = br.readLine()) != null)
            sb.append(st).append("\n");

        return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : null;

    }

}
