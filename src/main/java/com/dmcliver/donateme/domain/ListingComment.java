package com.dmcliver.donateme.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "ListingComment")
public class ListingComment {

	private ProductListing productListing;
	private UUID commentId;
	private String message;
	private User userWhoCommented;
	private User userReplyingTo;
	
	@Id
	@Type(type = "pg-uuid")
	@Column(name = "CommentId")
	public UUID getCommentId() {
		return commentId;
	}
	public void setCommentId(UUID commentId) {
		this.commentId = commentId;
	}
	
	@Column(name = "Message", length = 1024)
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@ManyToOne
	@JoinColumn(name = "ProductListingId", nullable = false)
	public ProductListing getProductListing() {
		return productListing;
	}
	public void setProductListing(ProductListing productListing) {
		this.productListing = productListing;
	}
	
	@ManyToOne
	@JoinColumn(name = "UserId", nullable = false)
	public User getUserWhoCommented() {
		return userWhoCommented;
	}
	public void setUserWhoCommented(User userWhoCommented) {
		this.userWhoCommented = userWhoCommented;
	}
	
	@ManyToOne
	@JoinColumn(name = "ParentUserId", nullable = true)
	public User getUserReplyingTo() {
		return userReplyingTo;
	}
	public void setUserReplyingTo(User userReplyingTo) {
		this.userReplyingTo = userReplyingTo;
	}
}
