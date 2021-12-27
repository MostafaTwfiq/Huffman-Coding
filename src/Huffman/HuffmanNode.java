package Huffman;

public class HuffmanNode {

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

    protected HuffmanNode(byte[] value, int count, HuffmanNode parent, HuffmanNode right, HuffmanNode left) {
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
    }
}
