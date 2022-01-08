package unlock;

import java.util.*;

public class FileSystem {
    //路径和此路径包含的所有文件和文件夹
    Map<String, Set<String>> dirs;
    Map<String,String> files;

    public FileSystem(){
        dirs.put("/",new HashSet<>());
    }

    public List<String> ls(String path){
        List<String> res= new ArrayList<>();
        if(files.containsKey(path)){//path 是文件
            //输出文件名
            String name = path.substring(path.lastIndexOf("/"));
            res.add(name);
        }else {
            //得到该文件夹下的子文件
            Set<String> childs = dirs.get(path);
            res.addAll(childs);
        }
        return res;
    }

    public void mkdir(String path){
        String dir ="";
        String[] part = path.split("/");
        for(String parentDir:part){
            if(dir.equals("")){
                dir+="/";
            }

            dirs.getOrDefault(dir,new HashSet<>()).add(parentDir);
            if(dir.length()>1){
                dir+="/";
            }
            dir+=parentDir;
        }
    }

    public void addContentToFile(String filePath,String content){
        int idx = filePath.lastIndexOf("/");
        String dir = filePath.substring(0,idx);
        String file = filePath.substring(idx+1);
        if(dir==""){
            dir="/";
        }
        if(!dirs.containsKey(dir)){
            mkdir(dir);
        }
        dirs.getOrDefault(dir,new HashSet<>()).add(file);
        content = files.getOrDefault(file,"")+content;
        files.put(file,content);
    }

    public String readContentFromFile(String filePath){
        return files.get(filePath);
    }

}
