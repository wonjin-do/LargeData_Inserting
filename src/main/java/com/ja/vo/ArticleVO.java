package com.ja.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ArticleVO {
	private int writer;
	private String title;
	private String content;
	
	private java.sql.Date start;
	private java.sql.Date end;
	@Override
	public String toString() {
		return "writer=" + writer + ", title=" + title + "\n content=" + content + "\n, start=" + start
				+ ", end=" + end + "\n\n";
	}
	
	
}
