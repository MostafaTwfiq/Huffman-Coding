package Huffman;

import FilesHandler.FileLoader;
import FilesHandler.FileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class FileDecompressor {

    private String filePath;

    public FileDecompressor(String filePath) {
        this.filePath = filePath;
    }

    public void decompressFile() throws IOException {
        BitMask bitMask = new BitMask();
        FileLoader fileLoader = new FileLoader(filePath);

        File file = new File(filePath);
        String decompressedFilePath = file.getParent() + "\\" + "extracted." + file.getName().substring(0, file.getName().lastIndexOf(".hc"));

        File f = new File(decompressedFilePath);
        f.createNewFile();
        FileWriter writer = new FileWriter(decompressedFilePath);
        byte[] temp = fileLoader.loadNBytesBinFile(1);
        short numOfDummyZeroBits = temp[0];

        temp = fileLoader.loadNBytesBinFile(4);
        int numOfBytes = ByteBuffer.wrap(temp).getInt();

        temp = fileLoader.loadNBytesBinFile(4);
        int numOfElements = ByteBuffer.wrap(temp).getInt();

        temp = fileLoader.loadNBytesBinFile(numOfBytes * numOfElements);
        byte[][] literals = new byte[numOfElements][];
        for (int i = 0; i < numOfElements; i++) {
            literals[i] = Arrays.copyOfRange(temp, i * numOfBytes, (i + 1) * numOfBytes);
        }


        int headerSeqNumOfBits = 2 * numOfElements - 1;
        byte[] headerSequence = fileLoader.loadNBytesBinFile((int) Math.ceil((headerSeqNumOfBits)/8.0));

        HuffmanTree tree = HuffmanCodeBuilder.reconstructHuffmanTree(literals, headerSequence, headerSeqNumOfBits);
        byte[] compressedFileBytes = fileLoader.loadNBytesBinFile(fileLoader.getAvailableBytes());
        byte[] currElement;
        String byteCode;

        for (int i = 0; i < compressedFileBytes.length; i++) {
            boolean fileIsFinished = ((i == compressedFileBytes.length - 1) && numOfDummyZeroBits != 8);
            byteCode = bitMask.byteToString(compressedFileBytes[i]);
            for (int j = 0; j < (fileIsFinished ? 8 - numOfDummyZeroBits : 8); j++) {
                currElement = tree.decodeBitByBit(byteCode.charAt(j) == '1');
                if (currElement != null)
                    writer.writeBytesToBuff(currElement);

            }
        }
        writer.writeBuffToDisk();

    }
}
