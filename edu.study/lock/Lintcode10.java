package lock;

import java.util.ArrayList;
import java.util.List;

public class Lintcode10 {
  public List<String> stringPermutation2(String str) {
    List<String> res = new ArrayList<>();
    if(str==null|str.equals("")){
      return res;
    }
       char[] strs = str.toCharArray();

    dfsStringPermutation2(res,strs,new boolean[strs.length],new StringBuilder());
    return res;
  }

  public void dfsStringPermutation2(List<String> res,char[] strs, boolean[] visited,StringBuilder sb){
    if(sb.length()==strs.length){
      res.add(new String(sb.toString()));
      return;
    }

    for(int i=0;i<strs.length;i++){
      if(visited[i]){
        continue;
      }
      if(i>0){
        boolean flag = false;
        for(int j=0;j<i;j++){
          if(strs[j]==strs[i]&&!visited[j]){
            flag = true;
            break;
          }
        }
        if(flag){
          continue;
        }
      }

      visited[i] = true;
      sb.append(strs[i]);
      dfsStringPermutation2(res,strs,visited,sb);
      visited[i] = false;
      sb.setLength(sb.length()-1);
    }

  }

  public static void main(String[] args) {
    Lintcode10 test = new Lintcode10();
    test.stringPermutation2("abb");
    test.stringPermutation2("aabb");
  }


}
