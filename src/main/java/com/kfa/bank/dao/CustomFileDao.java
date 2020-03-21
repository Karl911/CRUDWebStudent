package com.kfa.bank.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.kfa.bank.entity.CustomFile;
import com.kfa.bank.exception.CustomFileTransactionException;
import com.kfa.bank.model.CustomFileInfo;

@Repository
public class CustomFileDao {

	@Autowired
	private EntityManager entityManager;

	public CustomFileDao () {

	}

	public CustomFile findById(Long id)
	{
		return this.entityManager.find(CustomFile.class, id);
	}

	public List<CustomFileInfo> getFiles() {
		String sql = "Select new " + CustomFileInfo.class.getName() //
				+ "(e.id,e.filename,e.extension, e.folderid, e.size, e.absolutepath, e.creationdate, e.updatedate) " //
				+ " from " + CustomFile.class.getName() + " As e order by e.size desc";
		Query query = entityManager.createQuery(sql, CustomFileInfo.class);
		return query.getResultList();
	}
	
	public CustomFile findCustomFileByFolderIdAndName(int folderId, String filename) {

		String sql = "SELECT f FROM CustomFile f" 
				+ " where f.folderid='"+ folderId+"'"
				+ " and f.filename = :filename";
		
		try {
			TypedQuery<CustomFile> query = entityManager.createQuery(sql, CustomFile.class);
			query.setParameter("filename", filename);
			return (CustomFile) query.getSingleResult();
		}
		catch (NoResultException e)
		{
			return null;
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public void saveFile(String filename, String extension, int folderId, long size, String absolutePath, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFile file = new CustomFile();

		file.setFilename(filename);
		if (null != extension && extension.length() >3)
		{
			System.out.println("Warning unusual extension");
		}
		
		file.setExtension(extension);
		file.setFolderId(folderId);
		file.setSize(size);
		file.setAbsolutepath(absolutePath);
		file.setCreationdate(null);
		file.setUpdatedate(null);
		this.entityManager.persist(file);
		//this.entityManager.flush();

	}
	
    private String escapeQuotes (String aString)   {
    	
    	String escapedString = aString.replaceAll("%","\"");
    	return escapedString;
    }
}

