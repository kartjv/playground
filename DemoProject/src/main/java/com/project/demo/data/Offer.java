package com.project.demo.data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="offer")
public class Offer implements Serializable {

	@Id
	@Column(name = "offercode")
	private String offercode;

	@Column(name = "issuedfor")
	private String issuedfor;

	@Column(name = "issueddate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date issueddate;

	@Column(name = "isredeemed")
	private boolean isredeemed;

	@Column(name = "offerdesc")
	private String offerdesc;
	
	public Offer() {
		
	}

	public Offer(String offercode, String issuedfor, Date issueddate, boolean isredeemed, String offerdesc) {
		this.offercode = offercode;
		this.issuedfor = issuedfor;
		this.issueddate = issueddate;
		this.isredeemed = isredeemed;
		this.offerdesc = offerdesc;
	}

	/**
	 * @return the offercode
	 */
	public String getOffercode() {
		return offercode;
	}

	/**
	 * @return the issuedfor
	 */
	public String getIssuedfor() {
		return issuedfor;
	}

	/**
	 * @return the issueddate
	 */
	public Date getIssueddate() {
		return issueddate;
	}

	/**
	 * @return the isredeemed
	 */
	public boolean isIsredeemed() {
		return isredeemed;
	}

	/**
	 * @return the offerdesc
	 */
	public String getOfferdesc() {
		return offerdesc;
	}
}
