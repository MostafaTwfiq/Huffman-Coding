package Huffman;

import FilesHandler.FileLoader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class FileDecompressor {

    private String filePath;
    public FileDecompressor(String filePath) {
        this.filePath = filePath;
    }


    public void decompressFile() throws IOException {
        BitMask bitMask = new BitMask();
        FileLoader fileLoader = new FileLoader(filePath);
        byte[] temp = fileLoader.loadNBytesBinFile(1);
        short numOfDummyZeroBits = temp[0];
        temp = fileLoader.loadNBytesBinFile(4);
        int numOfBytes = ByteBuffer.wrap(temp).getInt();
        temp = fileLoader.loadNBytesBinFile(4);
        int numOfElements = ByteBuffer.wrap(temp).getInt();

        Comparator<HuffmanNode> comparator = new HuffmanNodeComparator();
        PriorityQueue<HuffmanNode> nodes = new PriorityQueue<HuffmanNode>(numOfElements, comparator);

        int[] arr = new int[]{2, 4, 6};
        for (int i = 0; i < numOfElements; i++)
            nodes.add(new HuffmanNode(fileLoader.loadNBytesBinFile(numOfBytes), arr[i], null, null, null));

        HuffmanTree tree = HuffmanCodeBuilder.reconstructHuffmanTree(nodes, numOfBytes);
        byte[] buff;
        byte[] currElement;
        String byteCode;
        while ((buff = fileLoader.loadNBytesBinFile(1)) != null) {
            boolean fileIsFinished = fileLoader.getAvailableBytes() < 1;
            byteCode = bitMask.byteToString(buff[0]);
            for (int i = 0; i < (fileIsFinished ? numOfDummyZeroBits : 8); i++) {
                currElement = tree.decodeBitByBit(byteCode.charAt(i) == '1');
                if (currElement != null) {
                    for (byte b : currElement)
                        System.out.print((char) b);
                }

            }

        }

    }

}
