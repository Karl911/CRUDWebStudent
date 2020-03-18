package com.kfa.bank;

import org.hibernate.Session;

import com.kfa.bank.entity.BankAccount;

public class TestHibernateInsert {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        //Add new BankAccount object
        BankAccount account = new BankAccount();
        account.setId(9L);
        account.setFullName("Franck");
        account.setBalance(6000);
         
        //Save the bank account in database
        session.save(account);
 
        //Commit the transaction
        session.getTransaction().commit();
        HibernateUtil.shutdown();
	}

}
