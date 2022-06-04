package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dtos.ReviewDTO;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional
	public ReviewDTO insert(ReviewDTO reviewDTO) {
		var review = new Review();
		
		review.setText(reviewDTO.getText());
		
		var movie = movieRepository.getOne(reviewDTO.getMovieId());
		review.setMovie(movie);
		
		var user = authService.authenticated();
		review.setUser(user);
		
		var newReview = reviewRepository.save(review);
		
		return new ReviewDTO(newReview);
	}
	
}
