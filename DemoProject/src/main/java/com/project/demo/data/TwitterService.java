package com.project.demo.data;

import java.util.HashMap;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;

public interface TwitterService {

	public HashMap<String, Long> getWhoMentionedMe();
	
	public Tweet postDailySpecial(String text);
	
	public Offer rewardCustomer(String custScreenName, Long custId);
	
	public CursoredList<Long> getMyFollowers();
	
}
