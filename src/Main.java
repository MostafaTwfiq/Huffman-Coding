import FilesHandler.FileWriter;
import Huffman.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {

    public static void main (String[] args) {
        try {
            /*byte[] b1 = new byte[]{(byte) 0b11111000};
            byte[] b2 = new byte[]{(byte) 0b11111100, (byte) 0b01110000};
            BitMask bitMask = new BitMask();
            byte[] b3 = bitMask.mergeTwoBytesArr(b1, 5, b2, 4);
            for (byte b : b3)
                System.out.println(bitMask.byteToString(b));*/
            /*FileDecompressor decompressor = new FileDecompressor("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\try.txt");
            decompressor.decompressFile();
            System.exit(0);
            FileCompressor compressor = new FileCompressor("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\c.txt", 1);
            compressor.compressFile();
            System.exit(0);*/
            //HuffmanCodeBuilder builder = new HuffmanCodeBuilder("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\testFile.txt");
           // HuffmanTree tree = builder.constructHuffmanTree(1);
//
//            var bb = new byte[][]{
//                    new byte[]{(byte)'c'},
//                    new byte[]{(byte)'a'},
//                    new byte[]{(byte)'b'},
//                    new byte[]{(byte)'k'}};
//            HuffmanCodeBuilder.reconstructHuffmanTree(bb,"1101100");
            var fname  = "D:\\Uni\\Term 7\\Algorithms\\project\\Huffman-Coding\\test.huff";
            var fout = new FileOutputStream(fname);
            /*
            * 6 1 4 cabk 1101100 aaabbbccckkkk
            * 00000110
            * 00000000 00000000 00000000 00000001
            * 00000000 00000000 00000000 00000100
            *
            * 01100011 c
            * 01100001 a
            * 01100010 b
            * 01101011 k
            *
            * 1101100 0 sequence +padding
            * 01 01 01 10 10 10 00 00 00 11 11 11 11 compressed text
            * */
            // Done ka decompression
            // eh tani eli fadel?
            var file = new byte[]{
                    0b00000110,
                    0b00000000,
                    0b00000000,
                    0b00000000,
                    0b00000001,
                    0b00000000,
                    0b00000000,
                    0b00000000,
                    0b00000100,
                    0b01100011,
                    0b01100001,
                    0b01100010,
                    0b01101011,
                    (byte) 0b11011000,
                    0b01010110,
                    (byte) 0b10100000,
                    0b00111111,
                    (byte) 0b11000000
            };
            for (var f:
                 file) {
                fout.write(f);
            }
            fout.close();
            var fd = new FileDecompressor(fname);
            fd.decompressFile();
            //String s = tree.treeToHeader();
            //System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
