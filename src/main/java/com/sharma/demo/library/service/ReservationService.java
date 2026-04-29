package com.sharma.demo.library.service;

import com.sharma.demo.library.model.Book;
import com.sharma.demo.library.model.ReservationRequest;
import com.sharma.demo.library.model.ReservationResponse;

public interface ReservationService {

	ReservationResponse reserve(ReservationRequest request);

	Book getBookById(Long id);
}

