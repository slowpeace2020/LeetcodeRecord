package companyOA.citadel;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Friends {
    int[] root;
    int[] size;
    int count;
    public void group(String[] queryType,int[] student1,int[] student2){
            int n = queryType.length;
            root = new int[n];
            size = new int[n];
            count = n;
            Arrays.fill(size,1);
            for(int i=0;i<n;i++){
                root[i] = i;
            }

            for(int i=0;i<n;i++){
                String query = queryType[i];
                if(query.equals("Friend")){
                    union(student1[i],student2[i]);
                }else{

                }
            }


    }

    //weighted quick union
    public void union(int a,int b){
            int parentA = findRoot(a);
            int parentB = findRoot(b);
            if(parentA==parentB){
                return;
            }
            if(size[parentA]>size[parentB]){
                root[parentB] = parentA;
                size[parentA]+=size[parentB];
            }else{
                root[parentA] = parentB;
                size[parentB]+=size[parentA];
            }

            count--;
    }

    //路径压缩
    public int findRoot(int a){
            while (root[a]!=a){
               root[a] = root[root[a]];
               a = root[a];
            }
            return a;
      }
}
