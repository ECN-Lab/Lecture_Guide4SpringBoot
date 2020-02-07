package com.ntsphere.ecn.basicweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.ntsphere.ecn.basicweb.config.ServerProperty;

@SpringBootApplication
@ComponentScan({"com.ntsphere.ecn.basicweb"})
@EnableConfigurationProperties(ServerProperty.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
