package com.wl.batch;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class Scheduled {
//	@Scheduled(cron = "* 0/20 * * * *")
	public void myTest(){
		System.out.println("start test");
		Arrays.sort(new Long[10]);
	}
	
}
