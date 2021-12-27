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

    private HashMap<Character, Integer> charCountMap;
    private String s;
    public HuffmanCodeBuilder(String s) {
        charCountMap = new HashMap<>();
        this.s = s;
    }


    private void countStringCharacters() {
        for (Character c : s.toCharArray()) {
            if (charCountMap.containsKey(c))
                charCountMap.replace(c, charCountMap.get(c) + 1);
            else
                charCountMap.put(c, 0);
        }
    }

    private PriorityQueue<HuffmanNode> constructPriorityQueueNodes() {
        Comparator<HuffmanNode> comparator = new HuffmanNodeComparator();
        PriorityQueue<HuffmanNode> queue = new PriorityQueue<HuffmanNode>(charCountMap.size(), comparator);
        for (var entry : charCountMap.entrySet())
            queue.add(new HuffmanNode(entry.getKey(), entry.getValue(), null, null));

        return queue;
    }

    private HuffmanTree constructHuffmanTree() {
        PriorityQueue<HuffmanNode> queue = constructPriorityQueueNodes();

    }

}
