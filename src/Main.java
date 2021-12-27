import Huffman.HuffmanCodeBuilder;
import Huffman.HuffmanTree;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {


    public static void main (String[] args) {

        /*Path path = Paths.get("doc.txt");
        byte[] bytes = "ABCD".getBytes(StandardCharsets.UTF_8);

        try {
            Files.write(path, bytes);    // Java 7+ only
            System.out.println("Successfully written data to the file");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);

        /*String s = new String();
        for (int i = 0; i < 45; i++)
            s += "aa";
        for (int i = 0 ;i < 13; i++)
            s+= "bb";
        for (int i = 0; i < 12; i++)
            s+= "cc";
        for (int i = 0; i< 16; i++)
            s+= "dd";
        for (int i = 0; i < 9; i++)
            s+= "ee";
        for (int i =0; i <5;i++)
            s+="ff";

        System.out.println(s);
        System.exit(0);*/

        try {
            HuffmanTree tree = new HuffmanCodeBuilder("D:\\gbbct10.seq").constructHuffmanTree(1);
            System.out.println(tree.getNodeCode("fn"));
            //tree.decodeBitByBit(true);
            //tree.decodeBitByBit(false);
            //System.out.println(tree.decodeBitByBit(true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
