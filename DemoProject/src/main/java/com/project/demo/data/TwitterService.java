package com.project.demo.data;

import java.util.List;


import org.springframework.social.twitter.api.Tweet;

public interface TwitterService {

	public List<Tweet> getMyTweets();
	
	public Tweet postDailySpecial(String text);
	
	public String rewardCustomer(String custId);
	
}
