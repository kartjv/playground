package com.project.demo.data;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TweetData;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Service;

import com.project.demo.config.TwitterConfig;

@Service
public class TwitterServiceImpl implements TwitterService {

	private Twitter twitter;

	@Autowired
	public TwitterServiceImpl() {
		TwitterConfig config = new TwitterConfig();
		twitter = config.twitterTemplate();
	}

	@Override
	public String rewardCustomer(String custId) {
		String offercode = "PP" + UUID.randomUUID().toString().substring(0, 15);
		String offerdesc = "buy one get one free";
		String message = "ThankYou for tweeting us! Here is a one-time for " + offerdesc;
		twitter.directMessageOperations().sendDirectMessage(custId, message + offercode);
		String forwardUrl = "forward:/offers/add?offercode=" + offercode + "&issuedfor=" + custId + "&offerdesc="
				+ offerdesc;
		return forwardUrl;
	}

	@Override
	public Tweet postDailySpecial(String text) {
		return twitter.timelineOperations().updateStatus(new TweetData(text));
	}

	@Override
	public List<Tweet> getMyTweets() {
		return twitter.searchOperations().search("PizzaPalace", 10).getTweets();
	}

}
