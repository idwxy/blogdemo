package com.idwxy.blogdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BlogdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogdemoApplication.class, args);
	}

}
