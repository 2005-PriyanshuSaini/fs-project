package com.sharma.demo.library.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.sharma.demo.library.model.Book;
import com.sharma.demo.library.model.Student;

@Repository
public class LibraryRepository {

	private final Map<Long, Book> books = new ConcurrentHashMap<>();
	private final Map<Long, Student> students = new ConcurrentHashMap<>();

	public Book getOrCreateBook(Long bookId) {
		if (bookId == null) {
			return null;
		}

		return books.computeIfAbsent(bookId, id -> new Book(id, "Book-" + id));
	}

	public Student getOrCreateStudent(Long studentId, String studentName) {
		if (studentId == null) {
			return null;
		}

		final String name = (studentName == null || studentName.isBlank())
				? ("Student-" + studentId)
				: studentName;

		return students.computeIfAbsent(studentId, id -> new Student(id, name));
	}

	public Book findBook(Long bookId) {
		if (bookId == null) {
			return null;
		}
		return books.get(bookId);
	}
}

