package com.kfa.bank.dao;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kfa.bank.entity.CustomFile;
import com.kfa.bank.entity.CustomFolder;
import com.kfa.bank.exception.CustomFileTransactionException;

@Repository
public class CustomFolderDao {

	@Autowired
	private EntityManager entityManager;

	public CustomFolderDao() {

	}

	public CustomFolder findById(Long id)
	{
		return this.entityManager.find(CustomFolder.class, id);
	}

	public CustomFolder findCustomFolderByFolderpath(String folderPath) {

		//String preparedFolderPath = addBackslash(folderPath);

		String sql = "SELECT f FROM CustomFolder f" 
				+ " where f.folderpath = : folderpath";

		try {
			TypedQuery<CustomFolder> query = entityManager.createQuery(sql, CustomFolder.class);
			query.setParameter("folderpath", folderPath);
			return (CustomFolder) query.getSingleResult();
		}
		catch (NoResultException e)
		{
			return null;
		}
	}

	public CustomFolder getFolderByIdParent(int idParent) {
		
		String sql = "SELECT f FROM CustomFolder f" 
				+ " where f.idparent='"+idParent+"'";
		
		TypedQuery<CustomFolder> query = entityManager.createQuery(sql, CustomFolder.class);
		
		return (CustomFolder) query.getSingleResult();
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public int saveFolder(String folderName, int idParent, String folderPath, int size, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFolder folder = new CustomFolder(idParent, folderName, folderPath, size, creationDate, updateDate);
		/*
		folder.setIdparent(idParent);
		folder.setFoldername(folderName);
		folder.setFolderpath(folderPath);
		folder.setSize(size);
		folder.setCreationdate(null);
		folder.setUpdatedate(null);
		 */
		this.entityManager.persist(folder);
		System.out.println("after save");
		//this.entityManager.flush();
		return folder.getId();
	}
}
