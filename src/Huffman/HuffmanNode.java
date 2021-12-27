package Huffman;

public class HuffmanNode {

    private String c;
    private int count;
    private HuffmanNode right;
    private HuffmanNode left;

    protected HuffmanNode(String c, int count, HuffmanNode right, HuffmanNode left) {
        this.c = c;
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
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

}
