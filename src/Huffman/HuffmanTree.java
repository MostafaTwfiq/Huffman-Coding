package Huffman;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class HuffmanTree {

    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    protected HuffmanTree(HuffmanNode root) {
        this.root = root;
        currNode = root;
    }

    public String getNodeCode(byte[] val) {
        StringBuilder stringBuilder = new StringBuilder();
        Queue<HuffmanNode> nodesQueue = new LinkedList<HuffmanNode>();
        nodesQueue.add(root);
        HuffmanNode currNode;
        HuffmanNode targetNode = null;
        while (!nodesQueue.isEmpty()) {
            currNode = nodesQueue.poll();
            if (currNode.getValue() != null && Arrays.equals(currNode.getValue(), val)) {
                targetNode = currNode;
                break;
            }

            HuffmanNode tempNode = currNode.getLeft();
            if (tempNode != null)
                nodesQueue.add(tempNode);

            tempNode = currNode.getRight();
            if (tempNode != null)
                nodesQueue.add(tempNode);

        }

        if (targetNode == null)
            return null;

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

}
