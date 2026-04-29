package com.sharma.demo.library.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Book {

	private Long id;
	private String title;
	private volatile boolean available = true;

	// Observer list.
	private final CopyOnWriteArrayList<BookObserver> observers = new CopyOnWriteArrayList<>();

	public Book() {
	}

	public Book(Long id, String title) {
		this.id = id;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void addObserver(BookObserver observer) {
		if (observer == null) {
			return;
		}
		// Avoid duplicates by equals().
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	public void removeObserver(BookObserver observer) {
		observers.remove(observer);
	}

	public void notifyWhenAvailable() {
		if (!available) {
			return;
		}

		for (BookObserver observer : List.copyOf(observers)) {
			if (observer != null) {
				observer.update(this);
			}
		}
		// Clear after notification so next reservation re-registers.
		observers.clear();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Book)) return false;
		Book book = (Book) o;
		return Objects.equals(id, book.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}

