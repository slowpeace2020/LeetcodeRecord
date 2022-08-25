package july;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Third {
    public static String processInputs(List<String> inputs) {
        // Write your code here
        Map<String, Integer> serverToClient = new HashMap<>();
        Map<String, Map<Integer, String>> idMap = new HashMap<>();
        for (String str : inputs) {
            String[] record = str.split(" ");
            if (record[0].equals("OPEN")) {
                String link = record[1] + "_" + record[2];
                serverToClient.put(link, serverToClient.getOrDefault(link, 0) + 128);
                Map<Integer, String> currentIdMap = idMap.getOrDefault(record[1], new HashMap<>());
                Integer id = currentIdMap.size() + 1;
                currentIdMap.put(id, record[2]);
                idMap.put(record[1], currentIdMap);
            } else if (record[0].equals("READ")) {
                Integer currentId = Integer.valueOf(record[2]);
                Map<Integer, String> currentIdMap = idMap.get(record[1]);
                String client = currentIdMap.get(currentId);
                String link = record[1] + "_" + client;
                serverToClient.put(link, serverToClient.getOrDefault(link, 0) + Integer.valueOf(record[3]));

            } else if (record[0].equals("WRITE")) {
                Integer currentId = Integer.valueOf(record[2]);
                Map<Integer, String> currentIdMap = idMap.get(record[1]);
                String client = currentIdMap.get(currentId);
                String link = record[1] + "_" + client;
                serverToClient.put(link, serverToClient.getOrDefault(link, 0) + Integer.valueOf(record[3]));
            }
        }

        Map<String, String> serverClient = new HashMap<>();
        Map<String, Integer> serverTransfer = new HashMap<>();
        for (String key : serverToClient.keySet()) {
            String server = key.split("_")[0];
            String cleint = key.split("_")[1];
            Integer num = serverToClient.get(key);
            if (num > serverTransfer.getOrDefault(server, 0)) {
                serverClient.put(server, cleint);
                serverTransfer.put(server, num);
            }
        }
        String res = "";
        for (String key : serverClient.keySet()) {
            res += key + " " + serverToClient.get(key) + "\r\n";
        }
        return res;
    }

    public int maximumSumI(int[] a) {
        int n = a.length;
        int[] maxEndHere = new int[n];
        int[] maxStartHere = new int[n];
        int max = a[0];
        maxEndHere[0] = a[0];

        for(int i=1;i<n;i++){
            maxEndHere[i] = Math.max(a[i],maxEndHere[i-1]+a[i]);
            max = Math.max(max,maxEndHere[i]);
        }

        maxStartHere[n-1] = a[n-1];
        for(int i=n-2;i>=0;i--){
            maxStartHere[i] =  Math.max(a[i],maxStartHere[i+1]+a[i]);
        }

        for(int i=1;i<n-1;i++){
            max = Math.max(max,maxEndHere[i-1] + maxStartHere[i+1]);
        }

        return max;
    }

    public int maximumSum(int[] arr) {
        int n = arr.length;
        int oneDelete = 0;
        int noDelete = arr[0];
        int max = arr[0];
        for(int i=1;i<n;i++){
            oneDelete = Math.max(noDelete,oneDelete+arr[i]);
            noDelete = Math.max(noDelete+arr[i],arr[i]);
            max = Math.max(max,Math.max(oneDelete,noDelete));
        }

        return max;
    }

        public static void main(String[] args) {

        String[] list = new String[]{"OPEN s1 c1","READ s1 1 256","WRITE s1 1 512",
                "OPEN s1 c1","OPEN s1 c2","OPEN s2 c2","WRITE s1 3 1025","READ s1 2 64"};
        processInputs(Arrays.asList(list));
    }
}
