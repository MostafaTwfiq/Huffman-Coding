package Huffman;

import java.util.*;

public class HuffmanTree {

    private HashMap<ByteArray, HuffmanNode> nodesMap;
    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    protected HuffmanTree(HuffmanNode root, HashMap<ByteArray, HuffmanNode> nodesMap) {
        this.root = root;
        currNode = root;
        this.nodesMap = nodesMap;
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

    public byte[] treeToHeader(int numOfBytes) {
        byte[] headerSeq = new byte[(int) Math.ceil((2 * treeSize() - 1)/8.0)];
        byte[] headerBytes = new byte[numOfBytes * treeSize() + headerSeq.length];
        BitMask bitMask  = new BitMask();
        Stack<HuffmanNode> nodesStack = new Stack<>();
        HashSet<HuffmanNode> nodesHashset = new HashSet<>();
        nodesStack.push(root);

        HuffmanNode currNode, leftNode ,rightNode;
        int nodesCount = 0, count = 0;

        while (!nodesStack.empty()) {
            currNode = nodesStack.pop();
            if (nodesHashset.contains(currNode)) {
                headerSeq[count/8] = bitMask.addBitToByte(headerSeq[count/8], false, (byte) (count%8));
                count++;
                continue;
            }

            leftNode = currNode.getLeft();
            rightNode = currNode.getRight();
            nodesHashset.add(currNode);
            if (leftNode == null && rightNode == null) {
                for (int i = 0 ; i < currNode.getValue().length; i++) {
                    headerBytes[nodesCount + i] = currNode.getValue()[i];
                }
                nodesCount += currNode.getValue().length;
                headerSeq[count/8] = bitMask.addBitToByte(headerSeq[count/8], true, (byte) (count%8));
                count++;
            } else {
                nodesStack.push(currNode);
                if (rightNode != null)
                    nodesStack.push(rightNode);
                if (leftNode != null)
                    nodesStack.push(leftNode);
            }
        }

        for (int i = 0; i < headerSeq.length; i++)
            headerBytes[nodesCount + i] = headerSeq[i];

        return headerBytes;
    }

    public void resetDecoder() {
        currNode = root;
    }

    public int treeSize() {
        return nodesMap.size();
    }

}
