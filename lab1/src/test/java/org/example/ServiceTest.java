package org.example;

import domain.Grade;
import domain.Homework;
import domain.Student;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import service.Service;
import validation.GradeValidator;
import validation.HomeworkValidator;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest {

	private static Service service;
	private static StudentXMLRepository fileRepository1;

	@BeforeAll
	public static void setService() {

		Validator<Student> studentValidator = new StudentValidator();
		Validator<Homework> homeworkValidator = new HomeworkValidator();
		Validator<Grade> gradeValidator = new GradeValidator();
		fileRepository1 = new StudentXMLRepository(studentValidator, "students.xml");
		HomeworkXMLRepository fileRepository2 = new HomeworkXMLRepository(homeworkValidator, "homework.xml");
		GradeXMLRepository fileRepository3 = new GradeXMLRepository(gradeValidator, "grades.xml");
		service = new Service(fileRepository1, fileRepository2, fileRepository3);
		Assertions.assertNotNull(service);
	}

	@ParameterizedTest
	@ValueSource(ints = {222})
	void shouldStudentBeAddedTrue(int group) {
		int newStudent = service.saveStudent("5", "Ana", group);
		Assertions.assertEquals(1, newStudent);
	}

	@Test
	void shouldStudentBeAddedFalse() {
		Assertions.assertThrows(NullPointerException.class, () -> fileRepository1.save(null));
	}

	@Test
	void shouldHomeworkBeAdded() {
		int newHomework = service.saveHomework("111", "x", 2, 1);
		Assertions.assertEquals(1, newHomework);
		newHomework = service.saveHomework("111", "x", 2, 1);
		Assertions.assertEquals(0, newHomework);
	}

//	@Test
//	void shouldGradeBeAdded() {
//		int newStudent = service.saveStudent("122", "Ana", 222);
//		Assertions.assertEquals(1, newStudent);
//		int newHomework = service.saveHomework("125", "x", 2, 1);
//		Assertions.assertEquals(1, newHomework);
//		int newGrade = service.saveGrade("122", "125", 2.0, 1, "good");
//		Assertions.assertEquals(1, newGrade);
//		int deletedStudent = service.deleteStudent("122");
//		Assertions.assertEquals(1, deletedStudent);
//		int deletedHomework = service.deleteHomework("125");
//		Assertions.assertEquals(1, deletedHomework);
//	}

	@AfterAll
	public static void shouldStudentBeDeletedTrue() {
		int deletedStudent = service.deleteStudent("5");
		Assertions.assertEquals(1, deletedStudent);
	}

	@AfterAll
	public static void shouldHomeworkBeDeletedTrue() {
		int deletedHomework = service.deleteHomework("111");
		Assertions.assertEquals(1, deletedHomework);
	}


}
