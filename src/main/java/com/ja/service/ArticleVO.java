package com.ja.service;

import java.util.Date;

import lombok.Data;

@Data
public class ArticleVO {
	private int writer;
	private String title;
	private String boody;
	
	private int year;
	private int month;
	private int day;
	private int dayOfWeek;
	private Date time;
	
}
