package Huffman;

import java.util.LinkedList;
import java.util.Queue;

public class HuffmanTree {

    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    protected HuffmanTree(HuffmanNode root) {
        this.root = root;
        currNode = root;
    }

    public String getNodeCode(String val) {
        StringBuilder stringBuilder = new StringBuilder();
        Queue<HuffmanNode> nodesQueue = new LinkedList<HuffmanNode>();
        nodesQueue.add(root);
        HuffmanNode currNode;
        HuffmanNode targetNode = null;
        while (!nodesQueue.isEmpty()) {
            currNode = nodesQueue.poll();
            if (currNode.getValue() != null && currNode.getValue().equals(val)) {
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

    public String decodeBitByBit(boolean bit) {
        if (root.getValue() != null) // special case when the tree has only one node
            return root.getValue();
        else {
            currNode = bit ? currNode.getRight() : currNode.getLeft(); // check if to go right or left
            if (currNode == null) // check if the passed sequence of bits has no corresponding character
                throw new IllegalStateException();

            String c = currNode.getValue();
            if (c != null)
                resetDecoder();

            return c;

        }
    }

    public void resetDecoder() {
        currNode = root;
    }

}
