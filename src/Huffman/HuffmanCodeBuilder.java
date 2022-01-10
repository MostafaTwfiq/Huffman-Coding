package Huffman;

import FilesHandler.FileLoader;
import main.Main;
import java.io.IOException;
import java.util.*;

public class HuffmanCodeBuilder {

    private HashMap<ByteArray, Integer> charCountMap;
    private String filePath;

    public HuffmanCodeBuilder(String filePath) {
        if (filePath == null)
            throw new IllegalArgumentException();

        charCountMap = new HashMap<>();
        this.filePath = filePath;
    }


    private void countCharactersFromFile(int numOfBytes) throws IOException{
        FileLoader fileLoader = new FileLoader(filePath);
        int fileSize = fileLoader.getFileSize();
        byte[] b = fileLoader.loadNBytesBinFile(fileSize);
        for (int i = 0; i < fileSize; i += numOfBytes) {
            ByteArray byteArray = new ByteArray(Arrays.copyOfRange(b, i, i + numOfBytes));
            if (charCountMap.containsKey(byteArray))
                charCountMap.replace(byteArray, charCountMap.get(byteArray) + 1);
            else
                charCountMap.put(byteArray, 1);
        }
    }

    private PriorityQueue<HuffmanNode> constructPriorityQueueNodes() {
        Comparator<HuffmanNode> comparator = new HuffmanNodeComparator();
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(charCountMap.size(), comparator);
        for (var entry : charCountMap.entrySet())
            queue.add(new HuffmanNode(entry.getKey().getData(),
                    entry.getValue(),null, null, null));

        return queue;
    }

    public HuffmanTree constructHuffmanTree(int numOfBytes) throws IOException {
        countCharactersFromFile(numOfBytes);

        PriorityQueue<HuffmanNode> queue = constructPriorityQueueNodes();
        HashMap<ByteArray, HuffmanNode> nodesMap = new HashMap<>();

        while (queue.size() > 1) {
            HuffmanNode n1 = queue.poll();
            HuffmanNode n2 = queue.poll();
            if (n1.getValue() != null)
                nodesMap.put(new ByteArray(n1.getValue()), n1);
            if (n2.getValue() != null)
                nodesMap.put(new ByteArray(n2.getValue()), n2);


            HuffmanNode newNode = new HuffmanNode(null, n1.getCount() + n2.getCount(), null, n2, n1);
            n1.setParent(newNode);
            n2.setParent(newNode);
            queue.add(newNode);
        }

        return new HuffmanTree(queue.poll(), nodesMap);
    }

    public static HuffmanTree reconstructHuffmanTree(byte[][] sortedBytes, byte[] steps, int numOfSteps) {
        BitMask bitMask = new BitMask();
        Stack<HuffmanNode> nodesStack = new Stack<>();
        HashMap<ByteArray, HuffmanNode> nodesMap = new HashMap<>();

        for (int i = 0, byteCount = 0, sortedBytesCount = 0; i < numOfSteps; ++i, byteCount += (i%8 == 0 ? 1 : 0) ) {
            if (bitMask.getByteBit(steps[byteCount], (byte) (i % 8))) {
                nodesStack.push(new HuffmanNode(sortedBytes[sortedBytesCount++], null, null, null));
            } else {
                HuffmanNode n2 = nodesStack.pop();
                HuffmanNode n1 = nodesStack.pop();
                if (n1.getValue() != null)
                    nodesMap.put(new ByteArray(n1.getValue()), n1);

                if (n2.getValue() != null)
                    nodesMap.put(new ByteArray(n2.getValue()), n2);

                HuffmanNode newNode = new HuffmanNode(null, null, n2, n1);
                n1.setParent(newNode);
                n2.setParent(newNode);
                nodesStack.push(newNode);
            }
        }

        return new HuffmanTree(nodesStack.pop(), nodesMap);
    }

    public void setFilePath(String filePath) {
        if (filePath == null)
            throw new IllegalArgumentException();

        this.filePath = filePath;
    }
}
