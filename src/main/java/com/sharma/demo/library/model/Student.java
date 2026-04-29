package com.sharma.demo.library.model;

import java.time.Instant;
import java.util.Objects;

public class Student implements BookObserver {

	private Long id;
	private String name;

	// Used only for demonstration / logging.
	private Instant lastNotifiedAt;

	public Student() {
	}

	public Student(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Instant getLastNotifiedAt() {
		return lastNotifiedAt;
	}

	@Override
	public void update(Book book) {
		this.lastNotifiedAt = Instant.now();
		String bookTitle = (book == null) ? "null" : book.getTitle();
		System.out.println("Observer: Student " + id + " notified that book is available: " + bookTitle);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Student)) return false;
		Student student = (Student) o;
		return Objects.equals(id, student.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}

