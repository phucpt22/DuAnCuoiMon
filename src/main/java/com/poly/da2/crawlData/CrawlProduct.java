package com.poly.da2.crawlData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class CrawlProduct {
    public static void main(String[] args) {
        String url ="https://tiki.vn/api/v2/products?limit=40&category=29010&page=1";
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(document.html());
    }
}
