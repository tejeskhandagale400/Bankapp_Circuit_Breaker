package com.capgemini.zuulservicebank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class ZuulServiceBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServiceBankApplication.class, args);
	}

}

