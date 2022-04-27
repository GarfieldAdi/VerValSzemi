package org.example;

import domain.Grade;
import domain.Homework;
import domain.Pair;
import domain.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import repository.GradeXMLRepository;
import repository.HomeworkXMLRepository;
import repository.StudentXMLRepository;
import service.Service;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class ServiceMockTest {

	public Service service;

	public StudentXMLRepository fileRepository1;
	public HomeworkXMLRepository fileRepository2;
	public GradeXMLRepository fileRepository3;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		fileRepository1 = Mockito.mock(StudentXMLRepository.class);
		fileRepository2 = Mockito.mock(HomeworkXMLRepository.class);
		fileRepository3 = Mockito.mock(GradeXMLRepository.class);
		Mockito.when(fileRepository1.save(new Student(null, null, 111))).thenReturn(null);
		Student student = new Student("5", "Ana", 222);
		Mockito.when(fileRepository1.save(student)).thenReturn(student);
		service = new Service(fileRepository1, fileRepository2, fileRepository3);
		Homework homework = new Homework("111", "x", 2, 1);
		Mockito.when(fileRepository2.save(homework)).thenReturn(homework);
		Mockito.when(fileRepository2.save(new Homework(null, null, 1, 1))).thenReturn(null);

		Grade grade = new Grade(new Pair<>("112", "111"), 2.0, 1, "good");

		Homework homeWorkMock = mock(Homework.class);
		Mockito.when(homeWorkMock.getDeadline()).thenReturn(2);
		Mockito.when(fileRepository1.findOne("112")).thenReturn(student);
		Mockito.when(fileRepository2.findOne("111")).thenReturn(homeWorkMock);
		Mockito.when(fileRepository3.save(grade)).thenReturn(grade);
		Mockito.when(fileRepository3.save(new Grade(new Pair(null, null), 2.0, 1, "good"))).thenReturn(null);
	}


	@Test
	void shouldStudentBeAddedNull() {
		Assertions.assertEquals(1, service.saveStudent(null, any(), 111));
	}

	@Test
	void shouldFindAllStudentsCalled() {
		ArrayList<Student> studentArrayList = Mockito.mock(ArrayList.class);
		Mockito.when(fileRepository1.findAll()).thenReturn(studentArrayList);
		ArrayList<Student> students = (ArrayList<Student>) fileRepository1.findAll();
		Mockito.verify(fileRepository1).findAll();
	}

	@Test
	void shouldStudentBeAddedTrue() {
		Assertions.assertEquals(0, service.saveStudent("5", "Ana", 222));
	}

	@Test
	void shouldHomeworkBeAddedNull() {
		Assertions.assertEquals(1, service.saveHomework(null, null, 1, 1));
	}

	@Test
	void shouldHomeworkBeAddedTrue() {
		Assertions.assertEquals(0, service.saveHomework("111", "x", 2, 1));
	}

	@Test
	void shouldGradeBeAddedNull() {
		Assertions.assertEquals(-1, service.saveGrade(null, null, 2.0, 1, "good"));
	}

	@Test
	void shouldGradeBeAddedTrueNullHomework() {
		Assertions.assertEquals(1, service.saveGrade("112", "111", 2.0, 1, "good"));
	}

	@Test
	void shouldFindAllGradesCalled() {
		ArrayList<Grade> gradeArrayList = Mockito.mock(ArrayList.class);
		Mockito.when(fileRepository3.findAll()).thenReturn(gradeArrayList);
		ArrayList<Grade> grades = (ArrayList<Grade>) fileRepository3.findAll();
		Mockito.verify(fileRepository3).findAll();
	}


}
