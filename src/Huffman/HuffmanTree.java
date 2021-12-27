package Huffman;

public class HuffmanTree {

    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    protected HuffmanTree(HuffmanNode root) {
        this.root = root;
        currNode = root;
    }

    public String decodeBitByBit(boolean bit) {
        if (root.getC() != null) // special case when the tree has only one node
            return root.getC();
        else {
            currNode = bit ? currNode.getRight() : currNode.getLeft(); // check if to go right or left
            if (currNode == null) // check if the passed sequence of bits has no corresponding character
                throw new IllegalStateException();

            String c = currNode.getC();
            if (c != null)
                resetDecoder();

            return c;

        }
    }

    public void resetDecoder() {
        currNode = root;
    }

}
