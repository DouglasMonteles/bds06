package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dtos.MovieDTO;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieDTO> findByGenre(Long genreId, Pageable pageable) {
		var genre = (genreId > 0) ? genreRepository.getOne(genreId) : null;
		var movies = movieRepository.findByGenre(genre, pageable);
		var moviesDTO = movies.map(movie -> new MovieDTO(movie));
		
		return moviesDTO;
	}
	
	@Transactional(readOnly = true)
	public MovieDTO findById(Long id) {
		var movie = movieRepository.findById(id);
		var optionalMovie = movie.orElseThrow(() -> 
			new ResourceNotFoundException("Movie not found. Id: " + id));
		
		return new MovieDTO(optionalMovie);
	}
	
}
