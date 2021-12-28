import FilesHandler.FileWriter;
import Huffman.BitMask;
import Huffman.FileCompressor;
import Huffman.HuffmanCodeBuilder;
import Huffman.HuffmanTree;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {


    public static void main (String[] args) {

        try {
            FileCompressor compressor = new FileCompressor("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\c.txt", 1);
            compressor.compressFile();
            System.exit(0);
            BitMask bitMask = new BitMask();
            byte[] numberOfBytesArr = ByteBuffer.allocate(4).putInt(98).array();
            for  (int i = 0; i < numberOfBytesArr.length; i++)
                System.out.println(numberOfBytesArr[i]);

            FileWriter writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\writeFile.txt");
            writer.writeBytesToBuff(numberOfBytesArr);
            writer.writeBuffToDisk();
            System.exit(0);


            //FileWriter writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\writeFile.txt");
            byte[] bytes = new byte[1];
            bytes[0] = 1;
            writer.writeBytesToBuff(bytes);
            writer.writeBuffToDisk();
            System.exit(0);
            HuffmanTree tree = new HuffmanCodeBuilder("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\testFile.txt").constructHuffmanTree(2);
            //tree.print();
            //System.exit(0);
            System.out.println(tree.getNodeCode(new byte[]{(byte) 'f', (byte) 'f'}));
            tree.decodeBitByBit(true);
            tree.decodeBitByBit(false);
            System.out.println(Arrays.toString(tree.decodeBitByBit(true)));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
