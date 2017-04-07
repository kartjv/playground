package com.project.demo.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {
	
	@Query("update Offer o set o.isredeemed = true where o.offercode = :offercode ")
	public Offer setRedeemedByOffercode(String offercode);
	
	@Query("select o from Offer o where o.offercode = :offercode")
	public Offer getOfferByOffercode(String offercode);
	
	@Query("select o from Offer o where o.isredeemed = true")
	public List<Offer> getAllAvailableOffers();
}
