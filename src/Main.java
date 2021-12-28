import FilesHandler.FileWriter;
import Huffman.HuffmanCodeBuilder;
import Huffman.HuffmanTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {


    public static void main (String[] args) {
        try {

            FileWriter writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\writeFile.txt");
            writer.writeBytesToBuff("123".getBytes(StandardCharsets.UTF_8));
            //writer.writeBuffToDisk();
            writer.writeBytesToBuff("123".getBytes(StandardCharsets.UTF_8));
            //writer.writeBuffToDisk();
            writer.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
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
            HuffmanTree tree = new HuffmanCodeBuilder("D:\\gbbct10.seq").constructHuffmanTree(2);
            tree.print();
            System.exit(0);
            System.out.println(tree.getNodeCode(new byte[]{(byte) 'a', (byte) 'a'}));
            tree.decodeBitByBit(true);
            tree.decodeBitByBit(false);
            System.out.println(Arrays.toString(tree.decodeBitByBit(true)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
