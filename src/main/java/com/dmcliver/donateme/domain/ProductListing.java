package com.dmcliver.donateme.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ProductListing")
public class ProductListing {

	private User user;
	private Product product;
	private UUID productListingId;
	private LocalDate dateListed;
	private LocalDate dateSold;
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "UserId", nullable = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@ManyToOne
	@JoinColumn(name = "ProductId", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Id
	@Type(type="pg-uuid")
	@Column(name = "ProductListingId")
	public UUID getProductListingId() {
		return productListingId;
	}
	public void setProductListingId(UUID productListingId) {
		this.productListingId = productListingId;
	}
	
	@Type(type="date")
	@Column(name = "DateListed", nullable = false)
	public Date getDateListed() {
		return Date.valueOf(dateListed);
	}
	public void setDateListed(Date dateListed) {
		this.dateListed = dateListed.toLocalDate();
	}
	
	@Transient
	public void setDateListed(LocalDate date){
		this.dateListed = date;
	}
	
	/**
	 * Returns the last ownership date using the java 8 date-time SDK
	 * which adds extra or extended date-time manipulation functionality 
	 */
	@Transient
	public LocalDate getDateListedExt(){
		return dateListed;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Type(type = "date")
	@Column(name = "DateSold")
	public Date getDateSold() {
		return Date.valueOf(dateSold);
	}
	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold.toLocalDate();
	}
	
	@Transient
	public void setDateSold(LocalDate date){
		dateSold = date;
	}
	
	/**
	 * Returns the date the product was sold using the java 8 date-time SDK
	 * which adds extra or extended date-time manipulation functionality 
	 */
	@Transient
	public LocalDate getDateSoldExt() {
		return dateSold;
	}
}
