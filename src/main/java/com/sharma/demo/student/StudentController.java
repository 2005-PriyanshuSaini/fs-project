package com.sharma.demo.student;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public List<Student> getAllStudents() {
		return studentService.getAllStudents();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		Student student = studentService.getStudentById(id);
		if (student == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "Student not found with id " + id));
		}
		return ResponseEntity.ok(student);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Student> createStudent(@RequestBody Student student) {
		Student created = studentService.createStudent(student);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student student) {
		Student updated = studentService.updateStudent(id, student);
		if (updated == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "Student not found with id " + id));
		}
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
		boolean deleted = studentService.deleteStudent(id);
		if (!deleted) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Map.of("message", "Student not found with id " + id));
		}
		return ResponseEntity.ok(Map.of("message", "Student deleted successfully"));
	}
}
