package com.project.demo.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
@PropertySource("classpath:config.properties")
public class TwitterConfig {

	@Autowired
	private Environment env;
	
	private static Environment environment;
	
	@PostConstruct
	public void init() {
		environment = env;
	}
	
	@Bean
	public Twitter twitterTemplate() {
		TwitterTemplate twitter = new TwitterTemplate(
				environment.getProperty("com.twitter.consumer.key"),
				environment.getProperty("com.twitter.consumer.secret"), 
				environment.getProperty("com.twitter.access.token"),
				environment.getProperty("com.twitter.access.token.secret"));
		return twitter;
	}

}
