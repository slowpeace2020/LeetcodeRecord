package winter;

import java.util.*;

public class AutocompleteSystem {
    TrieNode root, curr;
    StringBuffer sb;

    public AutocompleteSystem(String[] sentences, int[] times) {
        if (sentences == null || times == null || sentences.length != times.length) {
            return;
        }

        reset();

        root = new TrieNode();

        for (int i = 0; i < times.length; i++) {
            insert(sentences[i], times[i]);
        }
    }

    public List<String> input(char c) {
        List<String> result = new ArrayList<>();
        if (curr == null) curr = root;
        if (c == '#') { // save sentence and reset state
            insert(sb.toString(), 1);
            reset();
            return result;
        }

        // Update global variable (curr TrieNode and string buffer); or append new character if not exist.
        sb.append(c);
        curr.children.putIfAbsent(c, new TrieNode());
        curr = curr.children.get(c);

        // MinHeap to find top 3.
        result.addAll(findTopK(curr, 3));

        return result;
    }

    private List<String> findTopK(TrieNode node, int k) {
        List<String> result = new ArrayList<>();
        if (node.freq.isEmpty()) {
            return result;
        }

        PriorityQueue<Pair> queue = new PriorityQueue<>(
                (a, b) -> a.count == b.count ? b.s.compareTo(a.s) : a.count - b.count);

        for (Map.Entry<String, Integer> entry : node.freq.entrySet()) {
            if (queue.size() < k || entry.getValue() >= queue.peek().count) {
                queue.offer(new Pair(entry.getKey(), entry.getValue()));
            }
            if (queue.size() > k) queue.poll();
        }

        while (!queue.isEmpty()) {
            result.add(0, queue.poll().s);
        }

        return result;
    }

    private void reset() {
        curr = null;
        sb = new StringBuffer();
    }

    private void insert(String sentence, int count) {
        if (sentence == null || sentence.length() == 0) {
            return;
        }

        TrieNode node = root;
        for (char c : sentence.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
            node.freq.put(sentence, node.freq.getOrDefault(sentence, 0) + count);
        }
        node.isEnd = true; // can set word to node as well, if needed
    }
}

class TrieNode {
    public boolean isEnd;
    public Map<String, Integer> freq;
    public Map<Character, TrieNode> children; // Map is more applicable to all chars, not limited to 256 ASCII

    public TrieNode() {
        this.freq = new HashMap<>();
        this.children = new HashMap<>();
    }
}

class Pair {
    String s;
    int count;

    public Pair(String s, int count) {
        this.s = s;
        this.count = count;
    }
}

