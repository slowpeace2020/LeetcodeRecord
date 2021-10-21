package course.algorithm_chapter1.data_abstraction1_2;

import algs4.src.main.java.edu.princeton.cs.algs4.Counter;

/**
 * exercise 1.2
 */
public class DataStructure {

    public static void exercise6(){
        String string1 = "hello";
        String string2 = string1;
        string1 = "world";
        System.out.println(string1);
        System.out.println(string2);
    }

    public static void exercise7(){
        String s = "ACTGACG";
        String t = "TGACGAC";
        System.out.println(s.concat(s));
        //s的长度和t相等，并且s拼接两次之后的字符串中包含， 那么t肯定满足circular rshift的条件
        if (s.length() == t.length() && s.concat(s).indexOf(t) >= 0) {
            System.out.println(s + " is the circular rotation of " + t);
        } else {
            System.out.println(s + " is not the circular rotation of " + t);
        }
    }

    /**
     * exercise8 swap array, by copy references, it is not necessary to copy millions of elements
     */


    /**
     * 二分查找变成递归的形式，每次调用计数
     */
    public static void exercise9(){
        rank(9,new int[]{2,4,6,8,11,12,18},new Counter("binarysearch count"));
    }

    public static int rank(int key, int[] a, Counter counter) {
        return rank(key, a, 0, a.length - 1, counter);
    }

    public static int rank(int key, int[] a, int lo, int hi, Counter counter) {
        if (lo > hi) {
            return -1;
        }
        counter.increment();
        int mid = lo + (hi - lo) / 2;
        if (key < a[mid]) {
            return rank(key, a, lo, mid - 1, counter);
        } else if (key > a[mid]) {
            return rank(key, a, mid + 1, hi, counter);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
            exercise7();
    }



}
