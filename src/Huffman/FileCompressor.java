package Huffman;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;

import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
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
                    prevByte = bitMask.mergeTwoBytesArr(prevByte, prevByteLastByteSize, b, 8 - code.length() % 8);
                    // 8 - l1 == l2 write
                    // 8 - l1 < l2 --> prelen = l2 - (8 - l1) = l2 + l1 - 8
                    // 8 - l1 > l2 ---> prelen = 8 - l1 - l2
                    if (8 - prevByteLastByteSize == code.length()) {
                        writer.writeBytesToBuff(b);
                        prevByte = null;
                    } else if (8 - prevByteLastByteSize < code.length())
                        prevByteLastByteSize += code.length() - 8;
                    else
                        prevByteLastByteSize = (byte) (8 - prevByteLastByteSize - code.length());

                } else {
                    prevByte = b;
                    prevByteLastByteSize = (byte) (8 - code.length() % 8);
                }
            } else
                writer.writeBytesToBuff(b);
        }
        if (prevByte != null)
            writer.writeBytesToBuff(prevByte);

        writer.writeBuffToDisk();
        writer.closeFile();
    }

    private void writeHuffmanTreeHeader(HuffmanTree tree) throws IOException {
        writer = new FileWriter("C:\\Users\\mosta\\IdeaProjects\\SIC\\Huffman-Coding\\try.txt");
        byte[] nOfBytes = ByteBuffer.allocate(4).putInt(numOfBytes).array();
        byte[] totalTreeSize = ByteBuffer.allocate(4).putInt(tree.treeSize()).array();
        writer.writeBytesToBuff(nOfBytes);
        writer.writeBytesToBuff(totalTreeSize);
        byte[][] uniqueBytesSorted = tree.getSortedBytes();
        for (byte[] b : uniqueBytesSorted)
            writer.writeBytesToBuff(b);

        writer.writeBuffToDisk();
    }

}
