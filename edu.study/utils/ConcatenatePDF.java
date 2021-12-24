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
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.ExternalOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;

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


    public static void main(String[] args) {
        List<String> fileList = new ArrayList<>();
        fileList = getFiles("/Users/shanchu/IdeaProjects/LeetcodeRecord/edu.study/utils",fileList);
//        mergePDF(fileList,"/Users/shanchu/IdeaProjects/LeetcodeRecord/edu.study/utils","lecture3.pdf");
        pptToPDF(fileList);

    }

    /**
     *
     * @param files
     */
    public static void pptToPDF(List<String> files) {

        //创建Presentation实例

        for (String filePath : files) {
            //加载PPT示例文档
            File inputFile = new File(filePath,"MacOS/soffice.bin");
            File outputFile = new File(filePath.replace("pptx", "pdf").replace("ppt", "pdf"),"MacOS/soffice.bin");
            //保存为PDF

            ppt2PDF(inputFile, outputFile);

            break;
        }

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
                if(!file1.getAbsolutePath().endsWith("ppt")&&!file1.getAbsolutePath().endsWith("pptx")){
                    continue;
                }
                fileList.add(file1.getAbsolutePath());
            }
        }

        return fileList;

    }


    /**

     * ppt转pdf

     * @param inputFile 输入文件

     * @param outputFile 输出文件

     * @author ServerZhang

     * @date 2016年9月20日

     */

    public static void ppt2PDF(File inputFile, File outputFile) {

        // 如果目标路径不存在, 则新建该路径

        if (!outputFile.getParentFile().exists()) {

            outputFile.getParentFile().mkdirs();

        }



        // convert

        ExternalOfficeManagerConfiguration configuration =

            new ExternalOfficeManagerConfiguration();

        configuration.setConnectionProtocol(OfficeConnectionProtocol.SOCKET);

        configuration.setPortNumber(8100);

        OfficeManager officeManager= configuration

            .buildOfficeManager();

        OfficeDocumentConverter converter =

            new OfficeDocumentConverter(officeManager);

        converter.convert(inputFile, outputFile);

    }


}
