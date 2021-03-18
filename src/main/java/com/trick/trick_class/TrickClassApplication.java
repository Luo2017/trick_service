package com.trick.trick_class;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.trick.trick_class.mapper")
@EnableTransactionManagement
public class TrickClassApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrickClassApplication.class, args);
	}

}
