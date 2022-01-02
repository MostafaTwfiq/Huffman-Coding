package Huffman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

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

    public String treeToHeader() {
        ArrayList<Byte> bytes = new ArrayList<Byte>();

        StringBuilder stringBuilder = new StringBuilder();
        Stack<HuffmanNode> nodesStack = new Stack<>();
        HashSet<HuffmanNode> nodesHashset = new HashSet<>();
        nodesStack.push(root);

        HuffmanNode currNode, leftNode ,rightNode;
        byte temp   = 0;
        int bitwrtn = 0;

        while (!nodesStack.empty()) {
            currNode = nodesStack.pop();
            if (nodesHashset.contains(currNode)) {
                if(bitwrtn == 8){
                    bytes.add(new Byte(temp));
                    temp    = 0;
                    bitwrtn = 0;
                }
                bitwrtn++;
                //stringBuilder.append('0');
                continue;
            }
            //11101101 000
            //11101101 000 00000
            leftNode = currNode.getLeft();
            rightNode = currNode.getRight();
            nodesHashset.add(currNode);
            if (leftNode == null && rightNode == null) {
                if(bitwrtn < 8) {
                    temp  = (byte)(temp | (1 << 8 - ++bitwrtn));
                }else{
                    bytes.add(new Byte(temp));
                    bitwrtn = 0;
                    temp  = (byte)(temp | (1 << 8 - ++bitwrtn));
                }
                //stringBuilder.append('1');
            } else {
                nodesStack.push(currNode);
                if (rightNode != null)
                    nodesStack.push(rightNode);
                if (leftNode != null)
                    nodesStack.push(leftNode);
            }
        }
        if (bitwrtn!=0)
            bytes.add(new Byte(temp));
        for (var ele: bytes) {
            stringBuilder.append(new BitMask().byteToString(ele));
        }
        System.out.println("Padding:"+(8-bitwrtn));
        return stringBuilder.toString();
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
