package com.dmcliver.donateme.domain;

import java.net.URL;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "Image")
public class Image {

	private UUID imageId;
	private URL fileNamePath;
	private Product product;
	
	@Id
	@Type(type = "pg-uuid")
	@Column(name = "ImageId")
	public UUID getImageId() {
		return imageId;
	}
	public void setImageId(UUID imageId) {
		this.imageId = imageId;
	}
	
	@Column(name = "FileNamePath", nullable = false)
	public URL getFileNamePath() {
		return fileNamePath;
	}
	public void setFileNamePath(URL fileNamePath) {
		this.fileNamePath = fileNamePath;
	}
	
	@ManyToOne
	@JoinColumn(name = "ProductId", nullable = false)
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}
