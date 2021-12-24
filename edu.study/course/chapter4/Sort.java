package course.chapter4;

public class Sort {
    private static int quickSelect(int[] a,int k, int from,int to){
      if(to<=from+1){
        return a[from];
      }
      int j=partition(a,from,to);
      if(j==k){
        return a[j];
      }

      if(j<k){
        return quickSelect(a,k,j+1,to);
      }else {
        return quickSelect(a,k,from,j);
      }

    }
    private static void quicksort(int[] a, int from,int to){
      if(to<=from+1){
        return;
      }

      int j = partition(a,from,to);
      quicksort(a,from,j);
      quicksort(a,j+1,to);
    }


    private static int partition(int[] a, int from,int to){
      int pivot = a[from];
      int i = from;
      int j=to;
      while (true){
        while (a[++i]<=pivot){
          if(i==to-1){
            break;
          }
        }

        while (a[--j]>pivot){
          if(j==from){
            break;
          }
        }

        if(j<=i){
          break;
        }

        swap(a,i,j);
      }

      swap(a,from,j);
      return j;
    }

    public static void swap(int[] a, int i,int j){
      int temp = a[i];
      a[i] = a[j];
      a[j] = temp;
    }

    public static void show(int[] a){
      for(int num:a){
        System.out.println(num);
      }
    }

  public static void main(String[] args) {
    int[] a = new int[]{2,3,4,7,7,2,3,5,6,10,4,4,67,90,3,3,5};
    quicksort(a,0,a.length);
    show(a);


  }
}
