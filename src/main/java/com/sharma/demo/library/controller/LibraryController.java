package com.sharma.demo.library.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.demo.library.model.Book;
import com.sharma.demo.library.model.BookResponse;
import com.sharma.demo.library.model.ReservationRequest;
import com.sharma.demo.library.model.ReservationResponse;
import com.sharma.demo.library.service.ReservationService;

@RestController
public class LibraryController {

	private final ReservationService reservationService;
	private final CacheManager cacheManager;

	public LibraryController(ReservationService reservationService, CacheManager cacheManager) {
		this.reservationService = reservationService;
		this.cacheManager = cacheManager;
	}

	@PostMapping("/reservation")
	public ResponseEntity<ReservationResponse> createReservation(@RequestBody ReservationRequest request) {
		ReservationResponse response = reservationService.reserve(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
		Cache cache = cacheManager.getCache("books");

		if (cache != null) {
			Book cached = cache.get(id, Book.class);
			if (cached != null) {
				return ResponseEntity.ok()
						.header("X-Cache", "HIT")
						.body(toResponse(cached, "HIT"));
			}
		}

		Book book = reservationService.getBookById(id);
		if (cache != null) {
			cache.put(id, book);
		}

		return ResponseEntity.ok()
				.header("X-Cache", "MISS")
				.body(toResponse(book, "MISS"));
	}

	private static BookResponse toResponse(Book book, String cacheStatus) {
		BookResponse resp = new BookResponse();
		resp.setId(book.getId());
		resp.setTitle(book.getTitle());
		resp.setAvailable(book.isAvailable());
		resp.setCacheStatus(cacheStatus);
		return resp;
	}
}

