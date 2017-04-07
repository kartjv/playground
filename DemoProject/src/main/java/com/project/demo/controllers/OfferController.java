package com.project.demo.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo.data.Offer;
import com.project.demo.data.OfferRepository;

@Controller
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;
	
	@RequestMapping("/")
	public String home(Model model) {
		return "pizzaPalaceHome";
	}
	
	@RequestMapping("/greeting")
	public String greeting(Model model) {
		model.addAttribute("name", "Karthika");
		return"greeting";
	}
	
	@RequestMapping("/showall")
	public String showAll(Model model) {
		model.addAttribute("offers", offerRepository.getAllAvailableOffers());
		return "showall :: showall-offers";
	}
	
	@RequestMapping("/show/{offercode}")
	public Offer getOffer(@RequestParam String offercode) {
		return offerRepository.getOfferByOffercode(offercode);
	}
	
	@RequestMapping("/add/{offercode}/{issuedfor}/{offerdesc}")
	public String addOffer(@RequestParam String offercode, @RequestParam String issuedfor, @RequestParam String offerdesc) {
		Offer offer = new Offer(offercode, issuedfor, new Date(), false, offerdesc);
		offerRepository.save(offer);
		return "Saved!";
	}
	
	@RequestMapping("/redeem")
	public String redeemOffer(@RequestParam String offercode) {
		Offer offer = offerRepository.getOfferByOffercode(offercode);
		if(offer.isIsredeemed()) {
			return "Already redeemed!";
		} else {
			offerRepository.setRedeemedByOffercode(offercode);
			return "Redeemed!";
		}
	}
	
}
