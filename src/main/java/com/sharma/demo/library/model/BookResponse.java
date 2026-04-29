package com.sharma.demo.library.model;

import java.util.Objects;

public class BookResponse {

	private Long id;
	private String title;
	private boolean available;

	private String cacheStatus; // HIT / MISS

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

	public String getCacheStatus() {
		return cacheStatus;
	}

	public void setCacheStatus(String cacheStatus) {
		this.cacheStatus = cacheStatus;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof BookResponse)) return false;
		BookResponse that = (BookResponse) o;
		return available == that.available && Objects.equals(id, that.id) && Objects.equals(title, that.title);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, available);
	}
}

