package Huffman;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;
import main.Main;

import java.io.File;
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

    // This function will return the compressed file path.
    public String compressFile() throws IOException {
        File file = new File(filePath);
        String compressedFilePath = file.getParent() + "\\" + "6609." + String.format("%d.", numOfBytes) + file.getName() + ".hc";
        (new File(compressedFilePath)).createNewFile();

        BitMask bitMask = new BitMask();
        HuffmanCodeBuilder huffmanCodeBuilder = new HuffmanCodeBuilder(filePath);
        HuffmanTree tree = huffmanCodeBuilder.constructHuffmanTree(numOfBytes);
        writeHuffmanTreeHeader(tree, compressedFilePath);
        FileLoader reader = new FileLoader(filePath);
        int fileSize = reader.getFileSize();
        byte[] totalFile = reader.loadNBytesBinFile(fileSize);
        byte[] currElement;
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
                    int firstHalfLength = s.length() - (s.length() % 8);
                    String temp = s.substring(0, firstHalfLength);
                    writer.writeBytesToBuff(bitMask.stringToBytes(temp));
                    builder.setLength(0);
                    builder.append(s.substring(firstHalfLength));
                }
            }
        }

        if (builder.length() != 0) {
            writer.writeBytesToBuff(bitMask.stringToBytes(builder.toString()));
            writer.writeBuffToDisk();
            writer.writeAtPosition(new byte[]{(byte) (8 - (builder.length() % 8))}, 0);
        }

        return compressedFilePath;

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