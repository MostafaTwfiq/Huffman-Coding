package main;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;
import Huffman.*;

import java.util.BitSet;
import java.util.Vector;

public class Main {

    public static Vector<Byte> wow = new Vector<>();

    public static void main (String[] args) {
        try {

            /*String s = "1010101010";
            System.out.println(s.substring(0, 8));
            System.exit(0);
            BitSet  b1  = new ExtendedBitSet(6);
            // 111011
            // 00011101 11111111

            // 12 11 10 9 8 7
            // 1   1  1 0 1 1

            // 6 5 4 3 2 1 0
            // 1 1 1 1 1 1 1

            b1.set(5,true);
            b1.set(4,true);
            b1.set(3,true);
            b1.set(2,false);
            b1.set(1,true);
            b1.set(0,true);

            BitSet b2 = new BitSet(8);
            b2.set(7,false);
            b2.set(6,true);
            b2.set(5,true);
            b2.set(4,true);
            b2.set(3,true);
            b2.set(2,true);
            b2.set(1,true);
            b2.set(0,true);

            BitMask bmask =  new BitMask();
            BitSet b3 = bmask.mergeBitSets(b1,b2);
            byte[] barr = b3.toByteArray();
            for (var b:
                 barr) {
                System.out.println(bmask.byteToString(b));
            }
            System.exit(-1);*/
            /*BitMask bitMask = new BitMask();
            byte[] b1 = new byte[]{(byte) 0b11101100, (byte) 0b00111111, (byte) 0b11111110, 0b00010000};
            byte[] b2 = new byte[]{(byte) 0b01111110, (byte) 0b11111111};
            byte[] b3 = bitMask.mergeTwoBytesArr(b1, 6, b2, 8);
            for (byte bb : b3)
                System.out.println(bitMask.byteToString(bb));*/

            //FileWriter writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\dec.txt");
            //writer.writeBytesToBuff(new byte[]{98});
            //writer.writeBuffToDisk();
            //System.exit(0);

            //BitMask bitMask = new BitMask();
            //System.out.println(bitMask.byteToString(bitMask.stringToBytes("1000000100001000")[1]));
            //byte b = (byte) 0b11101110;

            /*byte[] b1 = new byte[]{(byte) 0b11101111};
            byte[] b2 = new byte[]{(byte) 0b00111111};
            byte[] f = bitMask.mergeTwoBytesArr(b1, 7, b2, 8);
            for (byte b : f)
                System.out.println(bitMask.byteToString(b));
*/

            //System.exit(0);
            // -63 63
            // |30| < x
            // 50 -50
            // 40 -40
            /*while (true) {
                FileWriter f = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\dec.txt");
                Vector<Byte> v = new Vector<>();
                for (long i = 0; i < 10; i++) {
                    int val = (int) Math.floor(Math.random() * (40 - (-40) + 1) + (-40));
                    //System.out.println(val);
                    f.writeBytesToBuff(new byte[]{(byte) val});
                    v.add((byte) val);
                    wow.add((byte) val);
                }
                f.writeBuffToDisk();

                Byte[] vect = v.toArray(Byte[]::new);
                FileLoader fileLoader = new FileLoader("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\dec.txt");
                int fileSize = fileLoader.getFileSize();
                byte[] b = fileLoader.loadNBytesBinFile(fileSize);
                for (int i = 0; i < fileSize; i += 1) {
                    if (vect[i] != b[i])
                        System.out.println(vect[i]);

                }*/

            //System.exit(0);
            //System.exit(0);*/
            //FileWriter fileWriter = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\dec.txt");
            //fileWriter.writeBytesToBuff(new byte[]{-127, -2, 0, -127, -127, 0});
            //fileWriter.writeBuffToDisk();


            long startTime = System.currentTimeMillis();

            FileCompressor compressor = new FileCompressor("D:\\gbbct10.seq", 1);
            HuffmanTree tree = compressor.compressFile();
            System.out.println("done compressing");
            FileDecompressor decompressor = new FileDecompressor("D:\\gbbct10C.exe.hc");
            HuffmanTree tree1 = decompressor.decompressFile();

            System.out.println(tree.checkTwoTrees(tree1));
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            System.out.println(elapsedTime / 1000.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
