package com.project.demo.data;

import java.util.List;

import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;

public interface TwitterService {

	public List<Tweet> getWhoMentionedMe();
	
	public Tweet postDailySpecial(String text);
	
	public Offer rewardCustomer(String custId);
	
	public CursoredList<Long> getMyFollowers();
	
}
