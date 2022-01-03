package Huffman;

import FilesHandler.FileLoader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class FileDecompressor {

    private String filePath;

    public FileDecompressor(String filePath) {
        this.filePath = filePath;
    }
    //modify reconstruct function a8ayar el data

    public void decompressFile() throws IOException {
        BitMask bitMask = new BitMask();
        FileLoader fileLoader = new FileLoader(filePath);
        byte[] temp = fileLoader.loadNBytesBinFile(1);
        short numOfDummyZeroBits = temp[0];

        temp = fileLoader.loadNBytesBinFile(4);
        int numOfBytes = ByteBuffer.wrap(temp).getInt();

        temp = fileLoader.loadNBytesBinFile(4);
        int numOfElements = ByteBuffer.wrap(temp).getInt();

        // read header
        temp = fileLoader.loadNBytesBinFile(numOfBytes * numOfElements);
        byte[][] literals = new byte[numOfElements][];
        for (int i = 0; i < numOfElements; i++) {
            literals[i] = Arrays.copyOfRange(temp, i * numOfBytes, (i + 1) * numOfBytes);
        }
        // a huffman tree has n literal nodes and n-1 internal nodes
        // every internal node combines a literal node and a dummy node
        // except for the first dummy node that combines two literals, hence n - 1.
        int seqLength = 2 * literals.length - 1;
        StringBuilder sequence = new StringBuilder();
        var seqBitBuff = fileLoader.loadNBytesBinFile(1)[0];
        var masks = new byte[8];
        masks[0] = Byte.MIN_VALUE;
        for (int i = 1; i < 8; i++) {
            masks[i] = (byte) (masks[i] | (0x1 << (8 - i - 1)));
        }

        //  10111001
        //& 01000000
        //  10000000
        // 0111001

        for (int i= 0; i < seqLength; i++) {
            byte bit = (byte) (((seqBitBuff & masks[i % 8]) >>> (8 - i % 8 - 1)) & 0x1);
            if (bit == 1) {
                sequence.append("1");
            } else if (bit == 0) {
                sequence.append("0");
            } else {
                // for debugging
                System.out.println("Something went wrong while parsing sequence in header.");
                System.exit(-9);
            }
            if ((i + 1) % Byte.SIZE == 0) {
                seqBitBuff = fileLoader.loadNBytesBinFile(1)[0];
            }
        }
        //
        HuffmanTree tree = HuffmanCodeBuilder.reconstructHuffmanTree(literals, sequence.toString());
        byte[] buff;
        byte[] currElement;
        String byteCode;
        while ((buff = fileLoader.loadNBytesBinFile(1)) != null) {
            boolean fileIsFinished = fileLoader.getAvailableBytes() < 1;
            byteCode = bitMask.byteToString(buff[0]);
            for (int i = 0; i < (fileIsFinished ? 8-numOfDummyZeroBits : 8); i++) {
                assert tree != null;
                currElement = tree.decodeBitByBit(byteCode.charAt(i) == '1');
                if (currElement != null) {
                    for (byte b : currElement)
                        System.out.print((char) b);
                }
            }
        }
    }
}
