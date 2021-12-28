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
            HuffmanTree tree = new HuffmanCodeBuilder("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\testFile.txt").constructHuffmanTree(2);
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
