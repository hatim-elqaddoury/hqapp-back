package org.qad.project;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HqAppApplication {
	private static final Logger log = Logger.getLogger(HqAppApplication.class);

	public static void main(String[] args) {
		
		ApplicationContext ctx = SpringApplication.run(HqAppApplication.class, args);
		log.info(ctx.getId() + " is starting...");
		
		
	}

}
