package Huffman;

import java.util.Comparator;

public class HuffmanNodeComparator implements Comparator<HuffmanNode> {
        @Override
        public int compare(HuffmanNode n1, HuffmanNode n2) {
            return n1.getCount() - n2.getCount();
        }

}
