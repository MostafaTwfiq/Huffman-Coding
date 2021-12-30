import FilesHandler.FileWriter;
import Huffman.*;

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
            /*byte[] b1 = new byte[]{(byte) 0b11111000};
            byte[] b2 = new byte[]{(byte) 0b11111100, (byte) 0b01110000};
            BitMask bitMask = new BitMask();
            byte[] b3 = bitMask.mergeTwoBytesArr(b1, 5, b2, 4);
            for (byte b : b3)
                System.out.println(bitMask.byteToString(b));*/
            FileDecompressor decompressor = new FileDecompressor("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\try.txt");
            decompressor.decompressFile();
            System.exit(0);
            FileCompressor compressor = new FileCompressor("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\c.txt", 1);
            compressor.compressFile();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
