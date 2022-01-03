package Huffman;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Vector;

public class FileCompressor {

    private String filePath;
    private int numOfBytes;
    private FileWriter writer;

    public FileCompressor(String filePath, int numOfBytes) {
        this.filePath = filePath;
        this.numOfBytes = numOfBytes;
    }

    public void compressFile() throws IOException {
        BitMask bitMask = new BitMask();
        HuffmanCodeBuilder huffmanCodeBuilder = new HuffmanCodeBuilder(filePath);
        HuffmanTree tree = huffmanCodeBuilder.constructHuffmanTree(numOfBytes);
        writeHuffmanTreeHeader(tree);
        FileLoader reader = new FileLoader(filePath);
        byte[] currElement;
        byte[] prevByte = null;
        byte prevByteLastByteSize = 0;
        String code;
        while ((currElement = reader.loadNBytesBinFile(numOfBytes)) != null) {
            code = tree.getNodeCode(currElement);
            byte[] b = bitMask.stringToBytes(code);
            if (code.length() % 8 != 0 || prevByte != null) {
                if (prevByte != null) {
                    prevByte = bitMask.mergeTwoBytesArr(prevByte, prevByteLastByteSize, b, code.length() % 8);
                    // 8 - l1 == l2 write
                    // 8 - l1 < l2 --> prelen = l2 - (8 - l1) = l2 + l1 - 8
                    // 8 - l1 > l2 ---> prelen = 8 - l1 - l2
                    if (8 - prevByteLastByteSize == code.length() % 8) {
                        writer.writeBytesToBuff(prevByte);
                        prevByte = null;
                    } else if (8 - prevByteLastByteSize < code.length() % 8)
                        prevByteLastByteSize = (byte) ((code.length() % 8) - 8 + prevByteLastByteSize);
                    else
                        prevByteLastByteSize = (byte) (prevByteLastByteSize + (code.length() % 8));

                } else {
                    prevByte = b;
                    prevByteLastByteSize = (byte) (code.length() % 8);
                }
            } else
                writer.writeBytesToBuff(b);
        }
        if (prevByte != null) {
            writer.writeBytesToBuff(prevByte);
        }

        writer.writeBuffToDisk();
        if (prevByte != null) {
            writer.writeAtPosition(new byte[]{prevByteLastByteSize}, 0);
        }
        writer.closeFile();
    }

    private void writeHuffmanTreeHeader(HuffmanTree tree) throws IOException {
        writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\try.txt");
        byte[] nOfBytes = ByteBuffer.allocate(4).putInt(numOfBytes).array();
        byte[] totalTreeSize = ByteBuffer.allocate(4).putInt(tree.treeSize()).array();
        writer.writeBytesToBuff(new byte[]{0});
        writer.writeBytesToBuff(nOfBytes);
        writer.writeBytesToBuff(totalTreeSize);

        byte[][] inorder = tree.getInOrder();
        for (byte[] b : inorder)
            writer.writeBytesToBuff(b);


        var header = tree.treeToHeader().toCharArray();
        BitSet set        = new BitSet(header.length);
        for (int i = 0, headerLength = header.length; i < headerLength; i++) {
            char b = header[i];
            if (b == '1') {
                set.set(7 - i%8);
            }
        }
        writer.writeBytesToBuff( set.toByteArray());
        writer.writeBuffToDisk();
    }
}
