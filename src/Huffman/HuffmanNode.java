package Huffman;

public class HuffmanNode {

    private Character c;
    private int count;
    private HuffmanNode right;
    private HuffmanNode left;

    protected HuffmanNode(Character c, int count, HuffmanNode right, HuffmanNode left) {
        this.c = c;
        this.count = count;
        this.right = right;
        this.left = left;
    }

    public Character getC() {
        return c;
    }

    public void setC(Character c) {
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
