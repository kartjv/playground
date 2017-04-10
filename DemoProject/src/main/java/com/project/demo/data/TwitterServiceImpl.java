package com.project.demo.data;

import java.util.Collections;
import java.util.HashMap;
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
	private static Long lastTweetId = 0L;
	private static Long maxTweetId = 0L;

	@Autowired
	public TwitterServiceImpl() {
		TwitterConfig config = new TwitterConfig();
		twitter = config.twitterTemplate();
	}

	@Autowired
	private OfferRepository offerRepository;

	@Override
	public Offer rewardCustomer(String custScreenName, Long tweetId) {
		String offercode = "PP" + UUID.randomUUID().toString().substring(0, 19);
		String offerdesc = "buy one get one free coupon";
		Offer offer = new Offer();
		offer.setOffercode(offercode.toUpperCase());
		offer.setOfferdesc(offerdesc);
		offer.setIssuedfor(custScreenName);
		offer.setIsredeemed(false);
		offerRepository.save(offer);

		String message = "@" + custScreenName + " Thank you for mentioning us! Here is a one-time " + offerdesc + "- "
				+ offercode.toUpperCase();

		twitter.timelineOperations().updateStatus(new TweetData(message).inReplyToStatus(tweetId));
		return offer;
	}

	@Override
	public Tweet postDailySpecial(String text) {
		return twitter.timelineOperations().updateStatus(new TweetData(text));
	}

	@Override
	public HashMap<String, Long> getWhoMentionedMe() {
		List<Tweet> tweetsMentioningMe = Collections.EMPTY_LIST;
		if(lastTweetId == 0L) {
			tweetsMentioningMe = twitter.timelineOperations().getMentions(50);
		} else {
			tweetsMentioningMe = twitter.timelineOperations().getMentions(50, lastTweetId, maxTweetId);
		}
		HashMap<String, Long> map = new HashMap<String, Long>();

		for (Tweet tw : tweetsMentioningMe) {
			String name = tw.getUser().getScreenName();
			if (!name.equals("PizzaPalace_SD") && !map.containsKey(name)) {
				map.put(name, tw.getId());
				lastTweetId = tw.getId();
			}
			maxTweetId = tw.getId();
		}
		return map;
	}

	@Override
	public CursoredList<Long> getMyFollowers() {
		return twitter.friendOperations().getFollowerIds();
	}

}
