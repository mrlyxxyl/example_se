package com.yx.pc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 爬取用户昵称
 */
public class ParseHtml {
    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("e:/nick_name.sql", true);
        long time = 1290513230000L;
        try {
            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
                String url = "http://bbs.tianya.cn/list.jsp?item=lookout&nextid=" + (time + 219861805L * i);
                Document doc = Jsoup.connect(url).get();
                Elements elements = doc.select("a.author");
                for (Element element : elements) {
                    String title = element.text().trim().replaceAll("'", "");
                    if (!title.matches("\\d+")) {
                        String sql = "INSERT INTO `user` (`name`) VALUES ('" + element.text().trim().replaceAll("'", "") + "');\n";
                        fileWriter.write(sql);
                        fileWriter.flush();
                    }
                }
            }
        } catch (IOException e) {
        }
    }
}