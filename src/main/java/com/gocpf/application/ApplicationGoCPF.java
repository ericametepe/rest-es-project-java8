package com.gocpf.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.gocpf.configuration.ElasticsearchConfiguration;

/**
 * 
 * @author kodjovi1
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.gocpf"})
@EnableAutoConfiguration(exclude = {ElasticsearchConfiguration.class})
public class ApplicationGoCPF {
    public static void main(String args[]){
        SpringApplication.run(ApplicationGoCPF.class);
    }
}
