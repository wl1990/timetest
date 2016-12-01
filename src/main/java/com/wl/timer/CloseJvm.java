package com.wl.timer;

import java.io.IOException;

public class CloseJvm {
	public static void main(String[] args) {
	    try {
	    	Runtime.getRuntime().addShutdownHook(new Thread(){
	    		public void run(){
	    			 System.out.println("------- stopped! --------");
	    		}
	    	});
	    	
	    	System.out.println("-----start----");
	    	String [] cmd={"cmd","/C","copy exe1 exe2"};
			Process proc =Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
}
