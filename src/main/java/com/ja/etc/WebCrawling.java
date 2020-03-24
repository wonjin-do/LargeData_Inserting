package com.ja.etc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;import lombok.Getter;

public class WebCrawling {
	public WebCrawling() {
		System.out.println(getClass());
	}
	
	private static String getText(String content) {  
	    Pattern SCRIPTS = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>",Pattern.DOTALL);  
	    Pattern STYLE = Pattern.compile("<style[^>]*>.*</style>",Pattern.DOTALL);  
	    Pattern TAGS = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");  
	    Pattern nTAGS = Pattern.compile("<\\w+\\s+[^<]*\\s*>");  
	    Pattern ENTITY_REFS = Pattern.compile("&[^;]+;");  
	    Pattern WHITESPACE = Pattern.compile("\\s\\s+");  
	    Matcher m;
	      
	    m = SCRIPTS.matcher(content);  
	    content = m.replaceAll("");  
	    m = STYLE.matcher(content);  
	    content = m.replaceAll("");  
	    m = TAGS.matcher(content);  
	    content = m.replaceAll("");  
	    m = ENTITY_REFS.matcher(content);  
	    content = m.replaceAll("");  
	    //m = WHITESPACE.matcher(content);  
	    //content = m.replaceAll(" ");          
	    return content;  
	}  
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebCrawling wc = new WebCrawling();
		// 1. URL ���� 
		String connUrl = "https://news.naver.com/main/ranking/read.nhn?rankingType=popular_day&oid=081&aid=";
		for(int i=95; i<=100; i++) {
			try {
				// 2. HTML �������� 
				Document doc = Jsoup.connect(connUrl+String.format("%010d", i)).get();
				System.out.println(connUrl+String.format("%010d", i));
				// 3. ������ HTML Document �� Ȯ���ϱ� 
				Element title = doc.getElementById("articleTitle");
				Element article = doc.getElementById("articleBodyContents");
				
				String refinedArticle = article.toString().replace(" <br>", "\n").replace("\n\n\n", "\n").replace("\n\n", "\n");
				refinedArticle = getText(refinedArticle);
				int idx = refinedArticle.indexOf("\n");
				refinedArticle = refinedArticle.substring(idx+1);
				for(int j=0;j<3;j++) {
					idx = refinedArticle.lastIndexOf("\n");
					if(idx != -1)refinedArticle = refinedArticle.substring(0, idx);	
				}
				
				//파일 객체 생성
				File file1 = new File("C:/Users/Bizspring/Desktop/largeData/title"+ i +".txt");
			    File file2 = new File("C:/Users/Bizspring/Desktop/largeData/article"+ i +".txt");
			    
			    try {
			      FileWriter fwTitle = new FileWriter(file1);
			      FileWriter fwArticle = new FileWriter(file2);
			      //fwArticle.write(article.toString());
			      fwTitle.write(title.text());
			      fwArticle.write(refinedArticle);
			      fwTitle.close();
			      fwArticle.close();
			      
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
				
			} catch (IOException e) { 
				// Exp : Connection Fail
				e.printStackTrace(); 
			}
		}
			
	}

}
