package com.sharma.demo.library.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.sharma.demo.library.model.Book;
import com.sharma.demo.library.model.BookObserver;
import com.sharma.demo.library.model.ReservationRequest;
import com.sharma.demo.library.model.ReservationResponse;
import com.sharma.demo.library.model.Student;
import com.sharma.demo.library.repository.LibraryRepository;

@Service
public class ReservationServiceImpl implements ReservationService {

	private static final long RELEASE_AFTER_SECONDS = 5;

	private final LibraryRepository repository;
	private final CacheManager cacheManager;
	private final ScheduledExecutorService scheduler;

	// Track whether a release task is already scheduled per book.
	private final java.util.concurrent.ConcurrentHashMap<Long, Boolean> releaseScheduled = new java.util.concurrent.ConcurrentHashMap<>();

	public ReservationServiceImpl(LibraryRepository repository, CacheManager cacheManager) {
		this.repository = repository;
		this.cacheManager = cacheManager;

		this.scheduler = Executors.newScheduledThreadPool(1, r -> {
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.setName("library-release-scheduler");
			return t;
		});
	}

	@Override
	public ReservationResponse reserve(ReservationRequest request) {
		Book book = repository.getOrCreateBook(request.getBookId());
		if (book == null) {
			throw new IllegalArgumentException("bookId is required");
		}

		Student student = repository.getOrCreateStudent(request.getStudentId(), request.getStudentName());
		if (student == null) {
			throw new IllegalArgumentException("studentId is required");
		}

		synchronized (book) {
			// Register student as observer waiting for the book.
			BookObserver observer = student;
			book.addObserver(observer);

			// If the book is currently available, reserve it now (mark unavailable).
			if (book.isAvailable()) {
				book.setAvailable(false);
				evictBookCache(book.getId());
			} else {
				// Still evict to keep GET response consistent with current availability.
				evictBookCache(book.getId());
			}
		}

		boolean scheduled = releaseScheduled.putIfAbsent(book.getId(), true) == null;
		if (scheduled) {
			scheduler.schedule(() -> releaseBook(book.getId()), RELEASE_AFTER_SECONDS, TimeUnit.SECONDS);
		}

		ReservationResponse resp = new ReservationResponse();
		resp.setBookId(book.getId());
		resp.setStudentId(student.getId());
		resp.setBookAvailableAfterReservation(book.isAvailable());
		resp.setMessage("Reservation placed. Student will be notified when book is available.");
		return resp;
	}

	private void releaseBook(Long bookId) {
		Book book = repository.findBook(bookId);
		if (book == null) {
			return;
		}

		synchronized (book) {
			book.setAvailable(true);
			evictBookCache(bookId);
			// Notify all waiting students.
			book.notifyWhenAvailable();
		}

		releaseScheduled.remove(bookId);
	}

	private void evictBookCache(Long bookId) {
		Cache cache = cacheManager.getCache("books");
		if (cache != null) {
			cache.evict(bookId);
		}
	}

	@Override
	public Book getBookById(Long id) {
		Book book = repository.findBook(id);
		if (book == null) {
			throw new BookNotFoundException(id);
		}
		return book;
	}
}

