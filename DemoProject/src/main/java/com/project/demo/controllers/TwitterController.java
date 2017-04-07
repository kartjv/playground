package com.project.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.demo.data.StatusData;
import com.project.demo.data.TwitterService;

@Controller
public class TwitterController {

	@Autowired
	private TwitterService twitterService;

	@RequestMapping(value = "/tweet")
	public String getMyTweets(Model model) {
		model.addAttribute("statusData", new StatusData());
		return "tweetform :: tweet-daily";
	}

	@RequestMapping(value = "/tweetdaily")
	public String postDailySpecial(@ModelAttribute StatusData statusData) {
		twitterService.postDailySpecial(statusData.getTest());
		return "redirect:/";
	}

	public String rewardCustomer(@RequestParam String custId) {
		return twitterService.rewardCustomer(custId);
	}
	
	public void autoRespond() {
		
	}
}
