package course.algorithm_chapter2.elementary_sort_2_1;

public class ShellSort {
    public static void main(String[] args) {

    }


    public static void sort(Comparable[] a){
        int N = a.length;
        int h = 1;
        while(h<N/3){
            h = 3*h+1;
        }

        while (h>=1){
            for(int i=h;i<N;i++){
                for(int j=i;j>=h&&a[j].compareTo(a[j-h])>0;j-=h){
                    //exchange(a,j,j-h)
                }
            }
             h = h/3;
        }
    }
}
