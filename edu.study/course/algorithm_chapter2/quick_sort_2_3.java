package course.algorithm_chapter2;

public class quick_sort_2_3 {
    public static void quickSort(Comparable[] a){
        quickSort(a,0,a.length-1);
    }

    public static void quickSort(Comparable[] a, int low, int high){
        if(high<=low)return;

        int j= partition(a,low,high);
        quickSort(a,low,j);
        quickSort(a,j+1,high);
    }

    public static int partition(Comparable[] a,int low, int high){
        int i=low,j=high+1;
        Comparable v = a[low];
        while (true){
            while (a[++i].compareTo(v)<0){
                if(i==high){
                    break;
                }
            }

            while (v.compareTo(a[--j])<0){
                if(j==low){
                    break;
                }
            }

            if(i>j){
                break;
            }
            exach(a,i,j);
        }
        exach(a,low,j);
        return j;
    }

    public static void exach(Comparable[] a,int i,int j){

    }

}
