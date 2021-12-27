import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public abstract class FilesReader {

    public static String readTextFile(String path) throws Exception {
        // File path is passed as parameter
        File file = new File(path);

        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)

        // Creating an object of BufferedReader class
        BufferedReader br = new BufferedReader(new java.io.FileReader(file));

        String st;
        StringBuilder sb = new StringBuilder();

        while ((st = br.readLine()) != null)
            sb.append(st).append("\n");

        return sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1).toString() : null;

    }

}
