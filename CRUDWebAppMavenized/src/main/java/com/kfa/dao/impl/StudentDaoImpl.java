package com.kfa.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kfa.dao.StudentDao;
import com.kfa.model.Student;

// test commit 2
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void add(Student student) {

		session.getCurrentSession().save(student);
	}

	@Override
	public void edit(Student student) {
		session.getCurrentSession().update(student);

	}

	@Override
	public void delete(int studentId) {
		session.getCurrentSession().delete(getStudent(studentId));
		System.out.println("aa");
	}

	@Override
	public Student getStudent(int studentId) {
		
		return (Student) session.getCurrentSession().get(Student.class, studentId);
	}

	@Override
	public List getAllStudents() {
		
		Query query = session.getCurrentSession().createQuery("from Student");
		return query.list();
	}

}
