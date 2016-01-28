package com.dmcliver.donateme.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Brand")
public class Brand {

	private UUID brandId;
	private String brandName;
	
	@Id
	@Type(type = "pg-uuid")
	@Column(name = "BrandId")
	public UUID getBrandId() {
		return brandId;
	}
	public void setBrandId(UUID brandId) {
		this.brandId = brandId;
	}
	
	@Column(name = "BrandName", nullable = false)
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
