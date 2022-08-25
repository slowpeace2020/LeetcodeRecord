package august;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * The Main class implements an application that reads lines from the standard input
 * and prints them to the standard output.
 */
public class Main1 {
    /**
     * Iterate through each line of input.
     */
    public static void main(String[] args) throws IOException {

        System.out.println(getExchange("USD,CAD,1.3;USD,GBP,0.71;USD,JPY,109;GBP,JPY,155","USD","JPY"));
    }

    public static double getExchange(String text,String from,String target){
        //make graph;
        Map<String,Map<String,Double>> map = new HashMap<>();
        String[] records = text.split(";");
        for(String record:records){
            String[] items =record.split(",");
            String source = items[0];
            String to = items[1];
            double rate = Double.parseDouble(items[2]);
            Map<String,Double> relation = map.getOrDefault(source,new HashMap<>());
            relation.put(to,rate);
            map.put(source,relation);
        }

        String source = from;
        String to = target;
        Map<String,Double> visited = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>((a,b)->(Double.compare(visited.getOrDefault(b,0.0),visited.getOrDefault(a,0.0))));
        visited.put(source,1.0);
        queue.add(source);
        while (!queue.isEmpty()){
            String cur = queue.poll();
            Map<String,Double> nexts = map.getOrDefault(cur,new HashMap<>());
            for(String next:nexts.keySet()){
                double rate = visited.get(cur)*nexts.get(next);
                if(rate>visited.getOrDefault(next,0.0)){
                    visited.put(next,rate);
                    queue.add(next);
                }
            }
        }

        return visited.getOrDefault(target,-1.0);
    }

}


