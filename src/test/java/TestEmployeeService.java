package com.journaldev.spring.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import com.journaldev.hibernate.data.Employee;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Date;
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TestEmployeeService {
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public void register(Employee emp){
		// Acquire session
		Session session = sessionFactory.getCurrentSession();
		// Save employee, saving behavior get done in a transactional manner


		 Session sess = sessionFactory.openSession();
		 Transaction tx = null;
		 try {
		     tx = sess.beginTransaction();
		     //do some work
		     sess.save(emp);	
		     System.out.println(emp);
		     tx.commit();
		 }
		 catch (Exception e) {
		     if (tx!=null) tx.rollback();
		     System.out.println(e);
		     throw e;
		 }
		 finally {
		     sess.close();
		 }
	}

	public List<Employee> selectAll(){
		Session session = getSessionFactory().getCurrentSession();
		assertEquals(true, session !=null);
		Criteria criteria = session.createCriteria(Employee.class);
		assertEquals(true, criteria !=null);		
		List<Employee> persons = (List<Employee>)criteria.list();
		return persons;
	}
 
    @Test
    @Transactional
    public void testEvenOddNumber(){
	List<Employee> list = selectAll();
        assertEquals(true, list.size() > 0);
	int zahl1 = list.size();	
	Employee emp = new Employee();
	emp.setEmployeeName("test");
	emp.setEmployeeHireDate(new Date());
	emp.setEmployeeSalary(100);
	register(emp);	
	list = selectAll();
        assertEquals(zahl1 + 1, list.size() + 1);
    }

}
