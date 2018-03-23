package com.kfa.dao;

import java.util.List;

import com.kfa.model.Student;

public interface StudentDao {
	public void add(Student student);
	public void edit(Student student);
	public void delete(int studentId);
	public Student getStudent(int studentId);
	public List getAllStudents();

}
