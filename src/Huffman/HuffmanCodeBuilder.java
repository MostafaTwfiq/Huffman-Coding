package Huffman;

import FilesHandler.FileLoader;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

public class HuffmanCodeBuilder {

    private class HuffmanNodeComparator implements Comparator<HuffmanNode> {

        @Override
        public int compare(HuffmanNode n1, HuffmanNode n2) {
            return n1.getCount() - n2.getCount();
        }
    }

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
        byte[] b;
        while ((b = fileLoader.loadNBytesBinFile(numOfBytes)) != null) {
            ByteArray byteArray = new ByteArray(b);
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
            queue.add(new HuffmanNode(entry.getKey().getData(), entry.getValue(),null, null, null));

        return queue;
    }

    public HuffmanTree constructHuffmanTree(int numOfBytes) throws IOException {
        countCharactersFromFile(numOfBytes);

        PriorityQueue<HuffmanNode> queue = constructPriorityQueueNodes();
        Vector<byte[]> sortedNodes = new Vector<>();
        HashMap<ByteArray, HuffmanNode> nodesMap = new HashMap<>();

        while (queue.size() > 1) {
            HuffmanNode n1 = queue.poll();
            HuffmanNode n2 = queue.poll();
            if (n1.getValue() != null) {
                nodesMap.put(new ByteArray(n1.getValue()), n1);
                sortedNodes.add(n1.getValue());
            }
            if (n2.getValue() != null) {
                nodesMap.put(new ByteArray(n2.getValue()), n2);
                sortedNodes.add(n2.getValue());
            }

            HuffmanNode newNode = new HuffmanNode(null,  n1.getCount() + n2.getCount(), null, n2, n1);
            n1.setParent(newNode);
            n2.setParent(newNode);
            queue.add(newNode);
        }

        return new HuffmanTree(queue.poll(), nodesMap, sortedNodes.toArray(byte[][]::new));
    }

    public void setFilePath(String filePath) {
        if (filePath == null)
            throw new IllegalArgumentException();

        this.filePath = filePath;
    }
}
