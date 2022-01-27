package winter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileSystem {
    private class FileNode {
        Map<String, FileNode> children;
        String content;
        boolean isFile;

        public FileNode(boolean isFile) {
            this.isFile = isFile;
            children = new HashMap<>();
            content = "";
        }
    }

    FileNode root;

    public FileSystem() {
        root = new FileNode(false);
    }

    public void mkdir(String path) {
        String[] dirs = path.split("/");
        FileNode cur = root;
        for (int i = 0; i < dirs.length; i++) {
            if (!cur.children.containsKey(dirs[i])) {
                cur.children.putIfAbsent(dirs[i], new FileNode(false));
            }

            cur = cur.children.get(dirs[i]);
        }
    }

    public void addContentToFile(String filePath, String content) {
        String[] dirs = filePath.split("/");
        FileNode cur = root;
        for (int i = 0; i < dirs.length; i++) {
            if (!cur.children.containsKey(dirs[i])) {
                cur.children.putIfAbsent(dirs[i], new FileNode(false));
            }

            cur = cur.children.get(dirs[i]);
        }

        cur.content += content;
        cur.isFile = true;
    }


    public String readContentFromFile(String filePath) {
        String[] dirs = filePath.split("/");
        FileNode cur = root;
        for (int i = 0; i < dirs.length; i++) {
            if (!cur.children.containsKey(dirs[i])) {
                cur.children.putIfAbsent(dirs[i], new FileNode(false));
            }

            cur = cur.children.get(dirs[i]);
        }

        return cur.content;
    }

    public List<String> ls(String path) {
        List<String> res = new ArrayList<>();
        String[] dirs = path.split("/");
        FileNode cur = root;
        for (int i = 0; i < dirs.length; i++) {
            if (!cur.children.containsKey(dirs[i])) {
                cur.children.putIfAbsent(dirs[i], new FileNode(false));
            }

            cur = cur.children.get(dirs[i]);
        }


        if (cur.isFile) {
            res.add(cur.content);
        } else {
            for (String child : cur.children.keySet()) {
                res.add(child);
            }
        }

        return res;
    }


}
