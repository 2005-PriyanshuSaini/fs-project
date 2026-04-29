package com.sharma.demo.library.model;

public class ReservationResponse {

	private Long bookId;
	private Long studentId;
	private String message;
	private boolean bookAvailableAfterReservation;

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isBookAvailableAfterReservation() {
		return bookAvailableAfterReservation;
	}

	public void setBookAvailableAfterReservation(boolean bookAvailableAfterReservation) {
		this.bookAvailableAfterReservation = bookAvailableAfterReservation;
	}
}

