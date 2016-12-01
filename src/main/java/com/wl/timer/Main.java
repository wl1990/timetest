package com.wl.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wl.batch.TestQuartzJob;

public class Main {
	public static final String DEFAULT_SPRING_CONFIG = "classpath*:test-timer.xml";
	private static Logger logger=LoggerFactory.getLogger(Main.class);
	
	private static volatile boolean running = true;
	static ClassPathXmlApplicationContext context;
	public static void main(String[] args) {
		  logger.info("-------start----");
		  Runtime.getRuntime().addShutdownHook(new Thread() {
              public void run() {
                  try {
                      try {
                          if (context != null) {
                              context.stop();
                              context.close();
                              context = null;
                          }
                      } catch (Throwable e) {
                          e.printStackTrace();
                      }
                      logger.info("test timer stopped!");
                  } catch (Throwable t) {
                     t.printStackTrace();
                  }
                  synchronized (Main.class) {
                      running = false;
                      Main.class.notify();
                  }
              }
          });
		context=new ClassPathXmlApplicationContext(DEFAULT_SPRING_CONFIG);
		 context.start();
		 logger.info("test timer server started!");
		 synchronized (Main.class) {
	            while (running) {
	                try {
	                    Main.class.wait();
	                } catch (Throwable ignored) {

	                }
	            }
	        }
		 
		 
	}
	
}
