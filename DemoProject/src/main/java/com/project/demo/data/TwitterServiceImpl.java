package com.project.demo.data;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.CursoredList;
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
	public Offer rewardCustomer(String custId) {
		String offercode = "PP" + UUID.randomUUID().toString().substring(0, 15);
		String offerdesc = "buy one get one free coupon";
		Offer offer = new Offer();
		offer.setOffercode(offercode);
		offer.setOfferdesc(offerdesc);
		offer.setIssuedfor(custId);
		
		String message = "Thank you for mentioning us! Here is a one-time " + offerdesc;
		twitter.directMessageOperations().sendDirectMessage(custId, message + offercode);
		
		return offer;
	}

	@Override
	public Tweet postDailySpecial(String text) {
		return twitter.timelineOperations().updateStatus(new TweetData(text));
	}

	@Override
	public List<Tweet> getWhoMentionedMe() {
		return twitter.timelineOperations().getMentions(150);
	}
	
	@Override
	public CursoredList<Long> getMyFollowers() {
		return twitter.friendOperations().getFollowerIds();
	}

}
