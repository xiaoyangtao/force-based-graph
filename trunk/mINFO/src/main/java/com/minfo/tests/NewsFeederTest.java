package com.minfo.tests;

import com.minfo.services.NewsFeeder;

public class NewsFeederTest {
	public static void main(String[] args) {
		NewsFeeder feeder = new NewsFeeder();
		
		feeder.getNews();
		
	}
}
