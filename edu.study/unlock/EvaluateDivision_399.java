package unlock;

import java.util.*;

public class EvaluateDivision_399 {

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String,List<Pair>> map = new HashMap<>();
        for(int i=0;i<values.length;i++){
            List<String> list = equations.get(i);
            double value = values[i];
            String source = list.get(0);
            String target = list.get(1);
            List<Pair> pairs = map.getOrDefault(source,new ArrayList<>());
            pairs.add(new Pair(target,value));
            map.put(source,pairs);

            List<Pair> pairt = map.getOrDefault(target,new ArrayList<>());
            pairt.add(new Pair(source,1.0/value));
            map.put(target,pairt);
        }
        double[] res = new double[queries.size()];
        Arrays.fill(res,-1.0);
        for(int i=0;i<queries.size();i++){
            String source = queries.get(i).get(0);
            String target = queries.get(i).get(1);
            if(!map.containsKey(source)){
                continue;
            }
            if(source.equals(target)&&map.containsKey(source)){
                res[i] = 1.0;
                continue;
            }
            Set<String> visited = new HashSet<>();
            Queue<Pair> queue = new LinkedList<>();
            queue.add(new Pair(source,1.0));
            while(!queue.isEmpty()){
                Pair cur = queue.poll();
                String string = cur.charactor;
                if(cur.charactor.equals(target)){
                    res[i] = cur.value;
                    break;
                }

                if(visited.contains(string)){
                    continue;
                }
                visited.add(string);

                if(map.containsKey(string)){
                    double value = cur.value;
                    List<Pair> pairs = map.get(string);
                    for(Pair next:pairs){
                        if(visited.contains(next.charactor)){
                            continue;
                        }
                        queue.add(new Pair(next.charactor,value*next.value));
                    }

                }
            }


        }

        return res;

    }

    class Pair{
        String charactor;
        double value;
        public Pair(String charactor,double value){
            this.charactor = charactor;
            this.value = value;
        }
    }

    public static void main(String[] args) {

        List<List<String>> equations = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        List<String> list2 = new ArrayList<>();
        list2.add("b");
        list2.add("c");
        equations.add(list1);
        equations.add(list2);
        double[] values = new double[]{2.0,3.0};
        List<List<String>> queries = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        list3.add("a");
        list3.add("c");
        List<String> list4 = new ArrayList<>();
        list4.add("b");
        list4.add("a");
        queries.add(list3);
        queries.add(list4);


        EvaluateDivision_399 test = new EvaluateDivision_399();
        test.calcEquation(equations,values,queries);



    }
}
