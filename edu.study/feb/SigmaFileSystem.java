package feb;

import java.util.*;

public class SigmaFileSystem {
        public SigmaFileSystem() {
            map = new TreeMap<>();
            relation = new TreeMap<>();
            root = new FileNode();
            root.parentId=-1;
            root.fileId=-1;
            root.isFolder = true;
        }

        class FileNode{
            String filename;
            String fileType;
            int fileId;
            int  parentId;
            boolean isFolder;
            boolean isDashboards;
            boolean isWorksheets;
            TreeMap<String,FileNode> child;
//            TreeMap<Integer,String> childMap;
            public FileNode(){
                child = new TreeMap<>();
//                childMap = new TreeMap<>();
            }
        }

        // Feel free to modify the parameter/return types of these functions
        // as you see fit. Please add comments to indicate your changes with a
        // brief explanation. We care more about your thought process than your
        // adherence to a rigid structure.

        int totalDashboards;
        int totalWorksheets;

        //allocate global fileId
        int totalId;

        //fileId matches FileNode
        Map<Integer,FileNode> map;

        //self-definition folderId matches system fileId
        Map<Integer,Integer> relation;

        //root dir of all files
        FileNode root;

        int getTotalDashboards() {
            // TODO: implement
            return this.totalDashboards;
        }

        int getTotalWorksheets() {
            // TODO: implement
            return this.totalWorksheets;
        }

        void addNewFile(String fileName, String fileType, int folderId) {
            // TODO: implement
            Integer fileId = relation.getOrDefault(folderId,-1);
            if(fileId==-1&&map.containsKey(folderId)){
                fileId = folderId;
            }
            //if parent dir not exist, create it
            if(!map.containsKey(fileId)){
                FileNode fileNode = new FileNode();
                fileNode.parentId= -1;
                fileNode.fileId = getTotalId();
                fileNode.isFolder = true;
                fileNode.filename = fileName;
                fileNode.fileType = "folder";
                map.put(totalId,fileNode);
                relation.put(folderId,totalId);
                root.child.put(fileName,fileNode);
                //itself is a folder
                return;
            }

            FileNode fileNode = map.get(fileId);
            if(!fileNode.isFolder){
                System.err.println("folderId is invalid, it's a file not a folder");
                return;
            }
            TreeMap<String,FileNode> child = fileNode.child;
            if(child.containsKey(fileName)){//folder contains file
                child.get(fileName).fileType = fileType;
            }else {//folder didn't contains file, create it
                FileNode node = new FileNode();
                node.parentId= folderId;
                node.fileType = fileType;
                node.filename = fileName;
                node.fileId = getTotalId();//global unique id
                child.put(fileName,node);
//                fileNode.childMap.put(totalId,fileName);
                map.put(node.fileId,node);
//                relation.put(totalId,totalId);
                if(fileType.equals("dashboard")){
                    node.isDashboards = true;
                    totalDashboards++;
                }else if(fileType.equals("worksheet")){
                    node.isWorksheets =true;
                    totalWorksheets++;
                }else {
                    node.isFolder=true;
                }
            }

            return;
        }
        int getTotalId(){
            while (map.containsKey(totalId)){
                totalId++;
            }

            return totalId;
        }
        int getFileId(String fileName, int folderId) {
            // TODO: implement
            Integer fileId = relation.getOrDefault(folderId,-1);
            if(fileId==-1&&map.containsKey(folderId)){
                fileId = folderId;
            }
            FileNode folder = map.getOrDefault(fileId,null);
            if(folder!=null){
                if(fileName.equals(folder.filename)){
                    return folder.fileId;
                }
                FileNode node = folder.child.getOrDefault(fileName,null);
                if(node!=null){
                    return node.fileId;
                }
            }else {
                addNewFile(fileName,"folder",folderId);
            }
            return getFileId(fileName,folderId);
        }

    void moveFile(int fileId, int newFolderId,boolean delete) {
        // TODO: implement
        Integer newfileId = relation.getOrDefault(newFolderId,-1);
        //maybe newFolderId not exist?
        if(newfileId==-1){
            newfileId =newFolderId;
        }
        FileNode folder = map.getOrDefault(newfileId,null);
        FileNode file = map.getOrDefault(fileId,null);

        //either one is not exist
        if(fileId<0||folder==null||file==null){
            System.out.println(fileId +"or" +newFolderId+"do not exist!");
            return;
        }

        if(file.isWorksheets||file.isDashboards){
            if(file.isWorksheets){
                totalWorksheets--;
            }

            if(file.isDashboards){
                totalDashboards--;
            }
            addNewFile(file.filename,file.fileType,newfileId);
            relation.remove(fileId);
            map.remove(fileId);

            //remove from parent directly
            if(file.parentId>=0 && delete){
                FileNode parent = map.get(file.parentId);
                parent.child.remove(file.filename);
//                parent.childMap.remove(file.fileId);
            }
        }else {
            addNewFile(file.filename, "folder", newfileId);
            int newId = getFileId(file.filename, newFolderId);

            for(FileNode child:file.child.values()){
                moveFile(child.fileId,newId,false);
            }
            //remove all children
            file.child = new TreeMap<>();
//            file.childMap = new TreeMap<>();

            // remove from parent directly
            if(file.parentId>=0 && delete){
                FileNode parent = map.get(file.parentId);
                parent.child.remove(file.filename);
//                parent.childMap.remove(file.fileId);
            }

        }
        return;
    }

        void moveFile(int fileId, int newFolderId) {
            moveFile(fileId,newFolderId,true);
        }

        String[] getFiles(int folderId) {
            // TODO: implement
            FileNode folder = map.getOrDefault(folderId,null);
            List<String> res = new ArrayList<>();
            if(folder!=null){
                for(FileNode node:folder.child.values()){
                    res.addAll(getFileNames(node,folder.filename));
                }
                String[] strings = new String[res.size()];
                int k=0;
                for(String fileName:res){
                    strings[k++] = fileName;
                }
                return strings;
            }
            return new String[0];
        }

        LinkedList<String> fileList;
        Integer pathDepth;
        void printFiles() {
            // TODO: implement
            fileList=new LinkedList<>();
            pathDepth = 0;
            prinfFile(root);
            return;
        }

        void prinfFile(FileNode fileNode){
             List<FileNode> children = new ArrayList<>();
            if(fileNode.isFolder){
                children.addAll(fileNode.child.values());
                pathDepth++;
                for(FileNode child:children){
                   for(int i=0;i<pathDepth-1;i++){
                       System.out.print(fileList.get(i));
                   }

                   if(child.isFolder){
                       //last file
                       if(children.get(children.size()-1).equals(child)){
                           System.out.print("└─");
                           fileList.add(pathDepth-1," ");
                       }else {
                           fileList.add(pathDepth-1,"│ ");
                           System.out.print("├─");
                       }
                       System.out.println(child.filename);
                       prinfFile(child);
                   }else {
                       //last file
                       if(children.get(children.size()-1).equals(child)){
                           System.out.print("└─");
                       }else {
                           System.out.print("├─");
                       }
                       System.out.println(child.filename);
                   }

                }
            }
            pathDepth--;
        }

        private List<String> getFileNames(FileNode file,String prefix){
            List<String> res = new ArrayList<>();
            String temp = prefix +"/"+file.filename;
            if(file.isFolder){
                for(FileNode child:file.child.values()){
                    if(child.isFolder){
                        res.addAll(getFileNames(child,temp));
                        getFileNames(child,temp);
                    }else {
                        res.add(temp+"/"+child.filename);
                    }
                }
            }else {
                res.add(temp);
            }
            return res;
        }

    public static void runExample() {
        SigmaFileSystem fs = new SigmaFileSystem();
        int rootId = fs.getFileId("MyDocuments", 0);
        fs.addNewFile("draft", "folder", rootId);
        fs.addNewFile("complete", "folder", rootId);
        int draftId = fs.getFileId("draft", rootId);
        int completeId = fs.getFileId("complete", rootId);
        fs.addNewFile("foo", "worksheet", draftId);
        fs.addNewFile("bar", "dashboard", completeId);
        int fooId = fs.getFileId("foo", draftId);
        fs.moveFile(fooId, completeId);

        System.out.println(String.join(", ", fs.getFiles(rootId)));
        System.out.println(String.join(", ", fs.getFiles(draftId)));
        System.out.println(String.join(", ", fs.getFiles(completeId)));

        fs.addNewFile("project", "folder", draftId);
        int projectId = fs.getFileId("project", draftId);
        fs.addNewFile("page1", "worksheet", projectId);
        fs.addNewFile("page2", "worksheet", projectId);
        fs.addNewFile("page3", "worksheet", projectId);
        fs.addNewFile("cover", "dashboard", projectId);
        fs.printFiles();
        fs.moveFile(projectId, completeId);
        projectId = fs.getFileId("project", completeId);
        int coverId = fs.getFileId("cover", projectId);
        fs.moveFile(coverId, rootId);

        System.out.println(String.join(", ", fs.getFiles(rootId)));
        System.out.println(String.join(", ", fs.getFiles(draftId)));
        System.out.println(String.join(", ", fs.getFiles(completeId)));
        System.out.println(String.join(", ", fs.getFiles(projectId)));

        System.out.println(fs.getTotalDashboards());
        System.out.println(fs.getTotalWorksheets());
        fs.printFiles();
    }

    private static int askForInteger(Scanner scanner, String question) {
        System.out.println(question);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            System.out.println("Please enter a valid integer.");
            return askForInteger(scanner, question);
        }
    }


    // Feel free to modify this main function as you see fit.
    public static void main(String[] args) {
//        runExample();
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        SigmaFileSystem fs = new SigmaFileSystem();
        int command;
        while (running) {
            command = askForInteger(scanner, "\nEnter an integer to indicate a command: \n[1] get_total_dashboards\n[2] get_total_worksheets\n[3] add_new_folder\n[4] get_file_id\n[5] move_file\n[6] get_files \n[7] print_files\n[8] exit\n");
            switch (command) {
                case 1: {
                    int totalDashboards = fs.getTotalDashboards();
                    System.out.println(String.format("There are %d dashboards in the file system.", totalDashboards));
                    break;
                }
                case 2: {
                    int totalWorksheets = fs.getTotalWorksheets();
                    System.out.println(String.format("There are %d worksheets in the file system.", totalWorksheets));
                    break;
                }
                case 3: {
                    System.out.println("Enter a new file name:");
                    String fileName = scanner.nextLine();
                    System.out.println("Enter a file type (worksheet, dashboard, or folder)");
                    String fileType = scanner.nextLine();
                    int folderId = askForInteger(scanner, "Enter a folder id where you'd like to put this file");
                    fs.addNewFile(fileName, fileType, folderId);
                    System.out.println(String.format("%s has been added to folder %d", fileName, folderId));
                    break;
                }
                case 4: {
                    System.out.println("Enter a file name:");
                    String fileName = scanner.nextLine();
                    int folderId = askForInteger(scanner, "Enter a folder id:");
                    System.out.println("your inoput: "+folderId);
                    int fileId = fs.getFileId(fileName, folderId);
                    System.out.println(String.format("%s is file %d", fileName, fileId));
                    break;
                }
                case 5: {
                    int fileId = askForInteger(scanner, "Enter a file id:");
                    int newFileId = askForInteger(scanner, "Enter the folder id where you'd like to move this file.");
                    fs.moveFile(fileId, newFileId);
                    System.out.println(String.format("Successfully moved file %d to folder %d", fileId, newFileId));
                    break;
                }
                case 6: {
                    int folderId = askForInteger(scanner, "Enter a folder id:");
                    String[] fileNames = fs.getFiles(folderId);
                    if (fileNames.length == 0) {
                        System.out.println(String.format("There are no files in folder %d", folderId));
                    } else {
                        System.out.println(String.format("The following files are in folder %d:", folderId));
                        for (String fileName: fileNames) {
                            System.out.println(String.format("\t%s", fileName));
                        }
                    }
                    break;
                }
                case 7: {
                    fs.printFiles();
                    break;
                }
                case 8: {
                    System.out.println("Exiting program.");
                    running = false;
                    scanner.close();
                    break;
                }
                default:
                    System.out.println(String.format("Invalid command: %d. Please try again.\n",command));
            }
        }
    }
}
