package com.ja.vo;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleVO {
	private int writer;
	private String title;
	private String boody;
	
	private int year;
	private int month;
	private int day;

	private int hour;
	private int millisec;

	
	private int dayOfWeek;
	private int min;
	private int sec;
	private Date time;	
	
	@Override
	public String toString() {
		return "ArticleVO [writer=" + writer + ", title=" + title + "]\n";
	}
}
