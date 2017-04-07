package com.project.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.project.demo.data.Offer;
import com.project.demo.data.OfferRepository;

@RestController
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;

	@RequestMapping("/")
	public ModelAndView home(ModelAndView mav) {
		mav.setViewName("pizzaPalaceHome");
		return mav;
	}

	@RequestMapping("/showall")
	public ModelAndView showAll(ModelAndView mav) {
		mav.setViewName("showall :: showall-offers");
		mav.addObject("offers", offerRepository.getAllAvailableOffers());
		return mav;
	}

	@RequestMapping("/add")
	public Offer addOffer(@ModelAttribute Offer offer) {
		offer.setIsredeemed(false);
		offerRepository.save(offer);
		return offer;
	}

	@RequestMapping(value="/redeem/{offercode}", method=RequestMethod.PUT)
	public ModelAndView redeemOffer(@RequestParam String offercode, ModelAndView mav) {
		Offer offer = offerRepository.getOfferByOffercode(offercode);
		String msg = null;
		if (offer.isIsredeemed()) {
			msg = "Already Redeemed, Coupon not usable!";
		} else {
			offerRepository.setRedeemedByOffercode(offercode);
			msg = "Redeemed Now!";
		}
		mav.setViewName("redeem :: redeem-offer");
		mav.addObject("redemptionStatus", msg);
		return mav;
	}
	

}
