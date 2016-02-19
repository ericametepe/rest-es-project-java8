package com.gocpf.repository;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.gocpf.configuration.SwaggerConfiguration;

@Configuration
@EnableAutoConfiguration(exclude=SwaggerConfiguration.class)
@ComponentScan(basePackages={"com.gocpf"})
public class ApplicationGoCPFConfigTest {

}
