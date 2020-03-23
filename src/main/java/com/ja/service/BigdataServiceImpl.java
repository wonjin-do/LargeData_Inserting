package com.ja.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.ja.vo.ArticleVO;

public class BigdataServiceImpl implements BigdataService {

	private final static int USER_NUM = 100; 
	private final static int TOTAL_ARTICLE = 100000;
	
	static double min = 0;
	static double max = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random(123);
		Double[] month_percent = {0.0, 9.0, 9.0, 8.0, 8.0,
								       8.0, 8.0, 9.0, 9.0,
								       8.0, 8.0, 8.0, 8.0};
		
		Double[] week_percent  = {14.83, 14.59, 15.71, 
								  14.96, 13.93, 13.08, 12.9};
		
		Double[] time_percent = {3.93, 2.49, 1.5  , 0.99, 0.74, 0.7 , 0.98, 1.7 , 3.13, 4.84, 5.61,	5.92
								, 5.42, 6.02, 6.02,	6.16, 6.24, 6.13, 5.36, 5.02, 5.28, 5.49, 5.35,	4.98};
		
		List<Double> user_activity = new ArrayList<Double>();
		List<Integer> num_of_userArticle = new ArrayList<Integer>();  
		List<ArticleVO> articleList = new ArrayList<ArticleVO>();
		int year = 2020;
		
		
		input_userActivity(rand, user_activity, min);
		//make activity percent
		activity_percent(user_activity);
		//print_activity(user_activity);
		input_num_of_article(user_activity, num_of_userArticle);
		
		matching_Article_With_User(num_of_userArticle, articleList);
		
		int numOfArticle_per_month [] = new int[13];
		
		
		int article_idx = 0;
		int all = 0;
		for(int month=1; month<=12; month++) {
			//distribute to monthArray
			numOfArticle_per_month[month] = (int)(TOTAL_ARTICLE * (month_percent[month]/100));
			//numOfArticle_per_month[month] = (int) Math.round(TOTAL_ARTICLE * (month_percent[month]/100.0));
			//printInt(numOfArticle_per_month);
			Calendar cal = Calendar.getInstance();
			cal.set(year, month - 1, 1);
			int lastDay = cal.getActualMaximum(Calendar.DATE);
			int dayOfweek = cal.get(Calendar.DAY_OF_WEEK);
			
			//readyFor Distribution Of day 
			int numOfArticle_per_day[][] = new int[13][lastDay+1];
			int numOfArticle_per_hour[][][] = new int[13][lastDay+1][24];
			double avg_per_day = (numOfArticle_per_month[month]/(double)lastDay);
			int totalNum_per_week = (int)(avg_per_day * 7);
			
			int tot = 0;
			for(int day=1; day<=lastDay; day++) {//1일 ~ 31일
				numOfArticle_per_day[month][day] =(int)(totalNum_per_week * week_percent[dayOfweek % 7]/100);
				dayOfweek = (dayOfweek + 1) % 7;
				tot += numOfArticle_per_day[month][day];
			}
			int gap = numOfArticle_per_month[month] - tot;
			numOfArticle_per_day[month][1] += gap;
//			tot = 0;
//			for(int day=1; day<=lastDay; day++) {//1일 ~ 31일
//				tot += numOfArticle_per_day[month][day];
//			}
//			System.out.println(numOfArticle_per_month[month] + ", " + tot);
			
			
			for(int day=1; day<=lastDay; day++) {
				System.out.printf("%d일 하루게시글 수: %d\n", day, numOfArticle_per_day[month][day]);
				tot=0;
				for(int hour=0; hour<24; hour++) {
					numOfArticle_per_hour[month][day][hour] = (int)(numOfArticle_per_day[month][day] * time_percent[hour]/100);
					tot += numOfArticle_per_hour[month][day][hour];
				}
				gap = numOfArticle_per_day[month][day] - tot;
				numOfArticle_per_hour[month][day][12] += gap;
				
				
//			    for(int hour=0; hour<24; hour++) {
//				   System.out.print(numOfArticle_per_hour[month][day][hour] + " "); 
//				   all +=  numOfArticle_per_hour[month][day][hour];
//			    }System.out.println();
				  
				for(int hour=0; hour<24; hour++) {
					int article_per_hour = numOfArticle_per_hour[month][day][hour];
					int allocation_millisec = 60*60*1000 / article_per_hour;
					
					ArticleVO ele = articleList.get(article_idx++);
					ele.setYear(year);
					ele.setMonth(month);
					ele.setDay(day);
					ele.setHour(hour);
					ele.setMillisec(allocation_millisec);
					
				}
			}
			
		}//month END
		
				
	}

	private static void printInt(int [] list) {
		for(int i=0; i<list.length; i++) {
			System.out.print(list[i] + " ");
		}System.out.println();
	}
	
	private static void printDouble(double [] list) {
		for(int i=0; i<list.length; i++) {
			System.out.print(list[i] + " ");
		}System.out.println();
	}
	
	private static void matching_Article_With_User(List<Integer> num_of_userArticle, List<ArticleVO> articleList) {
		for(int user=0; user< USER_NUM; user++) {
			int num_of_articlePerUser = num_of_userArticle.get(user);
			for(int i=0; i<num_of_articlePerUser; i++) {
				ArticleVO ele = new ArticleVO();
				ele.setWriter(user);
				articleList.add(ele);
			}
		}
		Collections.shuffle(articleList);
	}

	private static void input_num_of_article(List<Double> user_activity, List<Integer> num_of_userArticle) {
		for(int i=0; i<USER_NUM; i++) {
			num_of_userArticle.add((int) Math.round(user_activity.get(i) * TOTAL_ARTICLE));
		}
	}

	private static void input_userActivity(Random rand, List<Double> user_activity, double min) {
		
		for(int i=0; i<USER_NUM; i++) {
			double act = rand.nextGaussian();
			min = (act < min)? act : min;
			max = (act > max)? act : max;
			user_activity.add(act);
		}
		
		
		for(int i=0; i<USER_NUM; i++) {
			double act = user_activity.get(i);
			user_activity.set(i, act-min);
		}
	}

	private static void print_activity(List<Double> user_activity) {
		//testCode
		for(Double elem : user_activity) {
			System.out.printf("%s ",Math.round(elem*10000)/10000.0);
		}System.out.println();
	}

	private static void activity_percent(List<Double> user_activity) {
		double tot = 0;
		for(Double elem : user_activity) {
			tot += elem;
		}
		for(int i=0; i<USER_NUM; i++) {
			double act = user_activity.get(i);
			user_activity.set(i, act/tot);
		}
	}

}
;