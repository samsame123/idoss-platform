package com.idoss.main;

import com.idoss.support.annotation.EnableHttpClientConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDubboConfig
@EnableHttpClientConfig
@EnableAspectJAutoProxy
@DubboComponentScan("com.idoss.support.service")
@ComponentScan("com.idoss.support")
@MapperScan(basePackages="com.idoss.support.mapper")
public class IdossMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdossMainApplication.class, args);
	}

}
