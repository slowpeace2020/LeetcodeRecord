package unlock;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class Test {

  int i = 0;
  public static void main(String args[])
  {
    int i = 1;
    i = change_i(i);
    System.out.println(i);

    Integer arr[] = {1, 2, 3};
   List<Integer> lists = (List<Integer>) Arrays.asList(arr);

    // creating Arrays of String type

    String a[]
        = new String[] { "A", "B", "C", "D" };

    // getting the list view of Array
    List<String> list = Arrays.asList(a);
  }
  public static int change_i(int i)
  {
    i = 2;
    i *= 2;
    return i;
  }

  public int uniqueNumber(int nums[]) {
    // write your code here
    int res = 0;
    for(int i:nums){
      res = res ^ i;
    }

    return res;
  }

}
