package com.example.sealZuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SealZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SealZuulApplication.class, args);
	}

}
