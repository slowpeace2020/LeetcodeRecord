package winter;

import java.util.*;

public class Test {

    class Trie{
        boolean isFolder;
        Map<String,Trie> child;
        public Trie(){
            this.child = new HashMap<>();
        }
    }
    public List<String> removeSubfolders(String[] folder) {
        Arrays.sort(folder);

        Trie root = new Trie();
        List<String> res =new ArrayList<>();
        for(String fold:folder){
            Trie cur = root;
            boolean in = true;

            if(fold.equals("/")){
                continue;
            }
            String[] books = fold.split("/");
            for(String book:books){
                if(cur.child.containsKey(book)){
                    if(cur.child.get(book).isFolder){
                        in = false;
                        break;
                    }
                }else{
                    cur.child.put(book,new Trie());
                }
                cur = cur.child.get(book);
            }
            if(in){
                res.add(fold);
                cur.isFolder = true;
            }

        }

        return res;

    }

    public static void main(String[] args) {
        String[] folders = new String[]{"/a","/a/b","/c/d","/c/d/e","/c/f"};
        Test test = new Test();
        test.removeSubfolders(folders);
    }
}
