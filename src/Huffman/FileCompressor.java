package Huffman;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;
import main.Main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FileCompressor {

    private String filePath;
    private int numOfBytes;
    private FileWriter writer;

    public FileCompressor(String filePath, int numOfBytes) {
        this.filePath = filePath;
        this.numOfBytes = numOfBytes;
    }

    public HuffmanTree compressFile() throws IOException {
        String destinationPath = filePath.substring(0, filePath.lastIndexOf('.')) + "C" + ".exe.hc";
        BitMask bitMask = new BitMask();
        HuffmanCodeBuilder huffmanCodeBuilder = new HuffmanCodeBuilder(filePath);
        HuffmanTree tree = huffmanCodeBuilder.constructHuffmanTree(numOfBytes);
        writeHuffmanTreeHeader(tree, destinationPath);
        FileLoader reader = new FileLoader(filePath);
        int fileSize = reader.getFileSize();
        byte[] totalFile = reader.loadNBytesBinFile(fileSize);
        byte[] currElement;
        byte[] prevByte = null;
        byte prevByteLastByteSize = 0;
        String code;
        StringBuilder builder = new StringBuilder();
        int maxStringSize = 1024;
        for (int i = 0; i < fileSize; i += numOfBytes) {
            currElement = Arrays.copyOfRange(totalFile, i, i + numOfBytes);
            code = tree.getNodeCode(currElement);
            builder.append(code);
            if (builder.length() >= maxStringSize) {
                String s = builder.toString();
                if (s.length() % 8 == 0) {
                    writer.writeBytesToBuff(bitMask.stringToBytes(s));
                    builder.setLength(0);
                } else {
                    // 1111111111
                    // 0123456789
                    // 10 - 2 = 8
                    // 0 -> 8
                    int firstHalfLength = s.length() - (s.length() % 8);
                    String temp = s.substring(0, firstHalfLength);
                    writer.writeBytesToBuff(bitMask.stringToBytes(temp));
                    builder.setLength(0);
                    builder.append(s.substring(firstHalfLength));
                }
            }
        }
            /*currElement = Arrays.copyOfRange(totalFile, i, i + numOfBytes);
            code = tree.getNodeCode(currElement);
            byte[] b = bitMask.stringToBytes(code);

            if (code.length() % 8 != 0 || prevByte != null) {
                if (prevByte != null) {
                    prevByte = bitMask.mergeTwoBytesArr(prevByte, prevByteLastByteSize, b, code.length() % 8);
                    // 8 - l1 == l2 write
                    // 8 - l1 < l2 --> prelen = l2 - (8 - l1) = l2 + l1 - 8
                    // 8 - l1 > l2 ---> prelen = 8 - l1 - l2
                    // 8- l1 =  3
                    if (8 - prevByteLastByteSize == code.length() % 8) {
                        writer.writeBytesToBuff(prevByte);
                        prevByteLastByteSize = 0;
                        prevByte = null;
                    } else if (8 - prevByteLastByteSize < code.length() % 8)
                        prevByteLastByteSize = (byte) ((code.length() % 8) + prevByteLastByteSize - 8);
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
            writer.writeAtPosition(new byte[]{(byte) (8 - prevByteLastByteSize)}, 0);
        }
        writer.closeFile();*/

        if (builder.length() != 0) {
            writer.writeBytesToBuff(bitMask.stringToBytes(builder.toString()));
            writer.writeBuffToDisk();
            writer.writeAtPosition(new byte[]{(byte) (8 - (builder.length() % 8))}, 0);
        }

        return tree;
    }

    private void writeHuffmanTreeHeader(HuffmanTree tree, String destinationPath) throws IOException {
        writer = new FileWriter(destinationPath);
        byte[] nOfBytes = ByteBuffer.allocate(4).putInt(numOfBytes).array();
        byte[] totalTreeSize = ByteBuffer.allocate(4).putInt(tree.treeSize()).array();
        writer.writeBytesToBuff(new byte[]{0});
        writer.writeBytesToBuff(nOfBytes);
        writer.writeBytesToBuff(totalTreeSize);
        byte[] headerBytes = tree.treeToHeader(numOfBytes);

        writer.writeBytesToBuff(headerBytes);
        writer.writeBuffToDisk();
    }
}