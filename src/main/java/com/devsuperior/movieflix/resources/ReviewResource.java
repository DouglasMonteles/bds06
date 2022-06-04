package com.devsuperior.movieflix.resources;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.movieflix.dtos.ReviewDTO;
import com.devsuperior.movieflix.services.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewResource {

	@Autowired
	private ReviewService reviewService;
	
	@PostMapping
	@PreAuthorize(value = "hasAnyRole('ADMIN', 'MEMBER')")
	public ResponseEntity<ReviewDTO> insert(@Valid @RequestBody ReviewDTO reviewDTO) {
		var newReviewDTO = reviewService.insert(reviewDTO);
		
		var uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newReviewDTO.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(newReviewDTO);
	}
	
}
