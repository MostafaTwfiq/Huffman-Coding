package Huffman;

import FilesHandler.FileLoader;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class HuffmanCodeBuilder {

    private class HuffmanNodeComparator implements Comparator<HuffmanNode> {

        @Override
        public int compare(HuffmanNode n1, HuffmanNode n2) {
            return n1.getCount() - n2.getCount();
        }
    }

    private HashMap<ByteArray, Integer> charCountMap;

    String filePath;
    public HuffmanCodeBuilder(String filePath) {
        if (filePath == null)
            throw new IllegalArgumentException();

        charCountMap = new HashMap<>();
        this.filePath = filePath;
    }

// 450*1024*1024/(2*10^6)
    private void countCharactersFromFile(int numOfBytes) throws Exception{
        FileLoader fileLoader = new FileLoader(filePath);
        byte[] b;
        int count = 0;
        while ((b = fileLoader.loadNBytesBinFile(numOfBytes)) != null) {
            if (count % 1000000 == 0)
                System.out.println("Hehe");

            ByteArray byteArray = new ByteArray(b);
            if (charCountMap.containsKey(byteArray))
                charCountMap.replace(byteArray, charCountMap.get(byteArray) + 1);
            else
                charCountMap.put(byteArray, 1);

            count++;
        }
    }

    private PriorityQueue<HuffmanNode> constructPriorityQueueNodes() {
        Comparator<HuffmanNode> comparator = new HuffmanNodeComparator();
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(charCountMap.size(), comparator);
        for (var entry : charCountMap.entrySet())
            queue.add(new HuffmanNode(entry.getKey().getData(), entry.getValue(),null, null, null));

        return queue;
    }

    public HuffmanTree constructHuffmanTree(int numOfBytes) throws Exception {
        countCharactersFromFile(numOfBytes);

        PriorityQueue<HuffmanNode> queue = constructPriorityQueueNodes();
        while (queue.size() > 1) {
            HuffmanNode n1 = queue.poll();
            HuffmanNode n2 = queue.poll();
            HuffmanNode newNode = new HuffmanNode(null,  n1.getCount() + n2.getCount(), null, n2, n1);
            n1.setParent(newNode);
            n2.setParent(newNode);
            queue.add(newNode);
        }

        return new HuffmanTree(queue.poll());
    }

    public void setFilePath(String filePath) {
        if (filePath == null)
            throw new IllegalArgumentException();

        this.filePath = filePath;
    }
}
