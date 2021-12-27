package Huffman;

public class HuffmanNode {

    private String value;
    private int count;
    private HuffmanNode parent;
    private HuffmanNode right;
    private HuffmanNode left;

    protected HuffmanNode(String c, HuffmanNode parent, HuffmanNode right, HuffmanNode left) {
        this.value = c;
        this.count = 0;
        this.right = right;
        this.left = left;
    }

    protected HuffmanNode(String c, int count, HuffmanNode parent, HuffmanNode right, HuffmanNode left) {
        this.value = c;
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
