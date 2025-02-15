package com.devsuperior.movieflix.dtos;

import javax.validation.constraints.NotBlank;

import com.devsuperior.movieflix.entities.Review;

public class ReviewDTO {

	private Long id;
	
	@NotBlank
	private String text;
	
	private Long movieId;
	
	private UserDTO user;
	
	public ReviewDTO() {}

	public ReviewDTO(Long id, String text, Long movieId, UserDTO user) {
		super();
		this.id = id;
		this.text = text;
		this.movieId = movieId;
		this.user = user;
	}

	public ReviewDTO(Review review) {
		super();
		this.id = review.getId();
		this.text = review.getText();
		this.movieId = review.getMovie().getId();
		this.user = new UserDTO(review.getUser());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}
	
}
