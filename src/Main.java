import Huffman.HuffmanCodeBuilder;
import Huffman.HuffmanTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {


    public static void main (String[] args) {

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
            HuffmanTree tree = new HuffmanCodeBuilder("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\testFile.txt").constructHuffmanTree(2);
            System.out.println(tree.getNodeCode("fn"));
            //tree.decodeBitByBit(true);
            //tree.decodeBitByBit(false);
            //System.out.println(tree.decodeBitByBit(true));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
