package com.ja.etc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextRead {

	List<String> rows;
	public TextRead() {
		super();
		this.rows = new ArrayList<String>();
	}

	public List<String> readTextFile(String filePath)  {
		try{
			//파일 객체 생성
	        File file = new File(filePath);
	        FileReader filereader = new FileReader(file);//입력 스트림 생성
	        BufferedReader bufReader = new BufferedReader(filereader);//입력 버퍼 생성			
	        String row;
	        while((row = bufReader.readLine()) != null){
	        	rows.add(row);//라인단위로 읽는 함수는 \n 을 읽어오진 않음
	        }
		 }catch (FileNotFoundException e) {
	            // TODO: handle exception
        }catch(IOException e){
            System.out.println(e);
        }
        return rows;
	}
}
