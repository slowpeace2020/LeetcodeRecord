package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.spire.presentation.FileFormat;
import com.spire.presentation.Presentation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ConcatenatePDF {

    /**
     * @param files          源PDF路径
     * @param outputPath     合并后输出的PDF路径
     * @param outputFileName 合并后输出的PDF文件名
     * @author Reverse_XML
     * 将多个PDF合并成一个PDF
     */
    public static void mergePDF(List<String> files, String outputPath, String outputFileName) {

        String sep = java.io.File.separator;
        Document document = null;
        PdfCopy copy = null;
        PdfReader reader = null;
        try {

            document = new Document(new PdfReader(files.get(0)).getPageSize(1));
            copy = new PdfCopy(document, new FileOutputStream(outputPath + sep + outputFileName));
            document.open();
            for (int i = 0; i < files.size(); i++) {

                reader = new PdfReader(files.get(i));
                int numberOfPages = reader.getNumberOfPages();
                for (int j = 1; j <= numberOfPages; j++) {

                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    copy.addPage(page);
                }
            }
        } catch (IOException e) {

            e.getMessage();
        } catch (DocumentException e) {

            e.getMessage();
        } finally {

            if (document != null)
                document.close();
            if (reader != null)
                reader.close();
            if (copy != null)
                copy.close();
        }
    }


    /**
     *
     */
    public static void pptToPDF(List<String> files) {
        //创建Presentation实例
        Presentation presentation = new Presentation();

        for(String filePath:files){
            //加载PPT示例文档
            try {
                presentation.loadFromFile(filePath);
                //保存为PDF
                presentation.saveToFile(System.getProperty("user.home")+filePath.substring(filePath.lastIndexOf("/")).replace("ppt","pdf"), FileFormat.PDF);

            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
 }

        presentation.dispose();
    }

    public static void main(String[] args) {
        List<String> fileList = new ArrayList<>();
        fileList = getFiles("/Users/shanchu/Documents/CSYE6200 class/lecture3",fileList);
//        mergePDF(fileList,"/Users/shanchu/IdeaProjects/LeetcodeRecord/edu.study/utils","lecture3.pdf");
        pptToPDF(fileList);
    }

    private static List<String> getFiles(String filePath, List<String> fileList) {
        File dir = new File(filePath);
//这里可以做个判断是否存在这个文件夹再进行下面操作
        if (!dir.exists()) {
            System.out.println("路径不存在!");
            throw new RuntimeException();
        }
//获取文件集合
        File[] files = dir.listFiles();
//排序
        Arrays.sort(files, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o1.lastModified() - o2.lastModified());
            }
        });
//判断是否是文件夹,如果是就进行递归,不是就添加进List集合.
//List集合特性是可以保证元素的添加顺序
        for (File file1 : files) {
            if (file1.isDirectory()) {
                continue;
//                getFiles(file1.getAbsolutePath(), fileList);
            } else {
                System.out.println(file1.getAbsolutePath());
                fileList.add(file1.getAbsolutePath());
            }
        }

        return fileList;

    }
}
