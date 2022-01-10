package Huffman;

import java.util.*;

public class HuffmanTree {

    private HashMap<ByteArray, HuffmanNode> nodesMap;
    private HuffmanNode root;
    private HuffmanNode currNode; // it will be useful while decoding

    public boolean checkTwoTrees(HuffmanTree tree) {
        return checkHelper(root, tree.root);
    }

    private boolean checkHelper(HuffmanNode root1, HuffmanNode root2) {
        if (root1 == null && root2 == null)
            return true;
        if (root1 == null || root2 == null)
            return false;
        if (root1.getValue() == null && root2.getValue() != null)
            return false;
        if (root2.getValue() == null && root1.getValue() != null)
            return false;

        if (root1.getValue() != null && !checkTwoBytesArrays(root1.getValue(), root2.getValue()))
            return false;

        return checkHelper(root1.getLeft(), root2.getLeft()) && checkHelper(root1.getRight(), root2.getRight());

    }

    private boolean checkTwoBytesArrays(byte[] b1, byte[] b2) {
        if (b1.length != b2.length)
            return false;

        for (int i = 0; i < b1.length; i++) {
            if (b1[i] != b2[i])
                return false;
        }

        return true;
    }

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
