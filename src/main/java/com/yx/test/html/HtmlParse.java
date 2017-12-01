package com.yx.test.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;

public class HtmlParse {

    public static void parse(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".portfolio-block");
        if (elements.size() > 0) {
            Iterator<Element> iterator = elements.iterator();
            Element element;
            String english;
            String date;
            while (iterator.hasNext()) {
                element = iterator.next();
                english = element.select(".col-md-4 p").get(0).text();
                date = element.select(".col-md-3").get(0).select("h3").get(0).text();
                System.out.println(english + "---------" + date);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        parse("http://shijian.cc/116/jieri");
    }
}
