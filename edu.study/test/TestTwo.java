package test;

//import static edu.neu.coe.info6205.sort.par.Main.writeToCsv;


import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/**
 * This code has been fleshed out by Ziyao Qiao. Thanks very much.
 * TODO tidy it up a bit.
 */
public class TestTwo {
    private static final int CHUNK = 250_000;
    private static final int MAX = 40000000;
//    private static final int NUM_PROCS = Runtime.getRuntime().availableProcessors() * 2;
    private static final int NUM_PROCS = 1;
    private static ForkJoinPool FORKJOIN_POOL = new ForkJoinPool(NUM_PROCS);

    //    -N 2 -P 2
    public static void main(String[] args) {
//        processArgs(args);
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", NUM_PROCS + "");
        test();
    }

    public static void test() {
            for(int i = 10000;i<160000;i+=i){
                String res ="";
                for (int arrSize = 1; arrSize <= 256; arrSize += arrSize) {
                    int[] data = makeRandomArray(arrSize*CHUNK);

//                    ParSort.cutoff = suggestTargetSize(data.length);
                    ParSort.cutoff = i;
                    long elapsed = benchmark(data);
                    String content = FORKJOIN_POOL.getRunningThreadCount() + "," + (elapsed / 10);
                    System.out.println(content);
                    res+=elapsed+",";

//                    writeToCsv(content,"depththree");
                    System.out.println("Degree of parallelism: " + FORKJOIN_POOL.getParallelism() + ",cutoff:" + ParSort.cutoff +
                        ",size:" + arrSize + ",time:" + elapsed + "ms");
                }
                System.out.println(res.substring(0,res.length()-1));
                writeToCsv(res.substring(0,res.length()-1),"testresult"+(i/10000));
            }
    }



    private static int[] makeRandomArray(int arrSize) {
        Random rand = new Random(10000000);
        int[] data = new int[arrSize];
        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt();
        }
        return data;
    }

    private static long benchmark(int[] data) {
        long begin = System.currentTimeMillis();
        ParSort.sort(data, 0, data.length, FORKJOIN_POOL);
        long end = System.currentTimeMillis();
        long elapsed = end - begin;
        return elapsed;
    }

    private static int suggestTargetSize(int sizeEstimate) {

//        int min = 1 << 13;
//        int n = sizeEstimate, p = FORKJOIN_POOL.getParallelism(), g;
//        return ((g = n / (p << 2)) <= min) ? min : g;
//        int p;
//        return (p = sizeEstimate / (FORKJOIN_POOL.getParallelism() << 3))
//                <= 0 ? 0 : p;
//        return sizeEstimate / Runtime.getRuntime().availableProcessors();
        return 10000;
//        return 40000;
    }

    public static void writeToCsv(String content, String fileName) {
        try {
            FileOutputStream fis = new FileOutputStream("./src/" + fileName + ".csv", true);
            OutputStreamWriter isr = new OutputStreamWriter(fis);
            BufferedWriter bw = new BufferedWriter(isr);
            bw.write(content + "\n");
            bw.flush();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long sortByDifferentParallelism(int threadNums, int cutoff) {
        ParSort.cutoff = cutoff;
        Random random = new Random();
        System.out.println(threadNums);
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", threadNums + "");
        System.out.println("Degree of parallelism: " + FORKJOIN_POOL.getParallelism());
        int[] array = new int[2000000];
        // for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
        long time;
        long startTime = System.currentTimeMillis();
        for (int t = 0; t < 10; t++) {
            for (int i = 0; i < array.length; i++) array[i] = random.nextInt(10000000);
//            ParSort.sort(array, 0, array.length);
        }
//        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "2");
        long endTime = System.currentTimeMillis();
        time = (endTime - startTime) / 10;
        String content = FORKJOIN_POOL.getParallelism() + "," + time;
        return time;
    }

    private static void processArgs(String[] args) {
        String[] xs = args;
        while (xs.length > 0)
            if (xs[0].startsWith("-")) xs = processArg(xs);
    }

    private static String[] processArg(String[] xs) {
        String[] result = new String[xs.length - 2];
        System.arraycopy(xs, 2, result, 0, xs.length - 2);
        processCommand(xs[0], xs[1]);
        return result;
    }

    private static void processCommand(String x, String y) {
        if (x.equalsIgnoreCase("-N")) setConfig(x, Integer.parseInt(y));
        else
            // TODO sort this out
            if (x.equalsIgnoreCase("-P")) //noinspection ResultOfMethodCallIgnored
//                FORKJOIN_POOL.getParallelism();
                System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", y);
    }

    private static void setConfig(String x, int i) {
        configuration.put(x, i);
    }

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private static final Map<String, Integer> configuration = new HashMap<>();


}
