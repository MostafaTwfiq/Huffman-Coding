import Huffman.HuffmanCodeBuilder;
import Huffman.HuffmanTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {


    public static void main (String[] args) {


        System.exit(0);
        String s = new String();
        for (int i = 0; i < 45; i++)
            s += "a";
        for (int i = 0 ;i < 13; i++)
            s+= "b";
        for (int i = 0; i < 12; i++)
            s+= "c";
        for (int i = 0; i< 16; i++)
            s+= "d";
        for (int i = 0; i < 9; i++)
            s+= "e";
        for (int i =0; i <5;i++)
            s+="f";

        //HuffmanTree tree = new HuffmanCodeBuilder(s).constructHuffmanTree();
        //tree.decodeBitByBit(true);
        //tree.decodeBitByBit(false);
        //System.out.println(tree.decodeBitByBit(true));
    }
}
