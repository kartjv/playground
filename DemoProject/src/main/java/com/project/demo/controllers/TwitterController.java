package com.project.demo.controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ApiException;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.InvalidMessageRecipientException;
import org.springframework.social.twitter.api.MessageTooLongException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.demo.data.DailySpecial;
import com.project.demo.data.TwitterService;

@RestController
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@RequestMapping(value = "/tweet")
	public ModelAndView getMyTweets(ModelAndView mav) {
		mav.addObject("dailySpecial", new DailySpecial());
		mav.setViewName("tweetform :: tweet-daily");
		return mav;
	}

	@RequestMapping(value = "/tweetdaily")
	public ModelAndView postDailySpecial(@ModelAttribute DailySpecial dailySpecial, ModelAndView mav) {
		twitterService.postDailySpecial(dailySpecial.getSpecialName() + " - " + dailySpecial.getBriefRecipe());
		mav.addObject("tweetStatus", "Updated status on Twitter. Visit http://www.twitter.com");
		mav.setViewName("redirect:/");
		return mav;
	}

	@Scheduled(fixedRate = 60000)
	public void autoRespondInTwitter() {
		HashMap<String, Long> mymentions = twitterService.getWhoMentionedMe();
		if (twitterService != null && mymentions.size() > 0) {
			for (String screenName : mymentions.keySet()) {
				twitterService.rewardCustomer(screenName, mymentions.get(screenName));
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				System.out.println("InterruptedException occurred - " + e.getMessage());
			}
		}
	}

	@ExceptionHandler(ApiException.class)
	public void handleApiException(ApiException apie) {
		System.out.println("Error while communicating with Twitter - " + apie.getMessage());
	}

	@ExceptionHandler(MissingAuthorizationException.class)
	public void handleMissingAuthorizationException(MissingAuthorizationException mae) {
		System.out.println("TwitterTemplate was not created with OAuth credentials - " + mae.getMessage());
	}

	@ExceptionHandler(DuplicateStatusException.class)
	public void handleDuplicateStatusException(DuplicateStatusException dse) {
		System.out.println("Message duplicates a previously sent message - " + dse.getMessage());
	}

	@ExceptionHandler(InvalidMessageRecipientException.class)
	public void handleInvalidMessageRecipientException(InvalidMessageRecipientException imre) {
		System.out.println("Recipient is not following the authenticating user - " + imre.getMessage());
	}

	@ExceptionHandler(OperationNotPermittedException.class)
	public void handleOperationNotPermittedException(OperationNotPermittedException onpe) {
		System.out.println("Resource not as expected - " + onpe.getMessage());
	}

	@ExceptionHandler(MessageTooLongException.class)
	public void handleMessageTooLongException(MessageTooLongException mtle) {
		System.out.println("Length of the status message exceeds Twitter's 140 character limit - " + mtle.getMessage());
	}

	@ExceptionHandler(RateLimitExceededException.class)
	public void handleRateLimitExceededException(RateLimitExceededException rlee) {
		System.out.println("Rate limit exceeded - " + rlee.getMessage());
	}
}
