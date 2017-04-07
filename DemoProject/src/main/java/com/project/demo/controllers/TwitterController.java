package com.project.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.ApiException;
import org.springframework.social.DuplicateStatusException;
import org.springframework.social.MissingAuthorizationException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.RateLimitExceededException;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.InvalidMessageRecipientException;
import org.springframework.social.twitter.api.MessageTooLongException;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.demo.data.Offer;
import com.project.demo.data.DailySpecial;
import com.project.demo.data.TwitterService;

@Controller
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@RequestMapping(value = "/tweet")
	public String getMyTweets(Model model) {
		model.addAttribute("dailySpecial", new DailySpecial());
		return "tweetform :: tweet-daily";
	}

	@RequestMapping(value = "/tweetdaily")
	public String postDailySpecial(@ModelAttribute DailySpecial dailySpecial) {
		twitterService.postDailySpecial(dailySpecial.getSpecialName() + " - " + dailySpecial.getBriefRecipe());
		return "redirect:/";
	}

	@RequestMapping(value = "/rewardcust")
	public String rewardCustomer(@ModelAttribute String custId, Model model) {
		Offer offer = twitterService.rewardCustomer(custId);
		model.addAttribute("offer", offer);
		return "add";
	}

	//@Scheduled(fixedRate = 60000)
	public void autoRespondInTwitter() {
		List<Tweet> mymentions = twitterService.getWhoMentionedMe();
		while (twitterService != null) {
			if (mymentions.size() > 0) {
				for (Tweet tw : mymentions) {
					CursoredList<Long> followersList = twitterService.getMyFollowers();
					if(followersList.contains(tw.getInReplyToUserId())) {
						twitterService.rewardCustomer(tw.getInReplyToScreenName());
					}
				}
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
