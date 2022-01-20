package com.cscec3b;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cscec3b.mapper")
public class Cscec3bWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cscec3bWebApplication.class, args);
	}
}
