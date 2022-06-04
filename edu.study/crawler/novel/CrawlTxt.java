package crawler.novel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class CrawlTxt {
    public static void main(String[] args) {
        parsePageList();
    }

    public static Document getPage(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(6000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }


    private static void parsePageList() {

        for (int i = 64094; i <= 64111; i++) {
            String url = "https://www.aishuge.la/kan/28/28152/" +
                    i + ".html";
            Document doc = getPage(url);
            System.out.println(i);
            try {
                parsePage(doc);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }catch (Exception e){

            }
        }

    }

    private static void parsePage(Document document) {
        String title = document.select("div.neirong > h2").text() + "\n\r";
        String content = "";
        Elements elements = document.select("#txt > dd[data-id] > p");
        for(Element element:elements){
            content+=element.text()+"\n\r";
        }
        System.out.println(title);
        save("aojiao.txt", title + content);
    }

    private static void save(String file, String content) {
        content = content.replace("阅读傲娇没有女朋友最新章节","");
        content = content.replace("请关注明月小说网(www.aishuge.la)","");
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(content + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
