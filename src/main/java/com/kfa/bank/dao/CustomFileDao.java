package com.kfa.bank.dao;

import com.kfa.bank.entity.CustomFile;
import com.kfa.bank.entity.CustomFolder;
import com.kfa.bank.exception.CustomFileTransactionException;
import com.kfa.bank.model.CustomFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

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
				+ " from " + CustomFile.class.getName() + " As e ";
		Query query = entityManager.createQuery(sql, CustomFileInfo.class);
		return query.getResultList();
	}


	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public void saveFile(String filename, String extension, int folderId, int size, String absolutePath, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFile file = new CustomFile();

		file.setFilename(filename);
		file.setExtension(extension);
		file.setFolderId(folderId);
		file.setSize(size);
		file.setAbsolutepath(absolutePath);
		file.setCreationdate(null);
		file.setUpdatedate(null);
		this.entityManager.persist(file);
		//this.entityManager.flush();

	}



	/*
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = CustomFileTransactionException.class)
	public void saveFolder(String folderName, int idParent, String folderpath, int size, Date creationDate, Date updateDate) throws CustomFileTransactionException {

		CustomFolder folder = new CustomFolder();

		folder.setFoldername(folderName);
		folder.setIdparent(idParent);
		folder.setFolderpath(folderpath);
		folder.setSize(size);
		folder.setCreationdate(null);
		folder.setUpdatedate(null);
		this.entityManager.persist(folder);
		//this.entityManager.flush();

	}
	*/

}
