package com.ja.etc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class SplitOrigin {
	private List<String> allTitle = new ArrayList<String>();
	private List<String> allSentence = new ArrayList<String>();;
	private List<String> allNews = new ArrayList<String>();;
	public SplitOrigin() {
		for(int i=1;i<=100;i++) {
			try{
				//파일 객체 생성
				File file1 = new File("C:/Users/Bizspring/Desktop/largeData/title"+ i +".txt");
			    File file2 = new File("C:/Users/Bizspring/Desktop/largeData/article"+ i +".txt");
		        FileReader titleFileReader   = new FileReader(file1);//입력 스트림 생성
		        FileReader articleFileReader = new FileReader(file2);//입력 스트림 생성
		        BufferedReader titlebufReader = new BufferedReader(titleFileReader);//입력 버퍼 생성			
		        BufferedReader articlebufReader = new BufferedReader(articleFileReader);//입력 버퍼 생성
		        String row;
		        while((row = titlebufReader.readLine()) != null){
		        	allTitle.add(row);//라인단위로 읽는 함수는 \n 을 읽어오진 않음
		        }
		        allNews.add(new String(Files.readAllBytes(Paths.get("C:/Users/Bizspring/Desktop/largeData/article"+ i +".txt")), StandardCharsets.UTF_8));
		        
			 }catch (FileNotFoundException e) {
		            // TODO: handle exception
	        }catch(IOException e){
	            System.out.println(e);
	        }
		}
		for(String news : allNews) {
			String [] paragraphs = news.split("\n");
			for(String paragraph : paragraphs) {
				String [] sentences = paragraph.split("\\.");
				for(String sentence : sentences) {
					allSentence.add(sentence);
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SplitOrigin so = new SplitOrigin();
	}
}
