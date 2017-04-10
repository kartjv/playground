package com.project.demo.data;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface OfferRepository extends CrudRepository<Offer, Long> {
	
	@Modifying
	@Transactional
	@Query("update Offer o set o.isredeemed = true where o.offercode = ?1")
	public int setRedeemedByOffercode(String offercode);
	
	@Query("select o from Offer o where o.offercode = ?1")
	public Offer getOfferByOffercode(String offercode);
	
	@Query("select o from Offer o")
	public List<Offer> getAllAvailableOffers();
}
