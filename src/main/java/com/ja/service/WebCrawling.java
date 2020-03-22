package com.ja.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawling {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1. URL ���� 
		String connUrl = "https://news.naver.com/main/ranking/read.nhn?rankingType=popular_day&oid=081&aid=";
		for(int i=1; i<=3; i++) {
			try {
				// 2. HTML �������� 
				Document doc = Jsoup.connect(connUrl+String.format("%010d", i)).get(); 
				// 3. ������ HTML Document �� Ȯ���ϱ� 
				Element title = doc.getElementById("articleTitle");
				Element article = doc.getElementById("articleBodyContents");
				System.out.println(title.text());
				System.out.println(article.toString());
				System.out.println(article.text());
			} catch (IOException e) { 
				// Exp : Connection Fail
				e.printStackTrace(); 
			}
		
		
		}
		
			
			
			
	}

}
