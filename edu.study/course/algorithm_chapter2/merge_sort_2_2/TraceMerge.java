package course.algorithm_chapter2.merge_sort_2_2;

/**
 * top-down
 */
public class TraceMerge {
    private static void sort(String[] s, int low, int high){
        while (low<high){
            int mid = low+(high-low)/2;
            sort(s,low,mid);
            sort(s,mid+1,high);
            merge(s,low,mid,high);
        }
    }

    private static void merge(String[] s, int low, int mid, int high){
            String[] auxiliary = new String[s.length];
            for(int index = low;index<=high;index++){
                auxiliary[index] = s[index];
            }
            int i=low;
            int j=mid+1;
            int k=low;
            while (i<=mid&&j<=high){
                if(less(s[i],s[j])){
                    s[k++] = auxiliary[i++];
                }else {
                    s[k++] = auxiliary[j++];
                }
            }
    }

    public static boolean less(String o1, String o2){
        return o1.compareTo(o2)<0 ;
    }
}
