package Huffman;

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

    private HashMap<String, Integer> charCountMap;
    private String s;
    public HuffmanCodeBuilder(String s) {
        if (s == null)
            throw new IllegalArgumentException();

        charCountMap = new HashMap<>();
        this.s = s;
    }


    private void countStringCharacters() {
        /*for (Character c : s.toCharArray()) {
            if (charCountMap.containsKey(c))
                charCountMap.replace(c, charCountMap.get(c) + 1);
            else
                charCountMap.put(c, 1);
        }*/
    }

    private PriorityQueue<HuffmanNode> constructPriorityQueueNodes() {
        Comparator<HuffmanNode> comparator = new HuffmanNodeComparator();
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(charCountMap.size(), comparator);
        for (var entry : charCountMap.entrySet())
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));

        return queue;
    }

    public HuffmanTree constructHuffmanTree(String filePath, int numOfBytes) {

        countStringCharacters();
        PriorityQueue<HuffmanNode> queue = constructPriorityQueueNodes();
        while (queue.size() > 1) {
            HuffmanNode n1 = queue.poll();
            HuffmanNode n2 = queue.poll();
            HuffmanNode newNode = new HuffmanNode(null, n1.getCount() + n2.getCount(), n2, n1);
            queue.add(newNode);
        }

        return new HuffmanTree(queue.poll());
    }

    public void setString(String s) {
        if (s == null)
            throw new IllegalArgumentException();

        this.s = s;
    }
}
