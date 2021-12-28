package Huffman;

import java.util.HashMap;

public class HuffmanTree {

    private HashMap<ByteArray, HuffmanNode> nodesMap;
    private byte[][] sortedBytes;
    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    protected HuffmanTree(HuffmanNode root, HashMap<ByteArray, HuffmanNode> nodesMap, byte[][] sortedBytes) {
        this.root = root;
        currNode = root;
        this.nodesMap = nodesMap;
        this.sortedBytes = sortedBytes;
    }

    public String getNodeCode(byte[] val) {
        HuffmanNode targetNode = nodesMap.get(new ByteArray(val));
        if (targetNode == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder();
        while (targetNode.getParent() != null) {
            stringBuilder.append(targetNode.getParent().getLeft() == targetNode ? 0 : 1);
            targetNode = targetNode.getParent();
        }

        return stringBuilder.length() == 0 ? stringBuilder.append(0).toString() : stringBuilder.reverse().toString();
    }


    public byte[] decodeBitByBit(boolean bit) {
        if (root.getValue() != null) // special case when the tree has only one node
            return root.getValue();
        else {
            currNode = bit ? currNode.getRight() : currNode.getLeft(); // check if to go right or left
            if (currNode == null) // check if the passed sequence of bits has no corresponding character
                throw new IllegalStateException();

            byte[] bytes = currNode.getValue();
            if (bytes != null)
                resetDecoder();

            return bytes;

        }
    }

    public void resetDecoder() {
        currNode = root;
    }


    public int treeSize() {
        return nodesMap.size();
    }

    public void print() {
        print(root);
    }

    private void print(HuffmanNode root) {
        if (root == null)
            return;

        print(root.getLeft());
        if (root.getValue() == null) {
            System.out.println("null " + root.getCount());
        } else {
            for (int i = 0; i < root.getValue().length; i++) {
                System.out.print((char) root.getValue()[i] + " ");
            }
            System.out.println(root.getCount());
        }

        System.out.println();
        print(root.getRight());
    }

    public byte[][] getSortedBytes() {
        return sortedBytes;
    }
}
