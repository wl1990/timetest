package com.wl.batch;



public class TestQuartzJob {
	public void test(){
		
		try {
			System.out.println(Thread.currentThread().getName()+"----<><><><><><>--start----"+System.currentTimeMillis());
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()+"----*******--end----"+System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
public void test1(){
		
		try {
			System.out.println(Thread.currentThread().getName()+"----***********--start----"+System.currentTimeMillis());
			Thread.sleep(1000);
			System.out.println(Thread.currentThread().getName()+"--------------end----"+System.currentTimeMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
