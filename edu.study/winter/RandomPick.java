package winter;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomPick {
    int M;
    Random r;
    Map<Integer, Integer> map;

    public RandomPick(int n, int[] blacklist) {
        map = new HashMap<>();
        for (int b : blacklist) {
            map.put(b, -1);
        }
        M = n - map.size();
        for (int b : blacklist) {
            if (b < M) {
                while (map.containsKey(n - 1)) {
                    n--;
                }
                map.put(b, n - 1);
                n--;
            }
        }

        r = new Random();
    }

    public int pick() {
        int p = r.nextInt(M);
        if(map.containsKey(p)){
            return map.get(p);
        }

        return p;
    }
}
