package com.ja.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BigdataServiceImpl implements BigdataService {

	private final static int USER_NUM = 100; 
	private final static int TOTAL_ARTICLE = 100000;
	
	static double min = 0;
	static double max = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random(123);
		
		List<Double> user_activity = new ArrayList<Double>();
		List<Integer> user_article_num = new ArrayList<Integer>();  
		List<ArticleVO> news
		
		//percent값이 아직 아님
		input_userActivity(rand, user_activity, min);
		//percent값으로 변환
		activity_percent(user_activity);
		print_activity(user_activity);
		inputUser_Article(user_activity, user_article_num);
		
		
		
		
	}

	private static void inputUser_Article(List<Double> user_activity, List<Integer> user_article_num) {
		for(int i=0; i<USER_NUM; i++) {
			user_article_num.add((int) Math.round(user_activity.get(i) * TOTAL_ARTICLE));
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