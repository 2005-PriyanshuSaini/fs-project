package com.sharma.demo.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private final Map<Long, Student> students = new ConcurrentHashMap<>();
	private final AtomicLong idGenerator = new AtomicLong(1);

	public StudentService() {
		// Seed one record so list endpoint shows immediate data.
		Student seed = new Student(idGenerator.getAndIncrement(), "Rahul", "rahul@example.com", "CSE");
		students.put(seed.getId(), seed);
	}

	public List<Student> getAllStudents() {
		return new ArrayList<>(students.values());
	}

	public Student getStudentById(Long id) {
		return students.get(id);
	}

	public Student createStudent(Student student) {
		Long newId = idGenerator.getAndIncrement();
		student.setId(newId);
		students.put(newId, student);
		return student;
	}

	public Student updateStudent(Long id, Student updatedStudent) {
		Student existing = students.get(id);
		if (existing == null) {
			return null;
		}

		existing.setName(updatedStudent.getName());
		existing.setEmail(updatedStudent.getEmail());
		existing.setCourse(updatedStudent.getCourse());
		return existing;
	}

	public boolean deleteStudent(Long id) {
		return students.remove(id) != null;
	}
}
