package Huffman;

import java.util.Comparator;

public class HuffmanNode implements Comparable<HuffmanNode> {

    private byte[] value;
    private int count;
    private HuffmanNode parent;
    private HuffmanNode right;
    private HuffmanNode left;

    protected HuffmanNode(byte[] value, HuffmanNode parent, HuffmanNode right, HuffmanNode left) {
        this.value = value;
        this.count = 0;
        this.right = right;
        this.left = left;
    }

    public HuffmanNode(byte[] value, int count, HuffmanNode parent, HuffmanNode right, HuffmanNode left) {
        this.value = value;
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getParent() {
        return parent;
    }

    public void setParent(HuffmanNode parent) {
        this.parent = parent;
        if(this.parent.right==null)
            this.parent.right = this;
        else
            this.parent.left = this;
    }

    public boolean isLeaf(){
        return (this.left == null && this.right == null);
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return  this.getCount() - o.getCount();
    }
}
