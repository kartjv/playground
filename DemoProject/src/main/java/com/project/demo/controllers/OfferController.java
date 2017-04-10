package com.project.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
		mav.addObject("offer", new Offer());
		return mav;
	}

	@RequestMapping("/showall")
	public ModelAndView showAll(ModelAndView mav) {
		mav.setViewName("showall :: showall-offers");
		mav.addObject("offers", offerRepository.findAll());
		return mav;
	}

	@RequestMapping("/add")
	public void addOffer(@ModelAttribute Offer offer) {
		offer.setIsredeemed(false);
		offerRepository.save(offer);
	}

	@RequestMapping(value = "/redeem")
	public ModelAndView redeem(ModelAndView mav) {
		mav.addObject("offer", new Offer());
		mav.setViewName("redeem :: redeem-offer");
		return mav;
	}

	@RequestMapping(value = "/redeemoffer")
	public ModelAndView redeemOffer(@ModelAttribute Offer offer, ModelAndView mav) {
		String code = offer.getOffercode();
		offer = offerRepository.getOfferByOffercode(code);
		String msg = (offer.isIsredeemed()) ? "Already redeemed, coupon not usable!"
				: ((offerRepository.setRedeemedByOffercode(code) == 1) ? "Redeemed now!" : "Invalid coupon code!");
		mav.addObject("redemptionStatus", msg);
		mav.setViewName("pizzaPalaceHome");
		return mav;
	}

}
